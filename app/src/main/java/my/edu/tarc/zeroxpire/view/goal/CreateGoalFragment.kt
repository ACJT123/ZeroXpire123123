package my.edu.tarc.zeroxpire.view.goal

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import my.edu.tarc.zeroxpire.R
import my.edu.tarc.zeroxpire.databinding.FragmentCreateGoalBinding
import my.edu.tarc.zeroxpire.model.Goal
import my.edu.tarc.zeroxpire.model.Ingredient
import my.edu.tarc.zeroxpire.viewmodel.GoalViewModel
import my.edu.tarc.zeroxpire.viewmodel.IngredientViewModel
import java.util.*


class CreateGoalFragment : Fragment() {
    private lateinit var binding: FragmentCreateGoalBinding
    private var selectedStartDate: String? = null
    private var selectedEndDate: String? = null
    private var selectedCompletionDate: String? = null

    val goalViewModel : GoalViewModel by activityViewModels()

    //data
    private var name: String = ""
    private var name: String = ""
    private var date: String = ""
    private var numOfIngredients: Int = 0

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
        navBack()

        //calendar stuff
        calendarChooser()

        //target stuff
        chooseTargetNumOfIngredients()

        //store goal
        binding.createBtn.setOnClickListener {
            storeGoal()
        }
    }

    private fun storeGoal() {
        val goalName = binding.enterGoalName.text.toString()
        val goalCompletionDate = selectedCompletionDate
        val targetNumOfIngredient = binding.qty.text.toString().toInt()

        if (goalName.isNotEmpty() && goalCompletionDate != null) {
            // Insert the ingredient into the database
            binding.apply {
                name = goalName
                date = goalCompletionDate
                numOfIngredients = targetNumOfIngredient
                val newGoal = Goal(id, name, date, numOfIngredients, null, null, )
                ingredientViewModel.addIngredient(newIngredient)
            }
            Toast.makeText(requireContext(), "Goal is added successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()

        } else {
            if(ingredientName.isEmpty()){
                binding.enterIngredientNameLayout.error = "Please enter the ingredient's name"
                binding.enterIngredientName.requestFocus()
            }
            if(expiryDate.isNullOrEmpty()){
                binding.chooseExpiryDateLayout.error = "Please select the expiry date"
                binding.chooseExpiryDate.requestFocus()
            }
        }
    }

    private fun chooseTargetNumOfIngredients() {
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

    private fun calendarChooser() {
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
    }

    private fun navBack() {
        binding.upBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.createBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}