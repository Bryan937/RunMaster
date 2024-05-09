package com.example.runmaster.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DatabaseService {
    companion object {
        val instance: DatabaseService by lazy { Holder.instance }

        private object Holder {
            val instance = DatabaseService()
        }
    }

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val usersReference: DatabaseReference = database.getReference("users")
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun createUser(
        email: String,
        password: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "User created successfully")
                } else {
                    callback(false, "User creation failed: ${task.exception?.message}")
                }
            }
    }


    fun signOut() {
        auth.signOut()
    }
}