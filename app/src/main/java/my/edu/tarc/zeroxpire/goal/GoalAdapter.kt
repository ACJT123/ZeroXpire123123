package my.edu.tarc.zeroxpire.goal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class GoalAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ActiveGoalFragment()
            1 -> CompletedGoalFragment()
            2 -> UncompletedGoalFragment()
            else -> throw AssertionError()
        }
    }
}