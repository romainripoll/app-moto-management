package com.romainripoll.motogpmanager.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.romainripoll.motogpmanager.data.model.*
import java.util.Date

@Dao
interface RiderDao {
    @Query("SELECT * FROM riders")
    fun getAllRiders(): LiveData<List<Rider>>
    
    @Query("SELECT * FROM riders WHERE id = :id")
    fun getRiderById(id: Long): LiveData<Rider>
    
    @Query("SELECT * FROM riders WHERE teamId = :teamId")
    fun getRidersByTeam(teamId: Long): LiveData<List<Rider>>
    
    @Query("SELECT * FROM riders WHERE teamId IS NULL")
    fun getFreeAgentRiders(): LiveData<List<Rider>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRider(rider: Rider): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRiders(riders: List<Rider>): List<Long>
    
    @Update
    suspend fun updateRider(rider: Rider)
    
    @Delete
    suspend fun deleteRider(rider: Rider)
    
    @Query("DELETE FROM riders")
    suspend fun deleteAllRiders()
}

@Dao
interface TeamDao {
    @Query("SELECT * FROM teams")
    fun getAllTeams(): LiveData<List<Team>>
    
    @Query("SELECT * FROM teams WHERE id = :id")
    fun getTeamById(id: Long): LiveData<Team>
    
    @Query("SELECT * FROM teams WHERE isPlayerTeam = 1 LIMIT 1")
    fun getPlayerTeam(): LiveData<Team>
    
    @Transaction
    @Query("SELECT * FROM teams")
    fun getTeamsWithRiders(): LiveData<List<TeamWithRiders>>
    
    @Transaction
    @Query("SELECT * FROM teams WHERE id = :teamId")
    fun getTeamWithRiders(teamId: Long): LiveData<TeamWithRiders>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: Team): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeams(teams: List<Team>): List<Long>
    
    @Update
    suspend fun updateTeam(team: Team)
    
    @Delete
    suspend fun deleteTeam(team: Team)
    
    @Query("DELETE FROM teams")
    suspend fun deleteAllTeams()
}

data class TeamWithRiders(
    @androidx.room.Embedded
    val team: Team,
    @androidx.room.Relation(
        parentColumn = "id",
        entityColumn = "teamId"
    )
    val riders: List<Rider>
)

@Dao
interface BikeDao {
    @Query("SELECT * FROM bikes")
    fun getAllBikes(): LiveData<List<Bike>>
    
    @Query("SELECT * FROM bikes WHERE id = :id")
    fun getBikeById(id: Long): LiveData<Bike>
    
    @Query("SELECT * FROM bikes WHERE teamId = :teamId")
    fun getBikeByTeam(teamId: Long): LiveData<Bike>
    
    @Query("SELECT * FROM bike_manufacturers")
    fun getAllManufacturers(): LiveData<List<BikeManufacturer>>
    
    @Query("SELECT * FROM bike_manufacturers WHERE id = :id")
    fun getManufacturerById(id: Long): LiveData<BikeManufacturer>
    
    @Query("SELECT * FROM bike_components WHERE bikeId = :bikeId")
    fun getComponentsByBike(bikeId: Long): LiveData<List<BikeComponent>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBike(bike: Bike): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBikes(bikes: List<Bike>): List<Long>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManufacturer(manufacturer: BikeManufacturer): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManufacturers(manufacturers: List<BikeManufacturer>): List<Long>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponent(component: BikeComponent): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponents(components: List<BikeComponent>): List<Long>
    
    @Update
    suspend fun updateBike(bike: Bike)
    
    @Update
    suspend fun updateComponent(component: BikeComponent)
    
    @Delete
    suspend fun deleteBike(bike: Bike)
    
    @Delete
    suspend fun deleteComponent(component: BikeComponent)
}

@Dao
interface CircuitDao {
    @Query("SELECT * FROM circuits")
    fun getAllCircuits(): LiveData<List<Circuit>>
    
