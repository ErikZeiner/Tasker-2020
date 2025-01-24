package cz.uzlabina.spse.tasker

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cz.uzlabina.spse.tasker.model.Project
import cz.uzlabina.spse.tasker.model.Task
import cz.uzlabina.spse.tasker.viewModels.ProjectViewModel
import cz.uzlabina.spse.tasker.viewModels.TaskViewModel
import androidx.lifecycle.Observer
import cz.uzlabina.spse.tasker.adapters.ProjectsAdapter
import cz.uzlabina.spse.tasker.adapters.TasksAdapter

import kotlinx.android.synthetic.main.activity_new_task.*
import kotlinx.android.synthetic.main.content_new_task.*

import java.util.*

class EditTaskActivity : AppCompatActivity() {

    var date:String? = null
    var deadline: String? = null
    private lateinit var taskViewModel: TaskViewModel
    var projects =  mutableListOf<Project>()
    var projectsTitle = mutableListOf<String>()

    companion object {
        // Creates an Intent, which is used to send data from view to view
        fun createIntent (context: Context, task: Task): Intent
        {
            val intent = Intent(context,EditTaskActivity::class.java)
            intent.putExtra("task", task)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "Edit Task"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        setSupportActionBar(toolbar)

        val task = intent.getSerializableExtra("task") as Task

        btnSave.text = "Done"
        TxtTitle.setText(task.title)
        TxtNote.setText(task.note)

        if(task.date?.isEmpty() == false){
            TxtDate.text = task.date
            date = task.date
        }
        else{TxtDate.text = "Date"}
        if(task.deadline?.isEmpty() == false){
            TxtDeadline.text = task.deadline
            date = task.date
        }
        else{TxtDeadline.text = "Deadline"}


        val projectsAdapter = ProjectsAdapter(this)
        val projectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
        projectViewModel.projects.observe(this, Observer { project ->
            project?.let { projectsAdapter.setProject(it) }


            project.forEach {
                projects.add(it)
            }

            projectsTitle.add("Inbox")
            project.forEach {
                projectsTitle.add(it.title)
            }

            val spin: Spinner = findViewById(R.id.SpinProject)


            val spinAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, projectsTitle)

            spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spin.setAdapter(spinAdapter)

            spin.setSelection(task.project+1)

        })
        val taskAdapter = TasksAdapter(this)
        val taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.tasks.observe(this, Observer { tasks ->
            tasks?.let { taskAdapter.setTask(it) }
        })


        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        btnDate.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(this, DatePickerDialog.OnDateSetListener
                { view, mYear, mMonth, mDay ->
                    date = "" + mDay.toString().padStart(2,'0') + "/" + (mMonth+1).toString().padStart(2,'0') + "/" + mYear
                    TxtDate.text = date
                    Log.d("date", date)
                }, year, month, day
                )

            datePickerDialog.show()
        }
        btnDeadline.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(this, DatePickerDialog.OnDateSetListener
                { view, mYear, mMonth, mDay ->
                    deadline = "" + mDay.toString().padStart(2,'0') + "/" + (mMonth+1).toString().padStart(2,'0') + "/" + mYear
                    TxtDeadline.text = deadline
                    Log.d("deadline", deadline)
                }, year, month, day
                )

            datePickerDialog.show()
        }

        btnSave.setOnClickListener{

            if (TxtTitle.text.isEmpty()) {
                Toast.makeText(this, "Add task Title!", Toast.LENGTH_SHORT)
            } else {
                var projectId: Int = 0

                    projects.forEach {
                        if (it.title == SpinProject.selectedItem) {
                            projectId = it.id
                        }
                    }
                val updateTask = Task(
                    task.id,
                    TxtTitle.text.toString().trim(),
                    TxtNote.text.toString().trim(),
                    date,
                    projectId,
                    deadline
                    )
                taskViewModel.update(updateTask)
                finish()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}
