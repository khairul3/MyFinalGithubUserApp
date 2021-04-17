package com.khairul.myfinalgithubuserapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.khairul.myfinalgithubuserapp.R
import com.khairul.myfinalgithubuserapp.databinding.DetailFragmentBinding
import com.khairul.myfinalgithubuserapp.model.GithubUserModel
import com.khairul.myfinalgithubuserapp.util.State
import com.khairul.myfinalgithubuserapp.viewModel.DetailViewModel
import com.shashank.sony.fancytoastlib.FancyToast

class DetailFragment : Fragment() {
    private lateinit var binding: DetailFragmentBinding
    private lateinit var adapter: PagerAdapter
    private lateinit var model: DetailViewModel
    private lateinit var user: GithubUserModel
    private val args: DetailFragmentArgs by navArgs()

    data class DetailFragmentArgs(
        val Username: String
    ) : NavArgs {
        fun toBundle(): Bundle {
            val result = Bundle()
            result.putString("Username", this.Username)
            return result
        }

        companion object {
            @JvmStatic
            fun fromBundle(bundle: Bundle): DetailFragmentArgs {
                bundle.classLoader = DetailFragmentArgs::class.java.classLoader
                val __Username: String?
                when {
                    bundle.containsKey("Username") -> {
                        __Username = bundle.getString("Username")
                        if (__Username == null) {
                            throw IllegalArgumentException("Argument \"Username\" is marked as non-null but was passed a null value.")
                        }
                    }
                    else -> {
                        throw IllegalArgumentException("Required argument \"Username\" is missing and does not have an android:defaultValue")
                    }
                }
                return DetailFragmentArgs(__Username)
            }
        }
    }

    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(
            this
        ).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        observeDetail()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.content.transitionName = args.Username
        binding.fabFavorite.setOnClickListener { addOrRemoveFavorite() }
        val tabList = arrayOf(
            resources.getString(R.string.followers),
            resources.getString(R.string.following)
        )
        adapter = PagerAdapter(tabList, args.Username, this)
        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    private fun observeDetail() {
        model.data(args.Username).observe(viewLifecycleOwner, Observer {
            if (it.state == State.SUCCESS) {
                user = it.data!!
                binding.data = it.data
            }
        })
        model.isFavorite.observe(viewLifecycleOwner, Observer { fav ->
            isFavorite = fav
            changeFavorite(fav)
        })
    }

    private fun changeFavorite(where: Boolean) {
        when {
            where -> {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
            }
            else -> {
                binding.fabFavorite.setImageResource(R.drawable.ic_unfavorite)
            }
        }
    }

    private fun addOrRemoveFavorite() {
        when {
            !isFavorite -> {
                model.addFavorite(user)
                FancyToast.makeText(
                    context,
                    resources.getString(R.string.favorite_add, user.login),
                    Toast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
            }
            else -> {
                model.removeFavorite(user)
                FancyToast.makeText(
                    context,
                    resources.getString(R.string.favorite_remove, user.login),
                    Toast.LENGTH_SHORT,
                    FancyToast.ERROR,
                    false
                ).show()
            }
        }
    }

    inner class PagerAdapter(
        private val tabList: Array<String>,
        private val username: String,
        fragment: Fragment
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = tabList.size
        override fun createFragment(position: Int): Fragment =
            FollowFragment.newInstance(username, tabList[position])
    }
}
