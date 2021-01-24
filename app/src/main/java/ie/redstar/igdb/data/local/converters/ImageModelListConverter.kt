package ie.redstar.igdb.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ie.redstar.igdb.data.model.ImageModel
import java.lang.reflect.Type

class ImageModelListConverter {

    var gson = Gson()

    @TypeConverter
    fun toModelList(images: String?): List<ImageModel>? {
        val listType: Type = object : TypeToken<List<ImageModel>>() {}.type
        return gson.fromJson(images, listType);
    }

    @TypeConverter
    fun toListString(images: List<ImageModel>?): String? {
        return gson.toJson(images)
    }
}