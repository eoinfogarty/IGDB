package ie.redstar.igdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ie.redstar.igdb.data.model.GameListModel

@Dao
interface GameListDao {

    @Query("SELECT * FROM game_list_table WHERE ratingCount > 50 ORDER BY rating DESC")
    suspend fun getGamesList(): List<GameListModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: List<GameListModel>)

    @Query("DELETE FROM game_list_table")
    suspend fun deleteAll()
}