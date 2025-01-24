package cz.uzlabina.spse.tasker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.widget.EditText

import androidx.appcompat.app.AlertDialog

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cz.uzlabina.spse.tasker.model.Project
import cz.uzlabina.spse.tasker.viewModels.ProjectViewModel
import androidx.lifecycle.Observer
import cz.uzlabina.spse.tasker.adapters.ProjectsAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.TxtDate
import kotlinx.android.synthetic.main.content_new_task.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {



    // DEFAULT DATE FORMAT yyyy-MM-dd
    /*

    TASK
	    title
	    note - can be null
	    date - can be null
	    project - if null set inbox
	    deadline - can be null

    PROJECT
	    name

    */

    /*
https://michalflowersky.files.wordpress.com/2013/03/todolistdatabasediagram.png
https://androidride.com/open-calendar-on-button-click-in-android-example-kotlin-java/
https://www.youtube.com/watch?v=DbiyoWjQD38
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val projectsAdapter = ProjectsAdapter(this)
        rvProjects.adapter = projectsAdapter
        rvProjects.layoutManager = LinearLayoutManager(this)

        val projectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
        projectViewModel.projects.observe(this, Observer {projects ->
            projects?.let { projectsAdapter.setProject(it) }
        })

        val sdf = SimpleDateFormat("EEEE dd/MM")
        val currentDate = sdf.format(Date())

        TxtDate.text = currentDate.toString()
        fab.setOnClickListener {
            startActivityForResult(NewTaskActivity.createIntent(this), 100)

        }

        btnInbox.setOnClickListener{openProject(0,"Inbox")}
        btnToday.setOnClickListener{openProject(-1,TxtDate.text.toString())}
        btnUpcoming.setOnClickListener{startActivityForResult(DateActivity.createIntent(this), 100)}
        btnDeadlines.setOnClickListener{startActivityForResult(DeadlineActivity.createIntent(this), 100)}

        btnEdit.setOnClickListener{view ->
            val taskEditText = EditText(this)
            val dialog = AlertDialog.Builder(this)
                .setTitle("Add new project")
                //.setMessage("What do you want to do next?")
                .setView(taskEditText)
                .setPositiveButton(
                    "Add"
                ) { dialog, which ->
                    val name = taskEditText.text.toString()

                    if (name.isNotEmpty())
                    {
                        val project = Project(0,name)
                        projectViewModel.insert(project)
                        taskEditText.setText("")
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()
            dialog.show()
        }
    }

    fun openProject(projectID: Int, projectName: String)
    {
        startActivityForResult(ProjectActivity.createIntent(this, projectID, projectName), 100)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
