package my.edu.tarc.zeroxpire.view.ingredient

import android.app.AlertDialog
import android.graphics.Canvas
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import my.edu.tarc.zeroxpire.R
import my.edu.tarc.zeroxpire.adapters.IngredientAdapter
import my.edu.tarc.zeroxpire.databinding.FragmentIngredientBinding
import my.edu.tarc.zeroxpire.ingredient.IngredientClickListener
import my.edu.tarc.zeroxpire.model.Ingredient
import my.edu.tarc.zeroxpire.viewmodel.IngredientViewModel


class IngredientFragment : Fragment(), IngredientClickListener {
    private lateinit var binding: FragmentIngredientBinding

    val ingredientViewModel: IngredientViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        //receive details from login fragment
        setFragmentResultListener("requestEmail") { key, bundle ->
            val result = bundle.getString("email")
            binding.username.text = "$result"
        }

        binding = FragmentIngredientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = IngredientAdapter(this)

        ingredientViewModel.ingredientList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.ingredientSearchView.isIconified = true
                binding.ingredientSearchView.setOnQueryTextListener(null)
                binding.noIngredientHasRecorded.visibility = View.VISIBLE
            } else {
                binding.noIngredientHasRecorded.visibility = View.INVISIBLE
            }
            adapter.setIngredient(it)
        })
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter

        binding.ingredientSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val filteredIngredients = ingredientViewModel.ingredientList.value?.filter { ingredient ->
                    ingredient.ingredientName.contains(newText, ignoreCase = true)
                }

                if (filteredIngredients.isNullOrEmpty()) {
                    binding.notFoundText.visibility = View.VISIBLE
                } else {
                    binding.notFoundText.visibility = View.INVISIBLE
                }

                adapter.setIngredient(filteredIngredients ?: emptyList())

                return true
            }
        })




        delete(adapter)
        greeting()
        navigateBack()
    }



    private fun navigateBack() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Are you sure you want to Exit?").setCancelable(false)
                    .setPositiveButton("Exit") { dialog, id ->
                        requireActivity().finish()
                    }.setNegativeButton("Cancel") { dialog, id ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, onBackPressedCallback
        )
    }

    private fun greeting() {
        val greeting = binding.greeting
        if (isEvening()) {
            greeting.text = "Good evening!"
        }
        if (isMorning()) {
            greeting.text = "Good morning!"
        }
        if (isAfternoon()) {
            greeting.text = "Good afternoon!"
        }
    }

    private fun delete(adapter: IngredientAdapter) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Are you sure you want to Delete?").setCancelable(false)
                    .setPositiveButton("Delete") { dialog, id ->
                        val position = viewHolder.adapterPosition
                        val deletedIngredient = adapter.getIngredientAt(position)
                        ingredientViewModel.deleteIngredient(deletedIngredient)
                        toast("Ingredient deleted.")
                    }.setNegativeButton("Cancel") { dialog, id ->
                        dialog.dismiss()
                        adapter.notifyDataSetChanged()
                    }
                val alert = builder.create()
                alert.show()

            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(
                    c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                ).addBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(), R.color.secondaryColor
                        )
                    ).addActionIcon(R.drawable.baseline_delete_24).create().decorate()
                super.onChildDraw(
                    c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)
    }

    override fun onIngredientClick(ingredient: Ingredient) {
        findNavController().navigate(R.id.action_ingredientFragment_to_ingredientDetailFragment)
        val name = ingredient.ingredientName
        val date = ingredient.expiryDate
//        val id = ingredient.ingredientId
        setFragmentResult("requestName", bundleOf("name" to name))
        setFragmentResult("requestDate", bundleOf("date" to date))
//        setFragmentResult("requestId", bundleOf("id" to id))
        disableBtmNav()
    }

    private fun isMorning(): Boolean {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        return currentHour in 0..11 // Assuming morning is between 6 AM and 11 AM
    }

    private fun isAfternoon(): Boolean {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        return currentHour in 12..14 // Assuming morning is between 12 PM and 2 PM
    }

    private fun isEvening(): Boolean {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        return currentHour in 15..23 // Assuming evening is between 3 PM and 11 PM
    }

    private fun disableBtmNav() {
        val view = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        view.visibility = View.INVISIBLE

        val add = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        add.visibility = View.INVISIBLE
    }

    private fun toast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }



}