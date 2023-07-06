package my.edu.tarc.zeroxpire.model

import androidx.lifecycle.LiveData
import androidx.room.*
import my.edu.tarc.zeroxpire.model.Ingredient

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM Ingredient")
    fun getAllIngredients(): LiveData<List<Ingredient>>

    @Update
    suspend fun updateIngredient(ingredient: Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)

    @Query("DELETE FROM Ingredient")
    suspend fun deleteAllIngredient()

    @Query("UPDATE Ingredient set ingredientName = :name where ingredientId = :id")
    suspend fun updateName(id: Int?, name: String?)

    @Query("SELECT * FROM Ingredient WHERE ingredientId = :id")
    suspend fun getIngredientById(id: Int): Ingredient?
}