    @Query("SELECT * FROM circuits WHERE id = :id")
    fun getCircuitById(id: Long): LiveData<Circuit>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCircuit(circuit: Circuit): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCircuits(circuits: List<Circuit>): List<Long>
    
    @Update
    suspend fun updateCircuit(circuit: Circuit)
    
    @Delete
    suspend fun deleteCircuit(circuit: Circuit)
}

@Dao
interface RaceDao {
    @Query("SELECT * FROM races")
    fun getAllRaces(): LiveData<List<Race>>
    
    @Query("SELECT * FROM races WHERE id = :id")
    fun getRaceById(id: Long): LiveData<Race>
    
    @Query("SELECT * FROM races WHERE season = :season ORDER BY round")
    fun getRacesBySeason(season: Int): LiveData<List<Race>>
    
    @Query("SELECT * FROM races WHERE date >= :date ORDER BY date LIMIT 1")
    fun getNextRace(date: Date): LiveData<Race>
    
    @Query("SELECT * FROM race_results WHERE raceId = :raceId ORDER BY position")
    fun getRaceResults(raceId: Long): LiveData<List<RaceResult>>
    
    @Query("SELECT * FROM race_results WHERE riderId = :riderId")
    fun getRiderResults(riderId: Long): LiveData<List<RaceResult>>
    
    @Transaction
    @Query("SELECT * FROM races")
    fun getRacesWithCircuits(): LiveData<List<RaceWithCircuit>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRace(race: Race): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRaces(races: List<Race>): List<Long>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRaceResult(raceResult: RaceResult): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRaceResults(raceResults: List<RaceResult>): List<Long>
    
    @Update
    suspend fun updateRace(race: Race)
    
    @Delete
    suspend fun deleteRace(race: Race)
}

@Dao
interface GameStateDao {
    @Query("SELECT * FROM game_state LIMIT 1")
    fun getGameState(): LiveData<GameState>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameState(gameState: GameState)
    
    @Update
    suspend fun updateGameState(gameState: GameState)
    
    @Query("SELECT * FROM sponsors")
    fun getAllSponsors(): LiveData<List<Sponsor>>
    
    @Query("SELECT * FROM sponsors WHERE teamId = :teamId")
    fun getSponsorsByTeam(teamId: Long): LiveData<List<Sponsor>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSponsor(sponsor: Sponsor): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSponsors(sponsors: List<Sponsor>): List<Long>
    
    @Update
    suspend fun updateSponsor(sponsor: Sponsor)
    
    @Delete
    suspend fun deleteSponsor(sponsor: Sponsor)
}

@Dao
interface ChampionshipDao {
    @Query("SELECT * FROM championship_standings WHERE season = :season ORDER BY position")
    fun getRiderStandings(season: Int): LiveData<List<ChampionshipStanding>>
    
    @Query("SELECT * FROM team_championship_standings WHERE season = :season ORDER BY position")
    fun getTeamStandings(season: Int): LiveData<List<TeamChampionshipStanding>>
    
    @Query("SELECT * FROM championship_standings WHERE season = :season AND riderId = :riderId")
    fun getRiderStanding(season: Int, riderId: Long): LiveData<ChampionshipStanding>
    
    @Query("SELECT * FROM team_championship_standings WHERE season = :season AND teamId = :teamId")
    fun getTeamStanding(season: Int, teamId: Long): LiveData<TeamChampionshipStanding>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRiderStanding(standing: ChampionshipStanding): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamStanding(standing: TeamChampionshipStanding): Long
    
    @Update
    suspend fun updateRiderStanding(standing: ChampionshipStanding)
    
    @Update
    suspend fun updateTeamStanding(standing: TeamChampionshipStanding)
    
    @Query("DELETE FROM championship_standings WHERE season = :season")
    suspend fun clearRiderStandings(season: Int)
    
    @Query("DELETE FROM team_championship_standings WHERE season = :season")
    suspend fun clearTeamStandings(season: Int)
}
