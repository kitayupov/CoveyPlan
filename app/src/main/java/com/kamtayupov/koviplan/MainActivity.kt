package com.kamtayupov.koviplan

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.kamtayupov.koviplan.data.Task
import com.kamtayupov.koviplan.editor.EditTaskFragment
import com.kamtayupov.koviplan.graph.QuartersFragment
import com.kamtayupov.koviplan.list.TaskFragment
import com.kamtayupov.koviplan.room.App
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        nav_view.setCheckedItem(R.id.nav_quarters)

        plus_fab.setOnClickListener {
            navController.navigate(R.id.editTaskFragment)
        }

        save_fab.setOnClickListener {
            val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            val fragments = hostFragment?.childFragmentManager?.fragments
            fragments?.let {
                val fragment = it[0]
                when (fragment) {
                    is EditTaskFragment -> fragment.saveTask()
                }
            }
            navController.popBackStack()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val tasksDb = App.instance.database
        val dao = tasksDb.taskDao()
        val all = dao.getAll()
        println(all)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val fragment = hostFragment?.childFragmentManager?.fragments?.get(0)
        when (item.itemId) {
            R.id.nav_quarters -> {
                if (fragment !is QuartersFragment) {
                    navController.navigate(R.id.chaptersFragment)
                }
            }
            R.id.nav_completed -> {
                navController.navigate(R.id.taskFragment, TaskFragment.getArguments(completed = true))
            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun onQuarterSelected(taskType: TaskFragment.TaskType) {
        navController.navigate(R.id.taskFragment, TaskFragment.getArguments(taskType = taskType))
    }

    fun onTaskSelected(task: Task) {
        navController.navigate(
            R.id.editTaskFragment,
            EditTaskFragment.getArguments(task)
        )
    }
}
