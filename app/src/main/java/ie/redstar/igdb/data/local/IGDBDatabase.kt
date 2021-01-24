package ie.redstar.igdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.redstar.igdb.data.local.converters.*
import ie.redstar.igdb.data.model.GameDetailModel
import ie.redstar.igdb.data.model.GameListModel

@Database(entities = [GameListModel::class, GameDetailModel::class], version = 1, exportSchema = false)
@TypeConverters(
    ImageModelConverter::class,
    ImageModelListConverter::class,
    GenreModelListConverter::class,
    GameListModelListConverter::class
)
abstract class IGDBDatabase : RoomDatabase() {

    abstract fun gameListDao(): GameListDao

    abstract fun gameDetailDao(): GameDetailDao
}