package my.edu.tarc.zeroxpire.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.edu.tarc.zeroxpire.model.IngredientRepository
import my.edu.tarc.zeroxpire.model.IngredientDatabase
import my.edu.tarc.zeroxpire.model.Ingredient

class IngredientViewModel(application: Application): AndroidViewModel(application) {

    var ingredientList: LiveData<List<Ingredient>>
    private val repository: IngredientRepository

    init {
        val ingredientDao = IngredientDatabase.getDatabase(application).ingredientDao()
        repository = IngredientRepository(ingredientDao)
        ingredientList = repository.allIngredients
    }

    fun addIngredient(ingredient: Ingredient) = viewModelScope.launch {
        repository.add(ingredient)
    }

    fun deleteAllIngredients() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun deleteIngredient(ingredient: Ingredient) = viewModelScope.launch {
        repository.delete(ingredient)
    }

    fun updateIngredient(ingredient: Ingredient) = viewModelScope.launch {
        repository.update(ingredient)
    }

    fun updateIngredientName(id: Int, newName: String) = viewModelScope.launch {
        repository.updateName(id, newName)
    }

    fun getIngredientById(id: Int) = viewModelScope.launch {
        repository.getIngredientById(id)
    }

}