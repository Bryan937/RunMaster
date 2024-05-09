package com.example.runmaster.models.sessions

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import com.example.runmaster.services.DatabaseService
import com.google.firebase.auth.FirebaseAuth
import com.example.runmaster.utils.LoginDataStore
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class User(
    private var name: String,
    private var lastName: String,
    private var email: String,
    private var password: String,
    private var profilePicture: String,
    private val history: MutableMap<String, Session> = mutableMapOf()
) {
    private val database = DatabaseService.instance.usersReference
    private val auth = DatabaseService.instance.auth

    companion object {
        val instance: User by lazy { User("", "", "", "", "") }
    }

    fun setInfoByLabel(label: String, value: String) {
        when (label) {
            "First name" -> name = value
            "Last name" -> lastName = value
            "Email" -> email = value
            "Password" -> password = value
            "Register" -> profilePicture = value
            else -> throw IllegalArgumentException("Invalid label: $label")
        }
    }

    fun getAuth(): FirebaseAuth {
        return auth
    }

    fun getInfoByLabel(label: String): String {
        return when (label) {
            "First name" -> name
            "Last name" -> lastName
            "Email" -> email
            "Password" -> password
            "Picture" -> profilePicture
            else -> throw IllegalArgumentException("Invalid label: $label")
        }
    }

    fun addToHistory(session: Session) {
        history[session.sessionId] = session
        updateDatabase { success, message -> null }
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
        return emailRegex.matches(email)
    }

    private fun sanitizeEmail(): String {
        return email.replace(".", "_46")
    }

    fun updateDatabase(callback: (success: Boolean, message: String) -> Unit) {
        signInAndProceed(email, password, callback)
    }


    // updates an existing user's information within the firebase database
    private fun updateExistingUser(
        snapshot: DataSnapshot,
        databaseRef: DatabaseReference,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        if (snapshot.child("userInformation").child("password").value == password) {
            name = snapshot.child("userInformation").child("name").value.toString()
            lastName = snapshot.child("userInformation").child("lastName").value.toString()
            profilePicture = snapshot.child("userInformation").child("profile").value.toString()

            // updates the history accordingly and successfully signs in
            val historyType = object : GenericTypeIndicator<Map<String, Session>>() {}
            val existingHistory = snapshot.child("history").getValue(historyType)
            val updatedHistory = (existingHistory ?: emptyMap()).toMutableMap()
            updatedHistory.putAll(history)

            databaseRef.child("history").setValue(updatedHistory)
            callback(true, "Login Success")
        } else {
            // incorrect sign in values
            callback(false, "Password Incorrect")
        }
    }

    // accesses the firebase database with an email and password and returns the authentication feedback
    // if the authentication with the given values succeeds and the user exists already, it just updates their information
    // otherwise it creates a new user within the database
    // if the authentication does not succeed it returns a message containing the error
    private fun signInAndProceed(
        email: String,
        password: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { signInTask ->
            if (signInTask.isSuccessful && auth.currentUser != null) {
                val userReference = database.child(sanitizeEmail())

                val userData = mutableMapOf(
                    "userInformation" to mapOf(
                        "name" to name,
                        "lastName" to lastName,
                        "password" to password,
                        "profile" to profilePicture
                    ),
                    "history" to history
                )

                userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) updateExistingUser(snapshot, userReference, callback)
                        else {
                            userReference.setValue(userData)
                            callback(true, "Registered New User")
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("User", "Error updating database: $error")
                    }
                })

            } else callback(false, "User sign in failed: ${signInTask.exception?.message}")
        }
    }


    // fetches a user's data from the database
    private fun retrieveSessions(snapshot: DataSnapshot, onDataFetched: (List<Session>) -> Unit) {
        val oldSessions = mutableListOf<Session>()
        if (snapshot.child("userInformation").child("password").value == password) {
            snapshot.child("history").children.forEach { historySnapshot ->
                val session = historySnapshot.getValue(Session::class.java)
                if (session != null) {
                    oldSessions.add(session)
                }
            }
        }
        onDataFetched(oldSessions)
    }


    // Checks the database for user data
    // if the user is authenticated and already exists, we fetch their data
    // otherwise we return nothing
    fun retrieveFromDatabase(onDataFetched: (List<Session>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { signInTask ->
            if (signInTask.isSuccessful && auth.currentUser != null) {
                val userReference = database.child(sanitizeEmail())

                userReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) retrieveSessions(snapshot, onDataFetched)
                        else onDataFetched(emptyList())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("User", "Error retrieving user history within database: $error")
                        onDataFetched(emptyList())
                    }
                })
            } else onDataFetched(emptyList())
        }
    }

    // updates the an existing user's profile picture within the database
    fun updateUserProfilePic(callback: (success: Boolean, message: String) -> Unit) {
        val userReference = database.child(sanitizeEmail())
        val userData = mutableMapOf(
                "name" to name,
                "lastName" to lastName,
                "password" to password,
                "profile" to profilePicture
        )
        userReference.child("userInformation").setValue(userData)
            .addOnCompleteListener { setTask ->
                if (setTask.isSuccessful) {
                    callback(true, "User name updated successfully")
                } else {
                    callback(false, "Error updating user name: ${setTask.exception?.message}")
                }
            }
    }
}