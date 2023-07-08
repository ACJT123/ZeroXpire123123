package my.edu.tarc.zeroxpire.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "ingredientName")
    val ingredientName: String,
    @ColumnInfo(name = "expiryDate")
    val expiryDate: String
)
