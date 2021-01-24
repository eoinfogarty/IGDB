package ie.redstar.igdb.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ie.redstar.igdb.NavGraphDirections
import ie.redstar.igdb.R
import ie.redstar.igdb.databinding.MainFragmentBinding
import ie.redstar.igdb.ui.util.EndlessScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedState: Bundle?
    ): View {
        val binding = MainFragmentBinding.inflate(inflater, container, false)
        requireActivity().title = getString(R.string.app_name)

        val adapter = MainListAdapter(
            onClickGame = { gameId, label ->
                val action = NavGraphDirections.globalToDetailFragment(
                    gameId = gameId,
                    label = label
                )
                findNavController().navigate(action)
            }
        )

        val layoutManager = LinearLayoutManager(activity)
        binding.mainLoadedState.gameListRecycler.layoutManager = layoutManager
        binding.mainLoadedState.gameListRecycler.adapter = adapter
        val scrollListener = object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore() {
                viewModel.loadMore()
            }
        }
        binding.mainLoadedState.gameListRecycler.addOnScrollListener(scrollListener)

        binding.swipeRefresh.setColorSchemeResources(R.color.teal_200)
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.state.asLiveData().observe(viewLifecycleOwner) { state ->
            when (state) {
                MainViewModel.State.Loading -> {
                    binding.viewFlipper.displayedChild = 0
                }
                is MainViewModel.State.Loaded -> {
                    binding.viewFlipper.displayedChild = 1
                    binding.swipeRefresh.isRefreshing = false
                    adapter.updateGameList(state.gameList)
                }
                MainViewModel.State.Empty -> {
                    binding.viewFlipper.displayedChild = 2
                    binding.swipeRefresh.isRefreshing = false
                }
                MainViewModel.State.UnAuthorized -> {
                    binding.viewFlipper.displayedChild = 3
                    binding.swipeRefresh.isRefreshing = false
                }
                MainViewModel.State.Refreshing -> {
                    scrollListener.resetState()
                }
                MainViewModel.State.Initial -> {
                    // do nothing
                }
                MainViewModel.State.LoadingMore -> {
                    //  todo add loading indicator to bottom of recyclerview
                }
            }
        }

        return binding.root
    }
}