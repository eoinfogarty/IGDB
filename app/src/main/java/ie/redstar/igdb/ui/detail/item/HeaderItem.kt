package ie.redstar.igdb.ui.detail.item

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import coil.load
import com.xwray.groupie.viewbinding.BindableItem
import ie.redstar.igdb.R
import ie.redstar.igdb.data.model.GameDetailModel
import ie.redstar.igdb.data.model.ImageSize
import ie.redstar.igdb.databinding.DetailListItemHeaderBinding
import ie.redstar.igdb.ui.util.getFormattedReleaseDate

class HeaderItem(
    private val game: GameDetailModel
) : BindableItem<DetailListItemHeaderBinding>() {

    override fun getLayout() = R.layout.detail_list_item_header

    override fun initializeViewBinding(view: View) = DetailListItemHeaderBinding.bind(view)

    override fun bind(viewBinding: DetailListItemHeaderBinding, position: Int) {
        viewBinding.gameTitle.text = game.name
        viewBinding.releaseDate.text = getFormattedReleaseDate(game.firstReleaseDate)
        viewBinding.ratigFAB.text = if (game.rating != null) {
            String.format("%.1f", game.rating)
        } else {
            "N/A"
        }

        viewBinding.coverImage.load(game.cover?.getImageUrl(ImageSize.CoverBig)) {
            crossfade(true)
            error(ColorDrawable(Color.LTGRAY))
        }
        viewBinding.screenshot.load(game.screenshots?.first()?.getImageUrl(ImageSize.ScreenshotBig))
    }

    override fun getId() = game.hashCode().toLong()

    override fun hashCode() = game.hashCode()

    override fun equals(other: Any?): Boolean {
        return (other as? HeaderItem)?.game == game
    }
}