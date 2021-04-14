package com.khairul.myfinalgithubuserapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.khairul.myfinalgithubuserapp.R
import com.khairul.myfinalgithubuserapp.adapter.UserFavoriteAdapter
import com.khairul.myfinalgithubuserapp.databinding.FavoriteFragmentBinding
import com.khairul.myfinalgithubuserapp.util.ShowStates
import com.khairul.myfinalgithubuserapp.viewModel.FavoriteViewModel

class FavoriteFragment : Fragment(), ShowStates {
    private lateinit var binding: FavoriteFragmentBinding
    private lateinit var favAdapter: UserFavoriteAdapter
    private val favModel: FavoriteViewModel by navGraphViewModels(R.id.my_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = context?.resources?.getString(R.string.favorite)
        binding = FavoriteFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favAdapter = UserFavoriteAdapter(arrayListOf()) { username, iv ->
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailsDestination(username),
                FragmentNavigatorExtras(iv to username)
            )
        }
        binding.recyclerFav.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favAdapter
        }
        observeFavorite()
    }

    private fun observeFavorite() {
        favoriteLoading(binding)
        favModel.dataFavorite.observe(viewLifecycleOwner, Observer {
            it?.let { users ->
                if (!users.isNullOrEmpty()) {
                    favoriteSuccess(binding)
                    favAdapter.setData(users)
                } else {
                    favoriteError(
                        binding,
                        resources.getString(
                            R.string.not_have,
                            "",
                            resources.getString(R.string.favorite)
                        )
                    )
                }
            }
        })
    }

    override fun favoriteLoading(favoriteFragmentBinding: FavoriteFragmentBinding): Int? {
        binding.apply {
            errlayout.mainNotFound.visibility = gone
            progress.start()
            recyclerFav.visibility = gone
        }
        return super.favoriteLoading(favoriteFragmentBinding)
    }

    override fun favoriteSuccess(favoriteFragmentBinding: FavoriteFragmentBinding): Int? {
        binding.apply {
            errlayout.mainNotFound.visibility = gone
            progress.stop()
            recyclerFav.visibility = visible
        }
        return super.favoriteSuccess(favoriteFragmentBinding)
    }

    override fun favoriteError(
        favoriteFragmentBinding: FavoriteFragmentBinding,
        message: String?
    ): Int? {
        binding.apply {
            errlayout.apply {
                mainNotFound.visibility = visible
                emptyText.text = message ?: resources.getString(R.string.not_found)
            }
            progress.stop()
            recyclerFav.visibility = gone
        }
        return super.favoriteError(favoriteFragmentBinding, message)
    }

    class FavoriteFragmentDirections private constructor() {
        private data class ActionFavoriteFragmentToDetailsDestination(
            val Username: String
        ) : NavDirections {
            override fun getActionId(): Int =
                R.id.action_favoriteFragment_to_details_destination

            override fun getArguments(): Bundle {
                val result = Bundle()
                result.putString("Username", this.Username)
                return result
            }
        }

        companion object {
            fun actionFavoriteFragmentToDetailsDestination(Username: String): NavDirections =
                ActionFavoriteFragmentToDetailsDestination(Username)
        }
    }
}
