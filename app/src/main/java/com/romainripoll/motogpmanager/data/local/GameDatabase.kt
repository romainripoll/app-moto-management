package com.romainripoll.motogpmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.romainripoll.motogpmanager.data.model.*
import java.util.Date

@Database(
    entities = [
        Rider::class,
        Team::class,
        Bike::class,
        BikeManufacturer::class,
        BikeComponent::class,
        Circuit::class,
        Race::class,
        RaceResult::class,
        Sponsor::class,
        GameState::class,
        ChampionshipStanding::class,
        TeamChampionshipStanding::class,
        RiderTeamCrossRef::class,
        SponsorTeamCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun riderDao(): RiderDao
    abstract fun teamDao(): TeamDao
    abstract fun bikeDao(): BikeDao
    abstract fun circuitDao(): CircuitDao
    abstract fun raceDao(): RaceDao
    abstract fun gameStateDao(): GameStateDao
    abstract fun championshipDao(): ChampionshipDao
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    
    @TypeConverter
    fun fromWeatherCondition(value: WeatherCondition): String {
        return value.name
    }
    
    @TypeConverter
    fun toWeatherCondition(value: String): WeatherCondition {
        return WeatherCondition.valueOf(value)
    }
    
    @TypeConverter
    fun fromRaceStatus(value: RaceStatus): String {
        return value.name
    }
    
    @TypeConverter
    fun toRaceStatus(value: String): RaceStatus {
        return RaceStatus.valueOf(value)
    }
    
    @TypeConverter
    fun fromGameDifficulty(value: GameDifficulty): String {
        return value.name
    }
    
    @TypeConverter
    fun toGameDifficulty(value: String): GameDifficulty {
        return GameDifficulty.valueOf(value)
    }
    
    @TypeConverter
    fun fromComponentType(value: ComponentType): String {
        return value.name
    }
    
    @TypeConverter
    fun toComponentType(value: String): ComponentType {
        return ComponentType.valueOf(value)
    }
}
