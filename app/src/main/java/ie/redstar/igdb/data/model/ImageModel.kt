package ie.redstar.igdb.data.model

data class ImageModel(
    val imageId: String
) {
    // For documentation for image and sizes
    // see https://api-docs.igdb.com/?shell#images
    fun getImageUrl(imageSize: ImageSize): String {
        return "https://images.igdb.com/igdb/image/upload/t_${imageSize.size}/${imageId}.jpg"
    }
}

sealed class ImageSize(val size: String) {
    object CoverSmall: ImageSize("cover_small")
    object ScreenshotMed: ImageSize("screenshot_med")
    object CoverBig: ImageSize("cover_big")
    object LogoMed: ImageSize("logo_med")
    object ScreenshotBig: ImageSize("screenshot_big")
    object ScreenshotHuge: ImageSize("screenshot_huge")
    object Thumb: ImageSize("thumb")
    object Micro: ImageSize("micro")
    object Size720p: ImageSize("720p")
    object Size080p: ImageSize("1080p")
}
