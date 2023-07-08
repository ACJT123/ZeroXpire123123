package my.edu.tarc.zeroxpire.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGoal(goal: Goal)

    @Query("SELECT * FROM Goal")
    fun getAllGoals(): LiveData<List<Goal>>

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("DELETE FROM Goal")
    suspend fun deleteAllGoal()
}