package cz.uzlabina.spse.tasker.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import cz.uzlabina.spse.tasker.database.TaskerDatabase
import cz.uzlabina.spse.tasker.model.Project
import cz.uzlabina.spse.tasker.repositories.ProjectRepository
import kotlinx.coroutines.launch


class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val projectRepository: ProjectRepository

    // LiveData gives us updated words when they change.
    val projects: LiveData<List<Project>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val db = TaskerDatabase.getDatabase(application)
        val projectDao = db.projectDao()

        projectRepository = ProjectRepository(projectDao)
        projects = projectRepository.allProjects
    }



//    fun getProject(id: Int) = viewModelScope.launch {
//        projectRepository.getProject(id)
//    }

    fun insert(project: Project) = viewModelScope.launch {
        projectRepository.insert(project)
    }

    fun update(project: Project) = viewModelScope.launch {
        projectRepository.update(project)
    }

    fun delete(project: Project) = viewModelScope.launch {
        projectRepository.delete(project)
    }
}
