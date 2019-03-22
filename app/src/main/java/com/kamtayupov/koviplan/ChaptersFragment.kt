package com.kamtayupov.koviplan

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ChaptersFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chapters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.supportFragmentManager?.apply {
            Chapter.values()
                .forEach {
                    val list = ArrayList<Task>()
                    for (c in 'a'..'z') {
                        list.add(Task(c.toString(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"))
                    }
                    beginTransaction().add(it.layoutResId, TaskFragment.newInstance(list, it.type, TaskAdapter.Size.SMALL))
                        .commit()
                }
        }
    }

    enum class Chapter(val type: TaskFragment.Type, val layoutResId: Int) {
        FIRST(TaskFragment.Type.FIRST, R.id.chapter_one_list),
        SECOND(TaskFragment.Type.SECOND, R.id.chapter_two_list),
        THIRD(TaskFragment.Type.THIRD, R.id.chapter_three_list),
        FOURTH(TaskFragment.Type.FOURTH, R.id.chapter_four_list)
    }
}