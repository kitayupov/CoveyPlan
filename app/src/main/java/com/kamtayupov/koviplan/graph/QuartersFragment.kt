package com.kamtayupov.koviplan.graph

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.list.TaskFragment
import com.kamtayupov.koviplan.list.TaskFragment.TaskType

class QuartersFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.quarters, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quarters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.apply {
            supportFragmentManager?.apply {
                Chapter.values().forEach {
                    beginTransaction()
                        .replace(
                            it.layoutResId,
                            TaskFragment.newInstance(
                                it.taskType,
                                true
                            )
                        )
                        .commit()
                }
            }
            setTitle(R.string.title_quarters)
        }
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

    enum class Chapter(val taskType: TaskFragment.TaskType, val layoutResId: Int) {
        FIRST(TaskType.URGENT_IMPORTANT, R.id.chapter_one_list),
        SECOND(TaskType.URGENT_UNIMPORTANT, R.id.chapter_two_list),
        THIRD(TaskType.NON_URGENT_IMPORTANT, R.id.chapter_three_list),
        FOURTH(TaskType.NON_URGENT_UNIMPORTANT, R.id.chapter_four_list)
    }
}