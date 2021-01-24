package ie.redstar.igdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ie.redstar.igdb.data.model.GameDetailModel

@Dao
interface GameDetailDao {

    @Query("SELECT * FROM game_detail_table WHERE id == :id")
    suspend fun getGamesDetail(id: Int): GameDetailModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(game: GameDetailModel)

    @Query("DELETE FROM game_detail_table")
    suspend fun deleteAll()
}