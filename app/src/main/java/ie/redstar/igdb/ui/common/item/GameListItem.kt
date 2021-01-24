package ie.redstar.igdb.ui.common.item

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import coil.load
import com.xwray.groupie.viewbinding.BindableItem
import ie.redstar.igdb.R
import ie.redstar.igdb.data.model.GameListModel
import ie.redstar.igdb.data.model.ImageSize
import ie.redstar.igdb.databinding.CommonListItemGameBinding
import ie.redstar.igdb.ui.util.getFormattedReleaseDate

class GameListItem(
    private val game: GameListModel,
    private val onClickGame: (Int, String) -> Unit
) : BindableItem<CommonListItemGameBinding>() {

    override fun getLayout() = R.layout.common_list_item_game

    override fun initializeViewBinding(view: View) = CommonListItemGameBinding.bind(view)

    override fun bind(viewBinding: CommonListItemGameBinding, position: Int) {
        viewBinding.coverImage.load(game.cover?.getImageUrl(ImageSize.CoverBig)) {
            crossfade(true)
            error(ColorDrawable(Color.LTGRAY))
        }
        viewBinding.gameTitle.text = game.name
        viewBinding.releaseDate.text = getFormattedReleaseDate(game.firstReleaseDate)

        viewBinding.root.setOnClickListener {
            onClickGame(game.id, game.name)
        }
    }

    override fun getId() = game.id.toLong()

    override fun hashCode() = game.hashCode()

    override fun equals(other: Any?): Boolean {
        return (other as? GameListItem)?.game == game
    }
}