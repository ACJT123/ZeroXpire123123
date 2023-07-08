package my.edu.tarc.zeroxpire.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import my.edu.tarc.zeroxpire.view.goal.ActiveGoalFragment
import my.edu.tarc.zeroxpire.view.goal.CompletedGoalFragment
import my.edu.tarc.zeroxpire.view.goal.UncompletedGoalFragment


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