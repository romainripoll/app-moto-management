package com.romainripoll.motogpmanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "riders")
data class Rider(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    var nationality: String,
    var skill: Int,            // 0-100, riding skill
    var consistency: Int,      // 0-100, consistency during race
    var wetWeatherSkill: Int,  // 0-100, skill in wet conditions
    var experience: Int,       // 0-100, experience level
    var morale: Int,           // 0-100, current morale
    var salary: Int,           // Annual salary in game currency
    var contractYears: Int,    // Number of years left on contract
    var teamId: Long? = null,  // ID of the team this rider belongs to
    var photo: String? = null, // Path to rider photo
    var age: Int,              // Rider age
    var isPlayer: Boolean = false // Whether this rider is controlled by the player
) {
    companion object {
        // Factory method to create sample riders for testing or initial game setup
        fun createSampleRiders(): List<Rider> {
            return listOf(
                Rider(
                    name = "Marc Marquez",
                    nationality = "ESP",
                    skill = 92,
                    consistency = 88,
                    wetWeatherSkill = 85,
                    experience = 90,
                    morale = 85,
                    salary = 15000000,
                    contractYears = 2,
                    age = 32
                ),
                Rider(
                    name = "Fabio Quartararo",
                    nationality = "FRA",
                    skill = 90,
                    consistency = 86,
                    wetWeatherSkill = 80,
                    experience = 75,
                    morale = 80,
                    salary = 12000000,
                    contractYears = 3,
                    age = 28
                ),
                Rider(
                    name = "Francesco Bagnaia",
                    nationality = "ITA",
                    skill = 91,
                    consistency = 87,
                    wetWeatherSkill = 82,
                    experience = 78,
                    morale = 90,
                    salary = 13000000,
                    contractYears = 2,
                    age = 29
                ),
                Rider(
                    name = "Joan Mir",
                    nationality = "ESP",
                    skill = 88,
                    consistency = 85,
                    wetWeatherSkill = 78,
                    experience = 76,
                    morale = 75,
                    salary = 10000000,
                    contractYears = 1,
                    age = 27
                ),
                Rider(
                    name = "Jack Miller",
                    nationality = "AUS",
                    skill = 86,
                    consistency = 82,
                    wetWeatherSkill = 88,
                    experience = 82,
                    morale = 78,
                    salary = 8000000,
                    contractYears = 1,
                    age = 30
                ),
                Rider(
                    name = "Maverick Viñales",
                    nationality = "ESP",
                    skill = 87,
                    consistency = 80,
                    wetWeatherSkill = 83,
                    experience = 84,
                    morale = 70,
                    salary = 9000000,
                    contractYears = 2,
                    age = 31
                ),
                Rider(
                    name = "Alex Rins",
                    nationality = "ESP",
                    skill = 85,
                    consistency = 81,
                    wetWeatherSkill = 80,
                    experience = 78,
                    morale = 75,
                    salary = 7500000,
                    contractYears = 1,
                    age = 29
                ),
                Rider(
                    name = "Franco Morbidelli",
                    nationality = "ITA",
                    skill = 84,
                    consistency = 80,
                    wetWeatherSkill = 79,
                    experience = 75,
                    morale = 72,
                    salary = 6500000,
                    contractYears = 1,
                    age = 30
                )
            )
        }
    }
}
