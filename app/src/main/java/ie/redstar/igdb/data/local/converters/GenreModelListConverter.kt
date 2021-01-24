package ie.redstar.igdb.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ie.redstar.igdb.data.model.GenreModel
import java.lang.reflect.Type

class GenreModelListConverter {

    var gson = Gson()

    @TypeConverter
    fun toModelList(genres: String?): List<GenreModel>? {
        val listType: Type = object : TypeToken<List<GenreModel>>() {}.type
        return gson.fromJson(genres, listType);
    }

    @TypeConverter
    fun toListString(genres: List<GenreModel>?): String? {
        return gson.toJson(genres)
    }
}