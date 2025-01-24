package cz.uzlabina.spse.tasker.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import cz.uzlabina.spse.tasker.model.Project
import cz.uzlabina.spse.tasker.viewModels.TaskViewModel
import kotlinx.android.synthetic.main.row_date_rows.view.*
import kotlinx.android.synthetic.main.row_projects.view.*

import androidx.core.content.ContextCompat.getSystemService
import cz.uzlabina.spse.tasker.*
import cz.uzlabina.spse.tasker.model.Task

import kotlinx.android.synthetic.main.content_new_task.view.*
import kotlinx.android.synthetic.main.row_date_rows.view.TxtDate





class DateAdapter(val context: Context) : RecyclerView.Adapter<DateAdapter.DatesHolder>()
{
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dates = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesHolder {
        val itemView = inflater.inflate(R.layout.row_date_rows, parent, false)
        return DatesHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dates.count()
    }

    override fun onBindViewHolder(holder: DatesHolder, position: Int) {
        val current = dates[position]
        holder.date.text = current
        if(context is DateActivity)
        {
            context.getTasksWhereDate(current).forEach {
                var vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                var v = vi.inflate(R.layout.row_tasks, null)

                val taskName = v.findViewWithTag("TaskName") as TextView
                taskName.text = it.title
                val task = it
                taskName.setOnClickListener {(context as Activity).startActivityForResult(EditTaskActivity.createIntent(context,task), 100)}
                val taskDate = v.findViewWithTag("TaskDate") as TextView
                taskDate.text = it.date?.dropLast(5)

                val taskNote = v.findViewWithTag("TaskNote") as TextView
                taskNote.text = it.note

                val taskDeadline = v.findViewWithTag("TaskDeadlineBool") as ImageView
                if(it.deadline == null){taskDeadline.visibility = View.INVISIBLE}
                else{taskDeadline.visibility = View.VISIBLE}

                holder.tasks.addView(v)
            }
        }
        if(context is DeadlineActivity)
        {
            context.getTasksWhereDeadline(current).forEach {
                var vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                var v = vi.inflate(R.layout.row_tasks, null)

                val taskName = v.findViewWithTag("TaskName") as TextView
                taskName.text = it.title
                val task = it
                taskName.setOnClickListener {(context as Activity).startActivityForResult(EditTaskActivity.createIntent(context,task), 100)}
                val taskDate = v.findViewWithTag("TaskDate") as TextView
                taskDate.text = it.date?.dropLast(5)

                val taskNote = v.findViewWithTag("TaskNote") as TextView
                taskNote.text = it.note

                val taskDeadline = v.findViewWithTag("TaskDeadlineBool") as ImageView
                if(it.deadline == null){taskDeadline.visibility = View.INVISIBLE}
                else{taskDeadline.visibility = View.VISIBLE}

                holder.tasks.addView(v)
            }
        }
    }
    fun setData(projects: List<Task>)
    {
        val list = mutableListOf<String>()
        projects.forEach {
            if(it.date?.isEmpty()== false){
                list.add(it.date)}
        }
        this.dates = list.distinct()
        notifyDataSetChanged()
    }

    fun setDataDeadline(projects: List<Task>)
    {
        val list = mutableListOf<String>()
        projects.forEach {
            if(it.deadline?.isEmpty()== false){
                list.add(it.deadline)}
        }
        this.dates = list.distinct()
        notifyDataSetChanged()
    }

    class DatesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.TxtDate
        val tasks: LinearLayout = itemView.LinLayout
        
    }
}