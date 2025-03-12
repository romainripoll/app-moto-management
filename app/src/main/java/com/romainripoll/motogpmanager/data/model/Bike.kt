package com.romainripoll.motogpmanager.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "bikes")
data class Bike(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var manufacturerId: Long,
    var model: String,
    var year: Int,
    var topSpeed: Int,       // 0-100, maximum speed capability
    var acceleration: Int,   // 0-100, acceleration capability
    var handling: Int,       // 0-100, handling in corners
    var braking: Int,        // 0-100, braking efficiency
    var reliability: Int,    // 0-100, reliability during races
    var teamId: Long? = null // The team that owns this bike
) {
    // Components that will be loaded from other tables
    @Ignore
    var components: MutableMap<ComponentType, BikeComponent> = mutableMapOf()
    
    companion object {
        // Factory method to create sample bikes for testing or initial game setup
        fun createSampleBikes(manufacturers: List<BikeManufacturer>): List<Bike> {
            return listOf(
                Bike(
                    manufacturerId = manufacturers.find { it.name == "Ducati" }?.id ?: 1,
                    model = "Desmosedici GP25",
                    year = 2025,
                    topSpeed = 95,
                    acceleration = 92,
                    handling = 85,
                    braking = 90,
                    reliability = 87
                ),
                Bike(
                    manufacturerId = manufacturers.find { it.name == "Honda" }?.id ?: 2,
                    model = "RC213V",
                    year = 2025,
                    topSpeed = 91,
                    acceleration = 88,
                    handling = 90,
                    braking = 92,
                    reliability = 93
                ),
                Bike(
                    manufacturerId = manufacturers.find { it.name == "Yamaha" }?.id ?: 3,
                    model = "YZR-M1",
                    year = 2025,
                    topSpeed = 88,
                    acceleration = 87,
                    handling = 95,
                    braking = 89,
                    reliability = 91
                ),
                Bike(
                    manufacturerId = manufacturers.find { it.name == "KTM" }?.id ?: 4,
                    model = "RC16",
                    year = 2025,
                    topSpeed = 90,
                    acceleration = 93,
                    handling = 86,
                    braking = 86,
                    reliability = 85
                ),
                Bike(
                    manufacturerId = manufacturers.find { it.name == "Aprilia" }?.id ?: 5,
                    model = "RS-GP",
                    year = 2025,
                    topSpeed = 87,
                    acceleration = 88,
                    handling = 89,
                    braking = 85,
                    reliability = 84
                ),
                Bike(
                    manufacturerId = manufacturers.find { it.name == "Suzuki" }?.id ?: 6,
                    model = "GSX-RR",
                    year = 2025,
                    topSpeed = 86,
                    acceleration = 86,
                    handling = 92,
                    braking = 88,
                    reliability = 90
                )
            )
        }
    }
}

@Entity(tableName = "bike_manufacturers")
data class BikeManufacturer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val country: String,
    val logo: String? = null,
    val primaryColor: String,
    val reputationBonus: Int // 0-20, extra reputation for teams using this manufacturer
) {
    companion object {
        // Factory method to create sample manufacturers
        fun createSampleManufacturers(): List<BikeManufacturer> {
            return listOf(
                BikeManufacturer(
                    name = "Ducati",
                    country = "Italy",
                    primaryColor = "#e10600",
                    reputationBonus = 20
                ),
                BikeManufacturer(
                    name = "Honda",
                    country = "Japan",
                    primaryColor = "#ff7d1e",
                    reputationBonus = 18
                ),
                BikeManufacturer(
                    name = "Yamaha",
                    country = "Japan",
                    primaryColor = "#000000",
                    reputationBonus = 17
                ),
                BikeManufacturer(
                    name = "KTM",
                    country = "Austria",
                    primaryColor = "#ff800c",
                    reputationBonus = 15
                ),
                BikeManufacturer(
                    name = "Aprilia",
                    country = "Italy",
                    primaryColor = "#000000",
                    reputationBonus = 14
                ),
                BikeManufacturer(
                    name = "Suzuki",
                    country = "Japan",
                    primaryColor = "#00539d",
                    reputationBonus = 16
                )
            )
        }
    }
}

enum class ComponentType {
    ENGINE, CHASSIS, SUSPENSION, BRAKES, ELECTRONICS, AERODYNAMICS
}

@Entity(tableName = "bike_components")
data class BikeComponent(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: ComponentType,
    var name: String,
    var level: Int,           // 1-10, component level
    var weight: Int,          // Weight in grams
    var reliability: Int,     // 0-100, component reliability
    var performance: Int,     // 0-100, component performance
    var cost: Int,            // Cost in game currency
    var developmentTime: Int, // Development time in days
    var bikeId: Long? = null  // The bike this component belongs to
)
