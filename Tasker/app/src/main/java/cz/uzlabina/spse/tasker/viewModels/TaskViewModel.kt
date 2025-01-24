package cz.uzlabina.spse.tasker.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import cz.uzlabina.spse.tasker.database.TaskerDatabase
import cz.uzlabina.spse.tasker.model.Task
import cz.uzlabina.spse.tasker.repositories.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel (application: Application): AndroidViewModel(application) {

    private val taskRepository: TaskRepository

    // LiveData gives us updated words when they change.
    val tasks: LiveData<List<Task>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val db = TaskerDatabase.getDatabase(application)
        val taskDao = db.taskDao()
        val projectDao = db.projectDao()
        taskRepository = TaskRepository(taskDao)
        tasks = taskRepository.allTasks
    }

//    fun getProjectTasks(project:Int) : List<Task> {
//        return taskRepository.getProjectTasks(project)
//    }

//    fun ProjectTasks(subjectKey: Int) : List<Task>
//    {
//        val ms = mutableListOf<Task>()
//        tasks.value?.forEach {
//            if (it.project == subjectKey)
//                ms.add(it)
//        }
//        return ms.toList()
//    }
//    fun getDateTasks() : List<Task> {
//        return taskRepository.getDateTasks()
//    }
//
//    fun getDate(date: String) : List<Task> {
//        return taskRepository.getDate(date)
//    }
//
//    fun getDeadlineTasks(): List<Task> {
//        return taskRepository.getDeadlineTasks()
//    }
//
//    fun getDeadline(date: String) : List<Task> {
//        return taskRepository.getDeadline(date)
//    }

    fun insert(task: Task) = viewModelScope.launch {
        taskRepository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        taskRepository.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        taskRepository.delete(task)
    }
}
