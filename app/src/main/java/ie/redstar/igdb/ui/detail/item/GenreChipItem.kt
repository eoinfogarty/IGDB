package ie.redstar.igdb.ui.detail.item

import android.view.View
import com.google.android.material.chip.Chip
import com.xwray.groupie.viewbinding.BindableItem
import ie.redstar.igdb.R
import ie.redstar.igdb.data.model.GenreModel
import ie.redstar.igdb.databinding.DetailListItemGenresBinding

class GenreChipItem(
    private val genres: List<GenreModel>
) : BindableItem<DetailListItemGenresBinding>() {

    override fun getLayout() = R.layout.detail_list_item_genres

    override fun initializeViewBinding(view: View): DetailListItemGenresBinding {
        val binding = DetailListItemGenresBinding.bind(view)
        genres.forEach {
            val chip = Chip(binding.root.context)
            chip.text = it.name
            binding.chipGroup.addView(chip)
        }
        return binding
    }

    override fun bind(viewBinding: DetailListItemGenresBinding, position: Int) {

    }

    override fun getId() = genres.hashCode().toLong()

    override fun hashCode() = genres.hashCode()

    override fun equals(other: Any?): Boolean {
        return (other as? GenreChipItem)?.genres == genres
    }
}