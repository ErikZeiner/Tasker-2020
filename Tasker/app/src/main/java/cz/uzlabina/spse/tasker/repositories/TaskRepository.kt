package cz.uzlabina.spse.tasker.repositories

import androidx.lifecycle.LiveData
import cz.uzlabina.spse.tasker.database.ProjectDao
import cz.uzlabina.spse.tasker.database.TaskDao
import cz.uzlabina.spse.tasker.model.Project
import cz.uzlabina.spse.tasker.model.Task

class TaskRepository(val taskDao: TaskDao) {
    val allTasks = taskDao.getAllTasks()

//    fun getProjectTasks(project: Int): List<Task>
//    {
//        return taskDao.getProjectTasks(project)
//    }
//
//    fun getDateTasks(): List<Task>
//    {
//        return taskDao.getDateTasks()
//    }
//
//    fun getDate(date: String): List<Task>
//    {
//        return taskDao.getDate(date)
//    }
//
//    fun getDeadlineTasks(): List<Task>
//    {
//        return taskDao.getDeadlineTasks()
//    }
//
//    fun getDeadline(date: String): List<Task>
//    {
//        return taskDao.getDeadline(date)
//    }

    suspend fun insert(task:Task)
    {
        taskDao.insert(task)
    }

    suspend fun update(task: Task)
    {
        taskDao.update(task)
    }

    suspend fun delete(task: Task)
    {
        taskDao.delete(task)
    }
}