package com.kamtayupov.koviplan.graph

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.list.TaskAdapter
import com.kamtayupov.koviplan.list.TaskFragment
import com.kamtayupov.koviplan.list.TaskFragment.TaskType

class ChaptersFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chapters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.apply {
            supportFragmentManager?.apply {
                Chapter.values()
                    .forEach {
                        beginTransaction().add(
                            it.layoutResId,
                            TaskFragment.newInstance(
                                it.taskType,
                                TaskAdapter.Size.SMALL
                            )
                        )
                            .commit()
                    }
            }
            setTitle(R.string.title_chapters)
        }
    }

    enum class Chapter(val taskType: TaskFragment.TaskType, val layoutResId: Int) {
        FIRST(TaskType.URGENT_IMPORTANT, R.id.chapter_one_list),
        SECOND(TaskType.URGENT_UNIMPORTANT, R.id.chapter_two_list),
        THIRD(TaskType.NON_URGENT_IMPORTANT, R.id.chapter_three_list),
        FOURTH(TaskType.NON_URGENT_UNIMPORTANT, R.id.chapter_four_list)
    }
}