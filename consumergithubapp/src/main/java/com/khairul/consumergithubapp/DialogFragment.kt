package com.khairul.consumergithubapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.khairul.consumergithubapp.databinding.FragmentDetailBinding
import com.khairul.consumergithubapp.databinding.FragmentDetailBinding.inflate
import com.khairul.consumergithubapp.model.GithubUserModel

class DialogFragment : DialogFragment() {
    companion object {
        private const val USER = "USER"
        @JvmStatic
        fun newInstance(userModel: GithubUserModel) =
            com.khairul.consumergithubapp.DialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER, userModel)
                }
            }
    }
    private lateinit var userModel: GithubUserModel
    private lateinit var binding: FragmentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.arguments?.let {
            userModel = it.getParcelable(USER)!! }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { binding = inflate(layoutInflater, container, false)
        binding.data = userModel
        return binding.root
    }
}