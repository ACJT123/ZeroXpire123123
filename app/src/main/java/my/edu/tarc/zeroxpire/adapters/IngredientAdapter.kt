package my.edu.tarc.zeroxpire.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.zeroxpire.R
import my.edu.tarc.zeroxpire.ingredient.IngredientClickListener
import my.edu.tarc.zeroxpire.model.Ingredient
import java.lang.Math.abs
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class IngredientAdapter(private val clickListener: IngredientClickListener): RecyclerView.Adapter<IngredientAdapter.ViewHolder>(){
    private var ingredientList = emptyList<Ingredient>()
    private var originalIngredientList = emptyList<Ingredient>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textViewIngredientName: TextView = view.findViewById(R.id.ingredientName)
        val textViewDaysLeft: TextView = view.findViewById(R.id.daysLeft)
        val textViewDateAdded: TextView = view.findViewById(R.id.dateAdded)
    }

    internal fun setIngredient(ingredient: List<Ingredient>) {
        originalIngredientList = ingredient
        ingredientList = ingredient
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent,false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewIngredientName.text = ingredientList[position].ingredientName
        val expiryDate = LocalDate.now().toString()
        holder.textViewDateAdded.text = "Date Added: $expiryDate"
        holder.itemView.setOnClickListener {
            clickListener.onIngredientClick(ingredientList[position])
            Toast.makeText(it.context, ingredientList[position].ingredientName, Toast.LENGTH_SHORT).show()
        }

        // date conversion and calculate days difference
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy")
        val recordedDaysLeft: String = ingredientList[position].expiryDate
        val convertedDaysLeft: LocalDate = LocalDate.parse(recordedDaysLeft, formatter)
        val currentDate: LocalDate = LocalDate.now()
        val absoluteDaysLeft: Long = ChronoUnit.DAYS.between(currentDate, convertedDaysLeft)

        val daysLeftText: String = when {
            absoluteDaysLeft < 0 -> {
                holder.textViewDaysLeft.setTextColor(getColor(holder.itemView.context, R.color.secondaryColor))
                "Expired"
            }
            absoluteDaysLeft == 0L -> {
                holder.textViewDaysLeft.setTextColor(getColor(holder.itemView.context, R.color.textColor))
                "Expires Today"
            }
            else -> {
                holder.textViewDaysLeft.setTextColor(getColor(holder.itemView.context, R.color.btnColor))
                when (absoluteDaysLeft) {
                    1L -> "Expired in $absoluteDaysLeft day"
                    else -> "Expired in $absoluteDaysLeft days"
                }
            }
        }
        holder.textViewDaysLeft.text = daysLeftText
    }

//    fun filter(query: String) {
//        ingredientList = if (query.isEmpty()) {
//            originalIngredientList
//
//        } else {
//            originalIngredientList.filter { ingredient ->
//                ingredient.ingredientName.contains(query, ignoreCase = true)
//            }
//        }
//        notifyDataSetChanged()
//    }



    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun getIngredientAt(position: Int): Ingredient {
        return ingredientList[position]
    }


}