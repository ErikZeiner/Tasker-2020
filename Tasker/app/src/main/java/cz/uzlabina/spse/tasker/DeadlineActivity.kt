package cz.uzlabina.spse.tasker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cz.uzlabina.spse.tasker.adapters.TasksAdapter
import cz.uzlabina.spse.tasker.model.Project
import cz.uzlabina.spse.tasker.model.Task
import cz.uzlabina.spse.tasker.viewModels.ProjectViewModel
import cz.uzlabina.spse.tasker.viewModels.TaskViewModel
import androidx.lifecycle.Observer
import cz.uzlabina.spse.tasker.adapters.DateAdapter
import kotlinx.android.synthetic.main.activity_date.*
import kotlinx.android.synthetic.main.activity_date.toolbar
import kotlinx.android.synthetic.main.activity_project.*
import kotlinx.android.synthetic.main.content_date.*
import kotlinx.android.synthetic.main.content_project.*
import java.text.SimpleDateFormat
import java.util.*

class DeadlineActivity : AppCompatActivity() {
    private var tasks: MutableList<Task> = mutableListOf()
    private lateinit var taskAdapter: TasksAdapter
    private lateinit var taskViewModel: TaskViewModel

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, DeadlineActivity::class.java)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)
        setSupportActionBar(toolbar)
        this.title = "Deadlines"


        val dateAdapter = DateAdapter(this)
        rvDates.adapter = dateAdapter
        rvDates.layoutManager = LinearLayoutManager(this)

        val taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.tasks.observe(this, Observer {tasks ->
            tasks?.let { dateAdapter.setDataDeadline(it) }
        })

    }
    fun getTasksWhereDeadline(date:String):List<Task>{
        val projectViewModel = ViewModelProvider(this@DeadlineActivity).get(ProjectViewModel::class.java)
        val taskViewModel = ViewModelProvider(this@DeadlineActivity).get(TaskViewModel::class.java)
        val taskAdapter = TasksAdapter(this@DeadlineActivity)

        var list: List<Task> = mutableListOf()

        taskViewModel.tasks.observe(this@DeadlineActivity, Observer {tasks ->
            tasks?.let { taskAdapter.setTask(it) }
            list = taskAdapter.getDeadlineTasks(date).toMutableList()
        })
        return list

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}
