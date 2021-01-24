package ie.redstar.igdb.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ie.redstar.igdb.NavGraphDirections
import ie.redstar.igdb.R
import ie.redstar.igdb.databinding.DetailFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    private val viewModel by viewModel<DetailViewModel> {
        parametersOf(args.gameId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DetailFragmentBinding.inflate(inflater, container, false)
        requireActivity().title = args.label

        binding.swipeRefresh.setColorSchemeResources(R.color.teal_200)
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        val adapter = DetailAdapter(
            onClickGame = { gameId, label ->
                val action = NavGraphDirections.globalToDetailFragment(
                    gameId = gameId,
                    label = label
                )
                findNavController().navigate(action)
            }
        )

        val layoutManager = LinearLayoutManager(activity)
        binding.detailLoadedState.gameDetailRecycler.layoutManager = layoutManager
        binding.detailLoadedState.gameDetailRecycler.adapter = adapter

        viewModel.state.asLiveData().observe(viewLifecycleOwner) { state ->
            when (state) {
                DetailViewModel.State.Initial -> {
                    binding.viewFlipper.displayedChild = 0
                }
                is DetailViewModel.State.Loaded -> {
                    binding.viewFlipper.displayedChild = 1
                    binding.swipeRefresh.isRefreshing = false
                    adapter.updateGame(state.game)
                }
                DetailViewModel.State.Empty -> {
                    binding.viewFlipper.displayedChild = 2
                    binding.swipeRefresh.isRefreshing = false
                }
                DetailViewModel.State.UnAuthorized -> {
                    binding.viewFlipper.displayedChild = 3
                    binding.swipeRefresh.isRefreshing = false
                }
                DetailViewModel.State.Loading  ->  {
                    // do nothing
                }
            }
        }

        return binding.root
    }
}