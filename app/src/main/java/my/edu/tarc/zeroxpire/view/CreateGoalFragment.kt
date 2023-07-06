package my.edu.tarc.zeroxpire.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.edu.tarc.zeroxpire.R
import my.edu.tarc.zeroxpire.databinding.FragmentCreateGoalBinding
import java.util.*


class CreateGoalFragment : Fragment() {
    private lateinit var binding: FragmentCreateGoalBinding
    private var selectedStartDate: String? = null
    private var selectedEndDate: String? = null
    private var selectedCompletionDate: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //nav stuff
        binding.upBtn.setOnClickListener {
            findNavController().navigate(R.id.action_createGoalFragment_to_goalFragment)
        }
        binding.createBtn.setOnClickListener {
            findNavController().navigate(R.id.action_createGoalFragment_to_goalFragment)
        }

        //calendar stuff
        binding.chooseExpiryStartDate.setOnClickListener{
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.CustomDatePickerDialog,
                { _, year, month, dayOfMonth ->
                    selectedStartDate = "$dayOfMonth/${month + 1}/$year"
                    binding.chooseExpiryStartDate.setText(selectedStartDate)
                },
                year,
                month,
                dayOfMonth
            )

            // Set the selected date as the default date
            if (selectedStartDate != null) {
                val dateParts = selectedStartDate!!.split("/")
                val selectedYear = dateParts[2].toInt()
                val selectedMonth = dateParts[1].toInt() - 1
                val selectedDayOfMonth = dateParts[0].toInt()

                datePickerDialog.updateDate(selectedYear, selectedMonth, selectedDayOfMonth)
            }

            datePickerDialog.show()
        }

        binding.chooseExpiryEndDate.setOnClickListener{
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.CustomDatePickerDialog,
                { _, year, month, dayOfMonth ->
                    selectedEndDate = "$dayOfMonth/${month + 1}/$year"
                    binding.chooseExpiryEndDate.setText(selectedEndDate)
                },
                year,
                month,
                dayOfMonth
            )

            // Set the selected date as the default date
            if (selectedEndDate != null) {
                val dateParts = selectedEndDate!!.split("/")
                val selectedYear = dateParts[2].toInt()
                val selectedMonth = dateParts[1].toInt() - 1
                val selectedDayOfMonth = dateParts[0].toInt()

                datePickerDialog.updateDate(selectedYear, selectedMonth, selectedDayOfMonth)
            }

            datePickerDialog.show()
        }

        binding.chooseTargetCompletionDate.setOnClickListener{
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.CustomDatePickerDialog,
                { _, year, month, dayOfMonth ->
                    selectedCompletionDate = "$dayOfMonth/${month + 1}/$year"
                    binding.chooseTargetCompletionDate.setText(selectedCompletionDate)
                },
                year,
                month,
                dayOfMonth
            )

            // Set the selected date as the default date
            if (selectedCompletionDate != null) {
                val dateParts = selectedCompletionDate!!.split("/")
                val selectedYear = dateParts[2].toInt()
                val selectedMonth = dateParts[1].toInt() - 1
                val selectedDayOfMonth = dateParts[0].toInt()

                datePickerDialog.updateDate(selectedYear, selectedMonth, selectedDayOfMonth)
            }

            datePickerDialog.show()
        }

        //target stuff
        var target: Int = binding.qty.text.toString().toInt()
        binding.decBtn.setOnClickListener {
            target -= 1
            binding.qty.text = target.toString()
        }
        binding.incBtn.setOnClickListener {
            target += 1
            binding.qty.text = target.toString()
        }
    }
}