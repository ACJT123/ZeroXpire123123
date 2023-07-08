package my.edu.tarc.zeroxpire.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val goalId: Int,
    @ColumnInfo(name = "goalName")
    val goalName: String,
    @ColumnInfo(name = "targetCompletionDate")
    val targetCompletionDate: String,
    @ColumnInfo(name = "targetNumOfIngredients")
    val targetNumOfIngredients: Int,
    @ColumnInfo(name = "completedDate")
    val completedDate: String?,
    @ColumnInfo(name = "uncompletedDate")
    val uncompletedDate: String?
)
