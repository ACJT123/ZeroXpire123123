package my.edu.tarc.zeroxpire.view.ingredient

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import my.edu.tarc.zeroxpire.R
import my.edu.tarc.zeroxpire.databinding.FragmentAddIngredientBinding
import my.edu.tarc.zeroxpire.model.Ingredient
import my.edu.tarc.zeroxpire.viewmodel.IngredientViewModel
import java.util.*


class AddIngredientFragment : Fragment() {
    private lateinit var binding: FragmentAddIngredientBinding
    private var selectedDate: String? = null // Variable to store the selected date

    val ingredientViewModel : IngredientViewModel by activityViewModels()

    //data
    private var name: String = ""
    private var date: String = ""
    private var id: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddIngredientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterIngredientName.doAfterTextChanged {
            // Clear the error when the user starts typing
            binding.enterIngredientNameLayout.error = null
        }

        setFragmentResultListener("requestKey") { key, bundle ->
            val result = bundle.getString("data")
            binding.enterIngredientName.setText("$result")
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

        binding.addBtn.setOnClickListener {
            storeIngredient()
        }

        binding.upBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addIngredientFragment_to_scannerFragment)
        }

        binding.ingredientImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            binding.ingredientImage.setImageURI(data?.data)
            binding.ingredientImage.setPadding(0,0,0,0)
            binding.ingredientImage.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun storeIngredient() {
        val ingredientName = binding.enterIngredientName.text.toString()
        val expiryDate = selectedDate

        if (ingredientName.isNotEmpty() && expiryDate != null) {
            // Insert the ingredient into the database
            binding.apply {
                name = binding.enterIngredientName.text.toString()
                date = selectedDate.toString()
                val newIngredient = Ingredient(id, ingredientName, expiryDate)
                ingredientViewModel.addIngredient(newIngredient)
            }
            Toast.makeText(requireContext(), "Ingredient is added successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
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
}
