package ie.redstar.igdb.ui.detail.item

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ie.redstar.igdb.R
import ie.redstar.igdb.databinding.DetailListItemSummaryBinding

class SummaryItem(
    private val summary: String
) : BindableItem<DetailListItemSummaryBinding>() {

    override fun getLayout() = R.layout.detail_list_item_summary

    override fun initializeViewBinding(view: View) = DetailListItemSummaryBinding.bind(view)

    override fun bind(viewBinding: DetailListItemSummaryBinding, position: Int) {
        viewBinding.summary.text = summary
    }

    override fun getId() = summary.hashCode().toLong()

    override fun hashCode() = summary.hashCode()

    override fun equals(other: Any?): Boolean {
        return (other as? SummaryItem)?.summary == summary
    }
}