package cz.uzlabina.spse.tasker.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import cz.uzlabina.spse.tasker.*
import cz.uzlabina.spse.tasker.model.Project
import cz.uzlabina.spse.tasker.model.Task
import kotlinx.android.synthetic.main.content_new_task.view.*
import kotlinx.android.synthetic.main.row_projects.view.*
import kotlinx.android.synthetic.main.row_tasks.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.app.Activity
import android.os.Handler
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProvider
import cz.uzlabina.spse.tasker.viewModels.TaskViewModel


class TasksAdapter(val context: Context) : RecyclerView.Adapter<TasksAdapter.TaskHolder>()
{
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val itemView = inflater.inflate(R.layout.row_tasks, parent, false)
        return TaskHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tasks.count()
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {

        val current = tasks[position]
        holder.taskName.text = current.title
        holder.taskNote.text = current.note
        holder.taskDate.text = current.date?.dropLast(5)
        if(current.deadline == null){holder.taskDeadline.visibility = View.INVISIBLE}
        else{holder.taskDeadline.visibility = View.VISIBLE}
        holder.taskName.setOnClickListener{
            (context as Activity).startActivityForResult(EditTaskActivity.createIntent(context,current), 100)
        }
        holder.checkbox.setOnClickListener{

            Handler().postDelayed(
                {
            if(holder.checkbox.isChecked){
                if(context is ProjectActivity)
                {context.deleteTask(current)

                }

            }

            holder.checkbox.isChecked = false
                },
                1500 // value in milliseconds
            )
        }


    }
    fun setTask(tasks: List<Task>)
    {
        this.tasks = tasks

        notifyDataSetChanged()
    }

    fun setProjectTasks(projectID: Int)
    {
        val projectTasks: MutableList<Task> = mutableListOf()
        tasks.forEach{
            if(it.project == projectID){
                projectTasks.add(it)
            }
        }
        this.tasks = projectTasks
    }

    fun getProjectTasks(projectID: Int):List<Task>
    {
        val projectTasks: MutableList<Task> = mutableListOf()
        tasks.forEach{
            if(it.project == projectID){
                projectTasks.add(it)
            }
        }
        return projectTasks
    }

    fun setDateTasks(projectDate:String)
    {
        val dateTasks: MutableList<Task> = mutableListOf()
        tasks.forEach{
            if(it.date == projectDate || it.deadline == projectDate){
                dateTasks.add(it)
            }
        }
        this.tasks = dateTasks
    }

    fun getDateTasks(projectDate: String):List<Task>
    {
        val dateTasks: MutableList<Task> = mutableListOf()
        tasks.forEach{
            if(it.date == projectDate){
                dateTasks.add(it)
            }
        }
        return dateTasks
    }

    fun getDeadlineTasks(projectDeadline: String):List<Task>
    {
        val dateTasks: MutableList<Task> = mutableListOf()
        tasks.forEach{
            if(it.deadline == projectDeadline){
                dateTasks.add(it)
            }
        }
        return dateTasks
    }
    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.TaskName
        val taskNote: TextView = itemView.TaskNote
        val taskDate: TextView = itemView.TaskDate
        val taskDeadline: ImageView = itemView.TaskDeadlineBool
        val checkbox: CheckBox = itemView.checkbox


    }
}