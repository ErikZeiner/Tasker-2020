package cz.uzlabina.spse.tasker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.uzlabina.spse.tasker.model.Project
import cz.uzlabina.spse.tasker.model.Task
import java.util.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(task: Task)

    @Delete()
    suspend fun delete(task: Task)

    @Query("SELECT * from tasks ORDER BY tasks.title ASC")
    fun getAllTasks(): LiveData<List<Task>>

//    @Query("SELECT * from tasks WHERE tasks.project = :project")
//    fun getProjectTasks(project:Int): List<Task>
//
//    @Query("SELECT * from tasks WHERE tasks.date IS NOT NULL")
//    fun getDateTasks(): List<Task>
//
//    @Query("SELECT * from tasks WHERE tasks.date = :date")
//    fun getDate(date:String): List<Task>
//
//    @Query("SELECT * from tasks WHERE tasks.deadline IS NOT NULL")
//    fun getDeadlineTasks(): List<Task>
//
//    @Query("SELECT * from tasks WHERE tasks.deadline = :date")
//    fun getDeadline(date:String): List<Task>


}