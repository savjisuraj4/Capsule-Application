package com.example.capsule_app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.capsule_app.fragments.*

class TabLayoutAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                VideoFragment()
            }
            1 -> {
                NotesFragment()
            }
            2 -> {
                QuizFragment()
            }
            3 -> {
                QuizResult()
            }

            else -> {
                BaseFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                return "Video"
            }
            1 -> {
                return "Notes"
            }
            2 -> {
                return "Quiz"
            }
            3 -> {
                return "Quiz Result"
            }

            else -> {
                return super.getPageTitle(position)
            }
        }
    }

}