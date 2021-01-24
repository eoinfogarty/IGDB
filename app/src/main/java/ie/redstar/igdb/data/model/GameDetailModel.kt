package ie.redstar.igdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_detail_table")
data class GameDetailModel(
    @PrimaryKey val id: Int,
    val firstReleaseDate: Long?,
    val name: String,
    val summary: String,
    val storyline: String?,
    val cover: ImageModel?,
    val rating: Double?,
    val screenshots: List<ImageModel>?,
    val genres: List<GenreModel>?,
    val similarGames: List<GameListModel>?
)