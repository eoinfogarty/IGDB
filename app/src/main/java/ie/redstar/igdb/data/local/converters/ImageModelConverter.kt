package ie.redstar.igdb.data.local.converters

import androidx.room.TypeConverter
import ie.redstar.igdb.data.model.ImageModel

class ImageModelConverter {

    @TypeConverter
    fun toImageModel(imageId: String?): ImageModel? {
        return if (imageId != null) {
            ImageModel(imageId)
        } else {
            null
        }
    }

    @TypeConverter
    fun toImageId(imageModel: ImageModel?): String? {
        return imageModel?.imageId

    }
}