package com.example.runmaster.view_models

import androidx.lifecycle.ViewModel
import com.example.runmaster.models.sessions.Session
import com.example.runmaster.utils.Position


object OldSessionViewModel : ViewModel() {
    var positions: MutableList<Position> = mutableListOf()
    var timeInMillis: Long = 0
    var numberOfSteps: Int = 0
    var distance: Double = 0.0
    var speed: Double = 0.0

    fun updateOldSession(session: Session) {
        this.positions = session.positions.toMutableList()
        this.timeInMillis = session.time
        this.numberOfSteps = session.steps
        this.distance = session.distance
        this.speed = session.speed
    }
}