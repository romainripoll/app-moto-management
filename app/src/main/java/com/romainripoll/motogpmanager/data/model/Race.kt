package com.romainripoll.motogpmanager.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Calendar
import java.util.Date

@Entity(tableName = "circuits")
data class Circuit(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    var country: String,
    var length: Double,  // In kilometers
    var turns: Int,
    var layout: String? = null,  // Image path
    var speedFocus: Double,
    var accelerationFocus: Double,
    var handlingFocus: Double,
    var brakingFocus: Double,
    var overtakingDifficulty: Double,
    var tireWear: Double
) {
    companion object {
        // Factory method to create sample circuits for testing or initial game setup
        fun createSampleCircuits(): List<Circuit> {
            return listOf(
                Circuit(
                    name = "Losail International Circuit",
                    country = "Qatar",
                    length = 5.38,
                    turns = 16,
                    speedFocus = 0.8,
                    accelerationFocus = 0.7,
                    handlingFocus = 0.5,
                    brakingFocus = 0.6,
                    overtakingDifficulty = 0.6,
                    tireWear = 0.5
                ),
                Circuit(
                    name = "Termas de Río Hondo",
                    country = "Argentina",
                    length = 4.8,
                    turns = 14,
                    speedFocus = 0.6,
                    accelerationFocus = 0.7,
                    handlingFocus = 0.7,
                    brakingFocus = 0.5,
                    overtakingDifficulty = 0.4,
                    tireWear = 0.7
                ),
                Circuit(
                    name = "Circuit of the Americas",
                    country = "United States",
                    length = 5.51,
                    turns = 20,
                    speedFocus = 0.6,
                    accelerationFocus = 0.8,
                    handlingFocus = 0.8,
                    brakingFocus = 0.9,
                    overtakingDifficulty = 0.5,
                    tireWear = 0.6
                ),
                Circuit(
                    name = "Circuito de Jerez",
                    country = "Spain",
                    length = 4.42,
                    turns = 13,
                    speedFocus = 0.5,
                    accelerationFocus = 0.6,
                    handlingFocus = 0.8,
                    brakingFocus = 0.7,
                    overtakingDifficulty = 0.7,
                    tireWear = 0.6
                ),
                Circuit(
                    name = "Le Mans",
                    country = "France",
                    length = 4.18,
                    turns = 14,
                    speedFocus = 0.6,
                    accelerationFocus = 0.7,
                    handlingFocus = 0.7,
                    brakingFocus = 0.8,
                    overtakingDifficulty = 0.5,
                    tireWear = 0.5
                ),
                Circuit(
                    name = "Mugello",
                    country = "Italy",
                    length = 5.24,
                    turns = 15,
                    speedFocus = 0.9,
                    accelerationFocus = 0.8,
                    handlingFocus = 0.7,
                    brakingFocus = 0.7,
                    overtakingDifficulty = 0.6,
                    tireWear = 0.7
                ),
                Circuit(
                    name = "Circuit de Barcelona-Catalunya",
                    country = "Spain",
                    length = 4.66,
                    turns = 14,
                    speedFocus = 0.7,
                    accelerationFocus = 0.7,
                    handlingFocus = 0.7,
                    brakingFocus = 0.7,
                    overtakingDifficulty = 0.7,
                    tireWear = 0.8
                ),
                Circuit(
                    name = "Sachsenring",
                    country = "Germany",
                    length = 3.67,
                    turns = 13,
                    speedFocus = 0.4,
                    accelerationFocus = 0.5,
                    handlingFocus = 0.9,
                    brakingFocus = 0.7,
                    overtakingDifficulty = 0.8,
                    tireWear = 0.6
                )
            )
        }
    }
}

@Entity(tableName = "races")
data class Race(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    var circuitId: Long,
    var date: Date,
    var season: Int,
    var round: Int,
    var laps: Int = 0,
    var weatherCondition: WeatherCondition = WeatherCondition.SUNNY,
    var temperature: Int = 25,
    var trackCondition: Int = 100,  // 0-100, wet to dry
    var completed: Boolean = false
) {
    companion object {
        fun createSampleRaces(circuits: List<Circuit>, season: Int = 2025): List<Race> {
            val calendar = Calendar.getInstance()
            calendar.set(season, Calendar.MARCH, 10) // Start with March 10
            
            return circuits.mapIndexed { index, circuit ->
                calendar.add(Calendar.DAY_OF_MONTH, 14) // Two weeks between races
                Race(
                    name = "Grand Prix of ${circuit.country}",
                    circuitId = circuit.id,
                    date = calendar.time,
                    season = season,
                    round = index + 1,
                    laps = calculateLaps(circuit.length)
                )
            }
        }
        
        private fun calculateLaps(circuitLength: Double): Int {
            // Target race distance is approximately 110 km
            val targetDistance = 110.0
            val laps = (targetDistance / circuitLength).toInt()
            return maxOf(laps, 15) // Minimum 15 laps
        }
    }
}

@Entity(tableName = "race_results")
data class RaceResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val raceId: Long,
    val riderId: Long,
    val teamId: Long,
    val position: Int,
    val points: Int,
    val finishTime: Double, // Total race time in seconds
    val bestLapTime: Double, // Best lap time in seconds
    val status: RaceStatus = RaceStatus.FINISHED,
    val startPosition: Int,
    val gainedPositions: Int = startPosition - position
)

// Data class for race with circuit information - used for UI display
data class RaceWithCircuit(
    @Embedded val race: Race,
    @Relation(
        parentColumn = "circuitId",
        entityColumn = "id"
    )
    val circuit: Circuit
)

enum class WeatherCondition {
    SUNNY, CLOUDY, LIGHT_RAIN, HEAVY_RAIN;
    
    companion object {
        fun random(): WeatherCondition {
            return values()[kotlin.random.Random.nextInt(values().size)]
        }
    }
}

enum class RaceStatus {
    FINISHED, DNF_CRASH, DNF_MECHANICAL, DNF_OTHER
}
