package my.edu.tarc.zeroxpire.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.zeroxpire.R
import my.edu.tarc.zeroxpire.ingredient.IngredientClickListener
import my.edu.tarc.zeroxpire.model.Ingredient

class IngredientAdapter(private val clickListener: IngredientClickListener): RecyclerView.Adapter<IngredientAdapter.ViewHolder>(){
    private var ingredientList = emptyList<Ingredient>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textViewIngredientName: TextView = view.findViewById(R.id.ingredientName)
        val textViewDaysLeft: TextView = view.findViewById(R.id.daysLeft)
    }

    internal fun setIngredient(ingredient: List<Ingredient>){
        this.ingredientList = ingredient
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewIngredientName.text = ingredientList[position].ingredientName
        holder.textViewDaysLeft.text = ingredientList[position].expiryDate
        holder.itemView.setOnClickListener{
            clickListener.onIngredientClick(ingredientList[position])
            Toast.makeText(it.context, ingredientList[position].ingredientName, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun getIngredientAt(position: Int): Ingredient{
        return ingredientList.get(position)
    }
}