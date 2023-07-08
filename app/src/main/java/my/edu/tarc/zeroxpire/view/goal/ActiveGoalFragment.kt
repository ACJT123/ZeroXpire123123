package my.edu.tarc.zeroxpire.view.goal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import my.edu.tarc.zeroxpire.R
import my.edu.tarc.zeroxpire.databinding.FragmentActiveGoalBinding
import my.edu.tarc.zeroxpire.databinding.FragmentCreateGoalBinding

class ActiveGoalFragment : Fragment() {
    private lateinit var binding: FragmentActiveGoalBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentActiveGoalBinding.inflate(inflater, container, false)
        return binding.root
    }


}