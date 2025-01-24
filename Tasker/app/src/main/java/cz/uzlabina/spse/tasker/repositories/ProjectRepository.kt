package cz.uzlabina.spse.tasker.repositories

import androidx.lifecycle.LiveData
import cz.uzlabina.spse.tasker.database.ProjectDao
import cz.uzlabina.spse.tasker.model.Project

class ProjectRepository(val projectDao: ProjectDao) {

    val allProjects: LiveData<List<Project>> = projectDao.getAllProjects()

//    suspend fun getProject(id: Int)
//    {
//        projectDao.getProject(id)
//    }

    suspend fun insert(project: Project)
    {
        projectDao.insert(project)
    }

    suspend fun update(project: Project)
    {
        projectDao.update(project)
    }

    suspend fun delete(project: Project)
    {
        projectDao.delete(project)
    }
}