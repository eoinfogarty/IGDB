package ie.redstar.igdb.ui.main

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import ie.redstar.igdb.R
import ie.redstar.igdb.data.model.GameListModel
import ie.redstar.igdb.ui.common.item.GameListItem
import ie.redstar.igdb.ui.common.item.SectionTitleListItem

class MainListAdapter(
    private val onClickGame: (Int, String) -> Unit
) : GroupAdapter<GroupieViewHolder>() {

    fun updateGameList(games: List<GameListModel>) {
        val items = mutableListOf<BindableItem<*>>()

        items.add(SectionTitleListItem(R.string.highly_rated_games))
        games.map { game ->
            items.add(GameListItem(game, onClickGame))
        }

        update(items)
    }
}
