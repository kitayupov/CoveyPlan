package com.kamtayupov.koviplan

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.kamtayupov.koviplan.data.Task
import com.kamtayupov.koviplan.editor.EditTaskFragment
import com.kamtayupov.koviplan.graph.ChaptersFragment
import com.kamtayupov.koviplan.list.BaseTaskAdapter.Type.USUAL
import com.kamtayupov.koviplan.list.TaskFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

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
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val fragment = hostFragment?.childFragmentManager?.fragments?.get(0)
        when (item.itemId) {
            R.id.nav_chapters -> {
                if (fragment !is ChaptersFragment) {
                    navController.navigate(R.id.chaptersFragment)
                }
            }
            R.id.nav_gallery -> {

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

    fun onChapterSelected(taskType: TaskFragment.TaskType) {
        navController.navigate(
            R.id.taskFragment,
            TaskFragment.getArguments(
                taskType,
                USUAL
            )
        )
    }

    fun onTaskSelected(task: Task) {
        navController.navigate(
            R.id.editTaskFragment,
            EditTaskFragment.getArguments(task)
        )
    }
}
