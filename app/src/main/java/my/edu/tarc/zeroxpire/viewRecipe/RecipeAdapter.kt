package my.edu.tarc.zeroxpire.viewRecipe

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import my.edu.tarc.zeroxpire.R


class RecipeAdapter : RecyclerView.Adapter<RecipeRecyclerViewHolder>() {
    private lateinit var context: Context
    private var firebaseDatabase: FirebaseDatabase? = FirebaseDatabase.getInstance("https://zeroxpire-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var recipesDatabaseReference: DatabaseReference? = firebaseDatabase!!.getReference("Recipes")
    private var userDatabaseReference: DatabaseReference? = firebaseDatabase!!.getReference("Users")

    //TODO: get user id
    val userId: String = "1"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeRecyclerViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(viewType, parent, false)

        return RecipeRecyclerViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.recipe_frame
    }

    override fun onBindViewHolder(holder: RecipeRecyclerViewHolder, position: Int) {
        val recipeID : Int = holder.bindingAdapterPosition
        val currentRecipe: DatabaseReference? = recipesDatabaseReference?.child((recipeID).toString())

        val recipeDescConstraintLayout : ConstraintLayout = holder.getView().findViewById(R.id.recipeDescConstraintLayout)
        val titleTextView: TextView = holder.getView().findViewById(R.id.recipe_title_textview)
        val ingredientsTextView: TextView = holder.getView().findViewById(R.id.recipe_ingredients_textview)
        val recipeImageView : ImageView = holder.getView().findViewById(R.id.recipe_imageView)
        val shareButton : Button = holder.getView().findViewById(R.id.shareButton)
        val bookmarkButton : Button = holder.getView().findViewById(R.id.bookmarkButton)

        recipeDescConstraintLayout.setOnClickListener {
            val action = RecipeFragmentDirections.actionRecipeFragmentToRecipeDetails(recipeID)
            Navigation.findNavController(holder.getView()).navigate(action)
        }
        //title
        currentRecipe?.child("title")?.get()?.addOnCompleteListener { recipeTitle ->
            titleTextView.text = recipeTitle.result.value.toString()
        }
        //ingredients
        currentRecipe?.child("ingredients")?.get()?.addOnCompleteListener {recipeIngredients ->
            ingredientsTextView.text = recipeIngredients.result.value.toString()
        }
        //image
        currentRecipe?.child("image")?.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // getting a DataSnapshot for the location at the
                    // specified relative path and getting in the link variable
                    val link = dataSnapshot.getValue(String::class.java)
                    if (link != null) {
                        // loading that data into recipeImageView
                        // variable which is ImageView
                        Picasso.get().load(link).into(recipeImageView)
                    }else {
                        recipeImageView.setImageResource(R.drawable.baseline_image_24)
                    }
                }

                // this will called when any problem occurs in getting data
                override fun onCancelled(databaseError: DatabaseError) {
                    recipeImageView.setImageResource(R.drawable.baseline_broken_image_24)
                }
            }
        )
        //bookmark button
        bookmarkButton.setOnClickListener{
            userDatabaseReference?.child(userId)?.child("bookmarks")?.child(recipeID.toString())?.
            setValue("true")?.addOnCompleteListener{
                Toast.makeText(context,"Added to bookmarks",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return 50
    }
}