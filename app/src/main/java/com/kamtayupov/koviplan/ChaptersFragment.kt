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
            arrayOf(
                R.id.chapter_one_list,
                R.id.chapter_two_list,
                R.id.chapter_three_list,
                R.id.chapter_four_list
            ).forEach {
                beginTransaction().add(it, TaskFragment.newInstance(arrayListOf("one", "two", "three"))).commit()
            }
        }
    }
}