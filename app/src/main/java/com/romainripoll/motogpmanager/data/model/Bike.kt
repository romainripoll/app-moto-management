package com.romainripoll.motogpmanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

enum class BikeManufacturer(
    val displayName: String,
    val baseAcceleration: Int,
    val baseTopSpeed: Int,
    val baseHandling: Int,
    val baseBraking: Int,
    val baseReliability: Int
) {
    DUCATI("Ducati", 90, 95, 75, 80, 70),
    HONDA("Honda", 85, 85, 85, 85, 85),
    YAMAHA("Yamaha", 80, 80, 90, 85, 85),
    KTM("KTM", 85, 90, 75, 80, 75),
    APRILIA("Aprilia", 80, 85, 85, 80, 80),
    SUZUKI("Suzuki", 75, 80, 90, 85, 90)
}

class BikeManufacturerConverter {
    @TypeConverter
    fun fromBikeManufacturer(value: BikeManufacturer): String {
        return value.name
    }

    @TypeConverter
    fun toBikeManufacturer(value: String): BikeManufacturer {
        return BikeManufacturer.valueOf(value)
    }
}

@Entity(tableName = "bikes")
@TypeConverters(BikeManufacturerConverter::class)
data class Bike(
    @PrimaryKey
    val id: String,
    val manufacturer: BikeManufacturer,
    val acceleration: Int,      // 0-100
    val topSpeed: Int,          // 0-100
    val handling: Int,          // 0-100
    val braking: Int,           // 0-100
    val reliability: Int        // 0-100
) {
    val overallPerformance: Int
        get() = (acceleration + topSpeed + handling + braking + reliability) / 5
        
    // Prix de base des améliorations par composant
    companion object {
        const val BASE_ENGINE_UPGRADE_COST = 100000
        const val BASE_CHASSIS_UPGRADE_COST = 80000
        const val BASE_BRAKES_UPGRADE_COST = 60000
        const val BASE_ELECTRONICS_UPGRADE_COST = 40000
        
        // Coût des améliorations augmente avec le niveau actuel
        fun calculateUpgradeCost(baseCost: Int, currentLevel: Int): Int {
            return (baseCost * (1 + currentLevel * 0.5)).toInt()
        }
    }
}
