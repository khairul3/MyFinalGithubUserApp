package com.khairul.myfinalgithubuserapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.khairul.myfinalgithubuserapp.R
import com.khairul.myfinalgithubuserapp.adapter.UserAdapter
import com.khairul.myfinalgithubuserapp.databinding.HomeFragmentBinding
import com.khairul.myfinalgithubuserapp.util.State.*
import com.khairul.myfinalgithubuserapp.viewModel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var homeBind: HomeFragmentBinding
    private lateinit var homeAdap: UserAdapter
    private val homeModel: HomeViewModel by navGraphViewModels(R.id.my_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBind = HomeFragmentBinding.inflate(layoutInflater, container, false)
        return homeBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)


        homeBind.errLayout.emptyText.text = resources.getString(R.string.search_hint)
        homeAdap = UserAdapter(arrayListOf(), fun(username: String, iv: View) {
            findNavController().navigate(
                HomeFragmentDirection.detailsAction(username),
                FragmentNavigatorExtras(
                    iv to username
                )
            )
        })

        homeBind.recyclerHome.apply {

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdap
        }

        homeBind.searchView.apply {
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeModel.setSearch(query)
                    homeBind.searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean = false
            })
        }

        observe()
    }

    class HomeFragmentDirection private constructor() {
        private data class DetailsAction(
            val Username: String
        ) : NavDirections {
            override fun getActionId(): Int = R.id.details_action

            override fun getArguments(): Bundle {
                val result = Bundle()
                result.putString("Username", this.Username)
                return result
            }
        }

        companion object {
            fun detailsAction(Username: String): NavDirections = DetailsAction(Username)

        }
    }

    private fun observe() {
        homeModel.searchResult.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.state) {
                    SUCCESS -> {
                        resource.data?.let { users ->
                            when {
                                !users.isNullOrEmpty() -> {
                                    homeSuccess(homeBind)
                                    homeAdap.setData(users)
                                }
                                else -> {
                                    homeError(homeBind, null)
                                }
                            }
                        }
                    }
                    LOADING -> homeLoading(homeBind)
                    ERROR -> homeError(homeBind, it.message)
                }
            }
        })
    }


    private fun homeLoading(homeBind: HomeFragmentBinding): HomeFragmentBinding {
        homeBind.errLayout.mainNotFound.visibility = View.GONE
        homeBind.progress.start()
        homeBind.recyclerHome.visibility = View.GONE
        return homeBind
    }

    private fun homeSuccess(homeBind: HomeFragmentBinding): HomeFragmentBinding {
        homeBind.also {
            it.errLayout.mainNotFound.visibility = View.GONE
            it.progress.stop()
            it.recyclerHome.visibility = View.VISIBLE
        }
        return homeBind
    }

    private fun homeError(homeBind: HomeFragmentBinding, message: String?): HomeFragmentBinding {
        homeBind.apply {
            errLayout.apply {
                mainNotFound.visibility = View.VISIBLE
                emptyText.text = message ?: resources.getString(R.string.not_found)
            }
            progress.stop()
            recyclerHome.visibility = View.GONE
        }
        return homeError(homeBind, message)
    }
}