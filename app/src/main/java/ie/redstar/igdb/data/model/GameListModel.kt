package ie.redstar.igdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_list_table")
data class GameListModel(
    @PrimaryKey val id: Int,
    val firstReleaseDate: Long?,
    val rating: Double?,
    val ratingCount: Int?,
    val cover: ImageModel?,
    val name: String
)