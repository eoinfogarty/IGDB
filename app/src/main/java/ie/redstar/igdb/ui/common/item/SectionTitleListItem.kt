package ie.redstar.igdb.ui.common.item

import android.view.View
import androidx.annotation.StringRes
import com.xwray.groupie.viewbinding.BindableItem
import ie.redstar.igdb.R
import ie.redstar.igdb.databinding.CommonListItemTitleBinding

class SectionTitleListItem(
    @StringRes private val title: Int
) : BindableItem<CommonListItemTitleBinding>() {

    override fun getLayout() = R.layout.common_list_item_title

    override fun initializeViewBinding(view: View) = CommonListItemTitleBinding.bind(view)

    override fun bind(viewBinding: CommonListItemTitleBinding, position: Int) {
        viewBinding.title.text = viewBinding.root.context.getString(title)
    }

    override fun getId() = title.hashCode().toLong()

    override fun hashCode() = title.hashCode()

    override fun equals(other: Any?): Boolean {
        return (other as? SectionTitleListItem)?.title == title
    }
}