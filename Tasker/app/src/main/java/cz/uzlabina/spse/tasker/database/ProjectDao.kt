package cz.uzlabina.spse.tasker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.uzlabina.spse.tasker.model.Project
import cz.uzlabina.spse.tasker.model.Task

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(project: Project)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(project: Project)

    @Delete
    suspend fun delete(project: Project)

    @Query("SELECT * from projects ORDER BY title ASC")
    fun getAllProjects(): LiveData<List<Project>>

//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    @Query("SELECT projects.title from projects WHERE projects.id = ID")
//    fun getProject(ID: Int): LiveData<Project>

}