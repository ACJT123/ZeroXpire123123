package my.edu.tarc.zeroxpire.viewRecipe

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import my.edu.tarc.zeroxpire.R
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request

class RecipeDetails : Fragment() {
    private var firebaseDatabase: FirebaseDatabase? = FirebaseDatabase.getInstance("https://zeroxpire-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var databaseReference: DatabaseReference? = firebaseDatabase!!.getReference("Recipes")
    private lateinit var instructions : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_details, container, false)
        val args: RecipeDetailsArgs by navArgs()
        val recipeID = args.recipeID.toString()
        val currentRecipe: DatabaseReference? = databaseReference?.child(recipeID)

        val recipeImageView = view.findViewById<ImageView>(R.id.recipeDescImageView)
        val recipeTextView = view.findViewById<TextView>(R.id.recipeDescTextView)
        val recipeDetailsBackImageButton = view.findViewById<ImageView>(R.id.recipeDetailsBackImageButton)

        //top bar back button
        recipeDetailsBackImageButton.setOnClickListener {
            findNavController().popBackStack()
        }

        //recipe image
        currentRecipe?.child("image")?.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val link = dataSnapshot.getValue(String::class.java)
                    if (link != null) {
                        Picasso.get().load(link).into(recipeImageView)
                    }else {
                        recipeImageView.setImageResource(R.drawable.baseline_image_24)
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    recipeImageView.setImageResource(R.drawable.baseline_broken_image_24)
                }
            }
        )

        //recipe instructions
        currentRecipe?.child("instructions")?.addListenerForSingleValueEvent(
            object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val link = dataSnapshot.getValue(String::class.java)

                    if (link != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val httpClient = OkHttpClient()
                            val request = Request.Builder().url(link).build()
                            val response = httpClient.newCall(request).execute()
                            instructions = response.body()?.string().toString()
                            withContext(Dispatchers.Main) {
                                recipeTextView.text = instructions
                            }
                        }
                    }else {
                        recipeTextView.text = getString(R.string.recipeDetailsNoInstructionsFound)
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    recipeTextView.text = getString(R.string.recipeDetailsErrorOccurred)
                }
            }
        )
        return view
    }
}