package com.romainripoll.motogpmanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity(tableName = "game_state")
data class GameState(
    @PrimaryKey
    val id: Int = 1, // Single instance table
    var playerTeamId: Long,
    var currentDate: Date,
    var currentSeason: Int,
    var currentRaceIndex: Int = 0,
    var money: Int = 0,
    var difficulty: GameDifficulty = GameDifficulty.NORMAL,
    var lastSaved: Date = Calendar.getInstance().time
)

enum class GameDifficulty {
    EASY, NORMAL, HARD;
    
    fun budgetMultiplier(): Float {
        return when (this) {
            EASY -> 1.5f
            NORMAL -> 1.0f
            HARD -> 0.7f
        }
    }
    
    fun aiPerformanceMultiplier(): Float {
        return when (this) {
            EASY -> 0.8f
            NORMAL -> 1.0f
            HARD -> 1.2f
        }
    }
    
    fun sponsorOfferMultiplier(): Float {
        return when (this) {
            EASY -> 1.3f
            NORMAL -> 1.0f
            HARD -> 0.8f
        }
    }
}

@Entity(tableName = "sponsors")
data class Sponsor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    var logo: String? = null,
    var basePayment: Int,
    var bonusPerPoint: Int,
    var bonusTopThree: Int,
    var bonusChampionship: Int,
    var contractLength: Int, // In seasons
    var teamId: Long? = null
) {
    companion object {
        fun createSampleSponsors(): List<Sponsor> {
            return listOf(
                Sponsor(
                    name = "Red Bull",
                    basePayment = 10000000,
                    bonusPerPoint = 10000,
                    bonusTopThree = 500000,
                    bonusChampionship = 5000000,
                    contractLength = 3
                ),
                Sponsor(
                    name = "Monster Energy",
                    basePayment = 9000000,
                    bonusPerPoint = 9000,
                    bonusTopThree = 450000,
                    bonusChampionship = 4500000,
                    contractLength = 3
                ),
                Sponsor(
                    name = "Repsol",
                    basePayment = 8500000,
                    bonusPerPoint = 8500,
                    bonusTopThree = 425000,
                    bonusChampionship = 4250000,
                    contractLength = 2
                ),
                Sponsor(
                    name = "Michelin",
                    basePayment = 7000000,
                    bonusPerPoint = 7000,
                    bonusTopThree = 350000,
                    bonusChampionship = 3500000,
                    contractLength = 2
                ),
                Sponsor(
                    name = "Shell",
                    basePayment = 6500000,
                    bonusPerPoint = 6500,
                    bonusTopThree = 325000,
                    bonusChampionship = 3250000,
                    contractLength = 1
                ),
                Sponsor(
                    name = "GoPro",
                    basePayment = 5000000,
                    bonusPerPoint = 5000,
                    bonusTopThree = 250000,
                    bonusChampionship = 2500000,
                    contractLength = 1
                ),
                Sponsor(
                    name = "Oakley",
                    basePayment = 4000000,
                    bonusPerPoint = 4000,
                    bonusTopThree = 200000,
                    bonusChampionship = 2000000,
                    contractLength = 1
                )
            )
        }
    }
}

@Entity(tableName = "championship_standings")
data class ChampionshipStanding(
    @PrimaryKey
    val id: Long = 0,
    val season: Int,
    val riderId: Long,
    val teamId: Long,
    var points: Int = 0,
    var position: Int = 0,
    var wins: Int = 0,
    var podiums: Int = 0,
    var fastestLaps: Int = 0
)

@Entity(tableName = "team_championship_standings")
data class TeamChampionshipStanding(
    @PrimaryKey
    val id: Long = 0,
    val season: Int,
    val teamId: Long,
    var points: Int = 0,
    var position: Int = 0,
    var wins: Int = 0,
    var podiums: Int = 0
)

// Cross reference tables for many-to-many relationships

@Entity(
    tableName = "rider_team_cross_ref",
    primaryKeys = ["riderId", "teamId"]
)
data class RiderTeamCrossRef(
    val riderId: Long,
    val teamId: Long,
    val contractStart: Date,
    val contractEnd: Date,
    val salary: Int
)

@Entity(
    tableName = "sponsor_team_cross_ref",
    primaryKeys = ["sponsorId", "teamId"]
)
data class SponsorTeamCrossRef(
    val sponsorId: Long,
    val teamId: Long,
    val contractStart: Date,
    val contractEnd: Date,
    val payment: Int
)
