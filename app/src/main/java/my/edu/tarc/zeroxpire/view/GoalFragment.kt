package my.edu.tarc.zeroxpire.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import my.edu.tarc.zeroxpire.R
import my.edu.tarc.zeroxpire.databinding.FragmentGoalBinding

class GoalFragment : Fragment() {
    private lateinit var binding: FragmentGoalBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = binding.tabLayout
        binding.goalHeading.text = "Active Goal"

        // Set up the TabLayout
        tabLayout.addTab(tabLayout.newTab().setText(R.string.active))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.completed))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.uncompleted))

        // Set initial fragment
        replaceFragment(ActiveGoalFragment())

        // Handle tab selection
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        binding.goalHeading.text = "Active Goal"
                        replaceFragment(ActiveGoalFragment())
                    }
                    1 -> {
                        binding.goalHeading.text = "Completed Goal"
                        replaceFragment(CompletedGoalFragment())
                    }
                    2 -> {
                        binding.goalHeading.text = "Uncompleted Goal"
                        replaceFragment(UncompletedGoalFragment())
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

}