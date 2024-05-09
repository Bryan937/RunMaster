package com.example.runmaster.utils

class Position(lat: Double = 0.0, long: Double = 0.0) {
    var latitude = lat
    var longitude = long

    fun setCoordinates(lat: Double, long: Double) {
        latitude = lat
        longitude = long
    }

    override fun toString(): String {
        return "Position(x=$latitude, y=$longitude)"
    }
}