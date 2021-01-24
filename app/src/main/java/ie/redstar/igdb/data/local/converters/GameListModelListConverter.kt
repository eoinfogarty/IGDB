package ie.redstar.igdb.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ie.redstar.igdb.data.model.GameListModel
import java.lang.reflect.Type

class GameListModelListConverter {

    var gson: Gson = Gson()

    @TypeConverter
    fun toModelList(games: String?): List<GameListModel>? {
        val listType: Type = object : TypeToken<List<GameListModel>>() {}.type
        return gson.fromJson(games, listType);
    }

    @TypeConverter
    fun toListString(games: List<GameListModel>?): String? {
        return gson.toJson(games)
    }
}