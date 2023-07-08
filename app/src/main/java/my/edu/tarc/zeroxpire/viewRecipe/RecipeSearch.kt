package my.edu.tarc.zeroxpire.viewRecipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import my.edu.tarc.zeroxpire.R

class RecipeSearch : Fragment() {
    private var imageButtonSearchBack: ImageButton? = null
    private var editTextSearch: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipe_search, container, false)
        imageButtonSearchBack = view.findViewById(R.id.imageButtonSearchBack)
        editTextSearch = view.findViewById(R.id.editTextSearch)

        imageButtonSearchBack!!.setOnClickListener {
            findNavController().popBackStack()
        }

        editTextSearch!!.requestFocus()
        return view
    }
}