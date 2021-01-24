package ie.redstar.igdb.ui.detail

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import ie.redstar.igdb.R
import ie.redstar.igdb.data.model.GameDetailModel
import ie.redstar.igdb.ui.common.item.GameListItem
import ie.redstar.igdb.ui.common.item.SectionTitleListItem
import ie.redstar.igdb.ui.detail.item.GenreChipItem
import ie.redstar.igdb.ui.detail.item.HeaderItem
import ie.redstar.igdb.ui.detail.item.SummaryItem

class DetailAdapter(
    private val onClickGame: (Int, String) -> Unit
) : GroupAdapter<GroupieViewHolder>() {

    fun updateGame(game: GameDetailModel) {
        val items = mutableListOf<BindableItem<*>>()

        items.addAll(
            listOf(
                HeaderItem(game),
                SectionTitleListItem(R.string.summary),
                SummaryItem(game.summary),
            )
        )

        game.genres?.let {
            items.addAll(
                listOf(
                    SectionTitleListItem(R.string.genres),
                    GenreChipItem(game.genres)
                )
            )
        }

        game.similarGames?.let {
            items.add(SectionTitleListItem(R.string.similar_games))
            it.map { gameListModel ->
                items.add(GameListItem(gameListModel, onClickGame))
            }
        }

        update(items)
    }
}
