package cz.uzlabina.spse.tasker.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cz.uzlabina.spse.tasker.MainActivity
import cz.uzlabina.spse.tasker.ProjectActivity
import cz.uzlabina.spse.tasker.R
import cz.uzlabina.spse.tasker.model.Project
import kotlinx.android.synthetic.main.row_projects.view.*

class ProjectsAdapter(val context: Context) : RecyclerView.Adapter<ProjectsAdapter.ProjectHolder>()
{
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var projects = emptyList<Project>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        val itemView = inflater.inflate(R.layout.row_projects, parent, false)
        return ProjectHolder(itemView)
    }

    override fun getItemCount(): Int {
        return projects.count()
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val current = projects[position]
        holder.projectName.text = current.title
        holder.projectName.setOnClickListener{
            if(context is MainActivity)
            {
                context.openProject(current.id,current.title)
            }
        }
    }
    fun setProject(projects: List<Project>)
    {
        this.projects = projects
        notifyDataSetChanged()
    }
    fun getProjects(): List<Project>
    {
        return projects.toList()
    }

    class ProjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val projectName: TextView = itemView.projectName
    }
}