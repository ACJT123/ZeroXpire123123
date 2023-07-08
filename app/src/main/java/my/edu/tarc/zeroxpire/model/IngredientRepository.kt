package my.edu.tarc.zeroxpire.model

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import my.edu.tarc.zeroxpire.model.IngredientDao
import my.edu.tarc.zeroxpire.model.Ingredient

class IngredientRepository(private val ingredientDao: IngredientDao){
    //Room execute all queries on a separate thread
    val allIngredients: LiveData<List<Ingredient>> = ingredientDao.getAllIngredients()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun add(ingredient: Ingredient){
        ingredientDao.addIngredient(ingredient)
    }

    @WorkerThread
    suspend fun deleteAll(){
        ingredientDao.deleteAllIngredient()
    }

    @WorkerThread
    suspend fun delete(ingredient: Ingredient){
        ingredientDao.deleteIngredient(ingredient)
    }

    @WorkerThread
    suspend fun update(ingredient: Ingredient){
        ingredientDao.updateIngredient(ingredient)
    }

//    @WorkerThread
//    suspend fun updateName(id: Int, newName: String) {
//        ingredientDao.updateName(id, newName)
//    }
//
//    @WorkerThread
//    suspend fun getIngredientById(id: Int){
//        ingredientDao.getIngredientById(id)
//    }

//    @WorkerThread
//    suspend fun sortByName(ingredient: Ingredient){
//        ingredientDao.sortByName(ingredient)
//    }
}