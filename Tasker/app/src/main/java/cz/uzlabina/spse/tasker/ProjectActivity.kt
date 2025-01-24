package cz.uzlabina.spse.tasker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cz.uzlabina.spse.tasker.adapters.ProjectsAdapter
import cz.uzlabina.spse.tasker.adapters.TasksAdapter
import cz.uzlabina.spse.tasker.model.Project
import cz.uzlabina.spse.tasker.model.Task
import cz.uzlabina.spse.tasker.viewModels.ProjectViewModel
import cz.uzlabina.spse.tasker.viewModels.TaskViewModel
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_project.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_project.*
import java.text.SimpleDateFormat
import java.util.*

class ProjectActivity : AppCompatActivity() {
    private var tasks: MutableList<Task> = mutableListOf()
    private lateinit var taskAdapter: TasksAdapter
    private lateinit var taskViewModel: TaskViewModel

    companion object {
        fun createIntent(context: Context, projectID: Int, projectName: String): Intent {
            val intent = Intent(context, ProjectActivity::class.java)
            intent.putExtra("projectID", projectID)
            intent.putExtra("projectName",projectName)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        setSupportActionBar(toolbar)
        this.title = ""

        var projectID = intent.getSerializableExtra("projectID") as Int
        var projectName = intent.getSerializableExtra("projectName") as String

        txtProject.text = projectName


        val taskAdapter = TasksAdapter(this)
        rvTasks.adapter = taskAdapter
        rvTasks.layoutManager = LinearLayoutManager(this)

        val taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.tasks.observe(this, Observer {tasks ->
            tasks?.let { taskAdapter.setTask(it) }
            if(projectID==-1){

                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = sdf.format(Date())
                taskAdapter.setDateTasks(currentDate.toString())
            }
            else{taskAdapter.setProjectTasks(projectID)}
        })
//        if(projectID==-1){taskAdapter.getProjectTasks(projectID)}
//        if(projectID==-2){taskAdapter.getDateTasks(Date().toString())}





//       tasksAdapter.setTask(t)
//        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
//        tasks = taskViewModel.getProjectTasks(project.id).toMutableList()
//
//        txtProject.text = project.title
//
//        taskAdapter = TaskAdapter(this)
//        rvTasks.layoutManager = LinearLayoutManager(this)
//        rvTasks.adapter = taskAdapter

    }

    fun deleteTask(task:Task)
    {
        val taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.delete(task)
    }
    fun deleteProject(item: MenuItem) {
        AlertDialog.Builder(this@ProjectActivity).apply {
            setTitle("Delete project and all tasks in it.")
            setMessage("Are you absolutely sure?")

            setPositiveButton("Yes"){_,_->
                var projectID = intent.getSerializableExtra("projectID") as Int
                val projectViewModel = ViewModelProvider(this@ProjectActivity).get(ProjectViewModel::class.java)
                val taskViewModel = ViewModelProvider(this@ProjectActivity).get(TaskViewModel::class.java)
                val taskAdapter = TasksAdapter(this@ProjectActivity)

                taskViewModel.tasks.observe(this@ProjectActivity, Observer {tasks ->
                    tasks?.let { taskAdapter.setTask(it) }
                    taskAdapter.getProjectTasks(projectID).forEach {
                        taskViewModel.delete(it)
                    }
                })

                projectViewModel.delete(Project(projectID,""))
                finish()
            }
            setNegativeButton("No"){_,_->}
        }.create().show()


    }
        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            if((intent.getSerializableExtra("projectID") as Int)>0){ menuInflater.inflate(R.menu.menu_delete, menu)}
            else{ menuInflater.inflate(R.menu.menu_main, menu)}
            return true
        }

    }


//
//package cz.uzlabina.spse.tasker
//
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import cz.uzlabina.spse.tasker.model.Project
//import java.util.*
//
//class ProjectActivity : AppCompatActivity() {
//
//
//    companion object {
//        fun createIntent(context: Context, project: Project): Intent {
//            val intent = Intent(context, ProjectActivity::class.java)
//            intent.putExtra("subject", project)
//            return intent
//        }
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_marks)
//        setSupportActionBar(toolbar)
//
//        subject = intent.getSerializableExtra("subject") as Subject
//        marksViewModel = ViewModelProvider(this).get(MarksViewModel::class.java)
//        marks = marksViewModel.marksForSubject(subject.key).toMutableList()
//
//        setTitle(getString(R.string.marks_activity_title) + " " + subject.name);
//
//        marksAdapter = MarksAdapter(this, marks)
//        rvMarks.layoutManager = LinearLayoutManager(this)
//        rvMarks.adapter = marksAdapter
//        recalculateAverage()
//
//        marksViewModel.marks.observe(this, LifecycleObserver {
//            marks = it.toMutableList()
//            marksAdapter.data = marksViewModel.marksForSubject(subject.key).toMutableList()
//            marksAdapter.notifyDataSetChanged()
//            recalculateAverage()
//        })
//
//        fab.setOnClickListener { view ->
//            marksViewModel.insert(Mark(UUID.randomUUID().toString(), 1, 5, subject.key))
//        }
//    }
//
//    fun recalculateAverage()
//    {
//        average.text = getString(R.string.average) + " " + marksViewModel.getSubjectMarksAverage(subject.key)
//    }
//
//    fun updateRecyclerView()
//    {
//        marksAdapter.changeData(marks)
//    }
//
//    fun save(menuItem: MenuItem)
//    {
//        subject.marks = marks.toTypedArray()
//        marksViewModel.insertForSubject(subject.key, marks.toList())
//        val intent = Intent()
//        intent.putExtra("subject", subject)
//        setResult(Activity.RESULT_OK, intent)
//        finish()
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_marks, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//}
//
