package com.khairul.consumergithubapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.khairul.consumergithubapp.adapter.UserAdapter
import com.khairul.consumergithubapp.databinding.ActivityMainBinding
import com.khairul.consumergithubapp.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var usersViewModel: UserViewModel
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var usersAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        usersAdapter = UserAdapter(ArrayList()) { githubUser ->
            val fragment = DialogFragment.newInstance(githubUser)
            fragment.show(supportFragmentManager, "userDetail")
        }

        mainBinding.mainRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = usersAdapter
        }

        usersViewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)
        visible()
        usersViewModel.userLists.observe(this, Observer{ users ->
            if (!users.isNullOrEmpty()) {
                gone(false)
                usersAdapter.setData(users)
            } else {
                gone(true)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.language_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.language_settings){
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun visible(){
        mainBinding.progress.visibility = View.VISIBLE
    }

    private fun gone(error: Boolean){
        if (error){
            mainBinding.apply {
                progress.visibility = View.GONE
                errLayout.visibility = View.VISIBLE
            }
        } else {
            mainBinding.progress.visibility = View.GONE
        }
    }
}