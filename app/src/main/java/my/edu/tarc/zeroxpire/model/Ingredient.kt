package my.edu.tarc.zeroxpire.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Goal::class,
            childColumns = ["ingredientGoalId"],
            parentColumns = ["goalId"]
        )
    ]
)
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val ingredientId: Int,
    val ingredientName: String,
    val expiryDate: String,
    val ingredientGoalId: Int?
)