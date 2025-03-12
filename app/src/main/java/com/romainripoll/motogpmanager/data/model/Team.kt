package com.romainripoll.motogpmanager.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class Team(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    var logo: String? = null,
    var primaryColor: String,
    var secondaryColor: String,
    var budget: Int,
    var reputation: Int,      // 0-100, affects rider negotiations and sponsor offers
    var facilityLevel: Int,   // 1-10, affects development speed
    var staffQuality: Int,    // 1-10, affects performance and reliability
    var isPlayerTeam: Boolean = false,
    var bikeManufacturerId: Long? = null
) {
    // Relationships that will be loaded from other tables
    @Ignore
    var riders: List<Rider> = emptyList()
    
    @Ignore
    var bike: Bike? = null
    
    @Ignore
    var sponsors: List<Sponsor> = emptyList()
    
    companion object {
        // Factory method to create sample teams for testing or initial game setup
        fun createSampleTeams(): List<Team> {
            return listOf(
                Team(
                    name = "Ducati Factory Team",
                    primaryColor = "#e10600",
                    secondaryColor = "#ffffff",
                    budget = 50000000,
                    reputation = 95,
                    facilityLevel = 10,
                    staffQuality = 9
                ),
                Team(
                    name = "Monster Yamaha",
                    primaryColor = "#000000",
                    secondaryColor = "#3db54a",
                    budget = 45000000,
                    reputation = 90,
                    facilityLevel = 9,
                    staffQuality = 8
                ),
                Team(
                    name = "Repsol Honda",
                    primaryColor = "#ff7d1e",
                    secondaryColor = "#ffffff",
                    budget = 48000000,
                    reputation = 93,
                    facilityLevel = 10,
                    staffQuality = 9
                ),
                Team(
                    name = "Red Bull KTM",
                    primaryColor = "#0067b1",
                    secondaryColor = "#ff800c",
                    budget = 40000000,
                    reputation = 85,
                    facilityLevel = 8,
                    staffQuality = 8
                ),
                Team(
                    name = "Aprilia Racing",
                    primaryColor = "#000000",
                    secondaryColor = "#c2032f",
                    budget = 35000000,
                    reputation = 80,
                    facilityLevel = 7,
                    staffQuality = 7
                ),
                Team(
                    name = "Suzuki Ecstar",
                    primaryColor = "#00539d",
                    secondaryColor = "#e5e5e5",
                    budget = 38000000,
                    reputation = 83,
                    facilityLevel = 8,
                    staffQuality = 7
                ),
                Team(
                    name = "Pramac Racing",
                    primaryColor = "#004a98",
                    secondaryColor = "#e50019",
                    budget = 30000000,
                    reputation = 75,
                    facilityLevel = 6,
                    staffQuality = 6
                ),
                Team(
                    name = "Tech3 KTM",
                    primaryColor = "#ff800c",
                    secondaryColor = "#000000",
                    budget = 28000000,
                    reputation = 70,
                    facilityLevel = 6,
                    staffQuality = 6
                )
            )
        }
    }
}
