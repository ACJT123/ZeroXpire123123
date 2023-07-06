package my.edu.tarc.zeroxpire.ingredient

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.edu.tarc.zeroxpire.R
import my.edu.tarc.zeroxpire.databinding.FragmentIngredientDetailBinding
import my.edu.tarc.zeroxpire.model.Ingredient
import my.edu.tarc.zeroxpire.viewmodel.IngredientViewModel
import java.util.*

class IngredientDetailFragment : Fragment() {
    private lateinit var binding: FragmentIngredientDetailBinding

    private var selectedDate: String? = null // Variable to store the selected date

    val ingredientViewModel : IngredientViewModel by activityViewModels()

    private var ingredientId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIngredientDetailBinding.inflate(inflater, container, false)

        setFragmentResultListener("requestName") { key, bundle ->
            val result = bundle.getString("name")
            binding.enterIngredientName.setText(result)
        }
        setFragmentResultListener("requestDate") { key, bundle ->
            val result = bundle.getString("date")
            binding.chooseExpiryDate.setText(result)
        }
        setFragmentResultListener("requestId") { key, bundle ->
            val id = bundle.getInt("data") // Use a default value if the key is not found or the value is not an integer
            ingredientId = id
            toast("id $ingredientId")
            // ...
        }

        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.upBtn.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.chooseExpiryDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.CustomDatePickerDialog,
                { _, year, month, dayOfMonth ->
                    selectedDate = "$dayOfMonth/${month + 1}/$year"
                    binding.chooseExpiryDate.setText(selectedDate)
                },
                year,
                month,
                dayOfMonth
            )

            // Set the selected date as the default date
            if (selectedDate != null) {
                val dateParts = selectedDate!!.split("/")
                val selectedYear = dateParts[2].toInt()
                val selectedMonth = dateParts[1].toInt() - 1
                val selectedDayOfMonth = dateParts[0].toInt()

                datePickerDialog.updateDate(selectedYear, selectedMonth, selectedDayOfMonth)
            }

            datePickerDialog.show()
        }

        binding.saveBtn.setOnClickListener{
            val newName = binding.enterIngredientName.text.toString()
            val newDate: String = if(selectedDate.toString().isEmpty()){
                selectedDate.toString()
            } else{
                binding.chooseExpiryDate.text.toString()
            }

            if (newName.isNotEmpty()) {
                binding.apply {
                    ingredientViewModel.updateIngredientName(ingredientId, newName)
                }

                toast("Ingredient is updated successfully!")
                toast(newName)
                findNavController().navigateUp()
                findNavController().navigateUp()

            } else {
                if(newName.isEmpty()){
                    binding.enterIngredientName.error = "Please enter the ingredient's name"
                    binding.enterIngredientName.requestFocus()
                }
            }
        }

        binding.deleteBtn.setOnClickListener {

        }
    }

    private fun toast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}