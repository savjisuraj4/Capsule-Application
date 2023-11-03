package com.example.capsule_app.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import com.example.capsule_app.MainActivity
import com.example.capsule_app.R
import com.example.capsule_app.TabLayoutAdapter
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BaseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPagerTab : ViewPager
    private lateinit var timeLimit : TextView
    private lateinit var nextPageTitle:TextView
    private lateinit var nextCardView: CardView
    lateinit var nextPageSubTitle:TextView

    private lateinit var backButton:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_base, container, false)
        hooks(view)

        backButton.setOnClickListener {
            startActivity(Intent(requireContext(),MainActivity::class.java))
        }

        val tabLayoutAdapter= TabLayoutAdapter(childFragmentManager)
        viewPagerTab.adapter=tabLayoutAdapter
        tabLayout.setupWithViewPager(viewPagerTab)

        nextPageTitle.text = tabLayoutAdapter.getPageTitle(viewPagerTab.currentItem+1)


        viewPagerTab.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            @SuppressLint("SetTextI18n")
            override fun onPageScrollStateChanged(state: Int) {
                if (viewPagerTab.currentItem == 3) {
                    nextPageTitle.text = "Finish"
                }
                else {
                    nextPageTitle.text = tabLayoutAdapter.getPageTitle(viewPagerTab.currentItem+1)
                    when(viewPagerTab.currentItem){
                        0->{nextPageSubTitle.text="Read from NCERT Books"}
                        1->{nextPageSubTitle.text="Questions: 10"}
                        2->{nextPageSubTitle.text="Finish and check score"}
                        3->{nextPageSubTitle.text=""}
                    }
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) { }

            override fun onPageSelected(position: Int) { }
        })

        nextCardView.setOnClickListener {
            if (viewPagerTab.currentItem == 3) {
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().remove(this).commit()
                fragmentManager.beginTransaction().add(R.id.fragmentContainerView, DashboardFragment()).commit()

            }
            else {
                viewPagerTab.currentItem = viewPagerTab.currentItem + 1
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val initialTimeMillis = 10*60 * 1000L

        countDownTimer = object : CountDownTimer(initialTimeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                timeLimit.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {


                val alertDialog=AlertDialog.Builder(ContextThemeWrapper(requireContext(),R.style.CustomAlertDialogTheme))
                    .setOnCancelListener {  }
                alertDialog.setIcon(R.drawable.hourglass)
                alertDialog.setMessage("Time's up!")
                alertDialog.setCancelable(false)

                alertDialog.setNeutralButton("OK"
                ) { _, _ ->
                    val fragmentManager = parentFragmentManager
                    fragmentManager.beginTransaction().remove(this@BaseFragment).commit()
                    fragmentManager.beginTransaction()
                        .add(R.id.fragmentContainerView, DashboardFragment()).commit()
                }
                alertDialog.create().show()
            }
        }

        countDownTimer.start()
    }
    private fun hooks(view: View) {
        backButton=view.findViewById(R.id.backButton)
        tabLayout=view.findViewById(R.id.tabLayout)
        viewPagerTab=view.findViewById(R.id.viewPagerTab)
        timeLimit =view.findViewById(R.id.timeLimit)
        nextCardView=view.findViewById(R.id.nextCardView)
        nextPageTitle=view.findViewById(R.id.nextPageTitle)
        nextPageSubTitle=view.findViewById(R.id.nextPageSubTitle)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BaseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BaseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}