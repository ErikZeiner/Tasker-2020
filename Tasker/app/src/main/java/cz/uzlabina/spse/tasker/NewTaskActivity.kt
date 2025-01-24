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

class NewTaskActivity : AppCompatActivity() {

    var date:String? = null
    var deadline: String? = null
    private lateinit var taskViewModel: TaskViewModel
    var projects =  mutableListOf<Project>()
    var projectsTitle = mutableListOf<String>()
    companion object {
        // Creates an Intent, which is used to send data from view to view
        fun createIntent (context: Context): Intent
        {
            val intent = Intent(context,NewTaskActivity::class.java)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "New Task"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        setSupportActionBar(toolbar)

        TxtDate.text = "Date"
        TxtDeadline.text = "Deadline"

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

        })

        val taskAdapter = TasksAdapter(this)
        val taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.tasks.observe(this, Observer { tasks ->
            tasks?.let { taskAdapter.setTask(it) }
        })


//
//
//        projectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
//        val marks = projectViewModel.GetProjectNamesList().toMutableList()
//
//        val spin: Spinner = findViewById(R.id.SpinProject)
//
//        val spinAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,marks)
//
//        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spin.setAdapter(spinAdapter)

//        val projectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
//        val projectAdapter = ProjectAdapter(this)
//
//        val taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
//        //Adds the back arrow button
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        // Sets it to close the window hence return
//        toolbar.setNavigationOnClickListener{finish()}
//
//        val spin: Spinner = findViewById(R.id.SpinProject)
//
//        val ms = mutableListOf<String>()
//        val md = projectViewModel.GetProjectNamesList().toList()
//
//        projectAdapter.getProjects().forEach{
//            ms.add(it.title)
//        }
//
//
//        val spinAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,ms.toList())
//
//        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spin.setAdapter(spinAdapter)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        //val sdf = SimpleDateFormat("dd/MM/YY")

        btnDate.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(this@NewTaskActivity, DatePickerDialog.OnDateSetListener
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
                DatePickerDialog(this@NewTaskActivity, DatePickerDialog.OnDateSetListener
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
                Log.d("OuiOui", "No texto")
                Toast.makeText(this, "Add task Title!", Toast.LENGTH_SHORT)
            } else {
//                if (SpinProject.selectedItem == "Inbox") {
                var projectId: Int = 0

                projects.forEach {
                    if (it.title == SpinProject.selectedItem) {
                        projectId = it.id
                    }
                }
                    val task = Task(
                        0,
                        TxtTitle.text.toString().trim(),
                        TxtNote.text.toString().trim(),
                        date,
                        projectId,
                        deadline
                    )
                    taskViewModel.insert(task)
//                } else {
//                    var projectId: Int = 0
//
//                    projects.forEach {
//                        if (it.title == SpinProject.selectedItem) {
//                            projectId = it.id
//                        }
//                    }
//                    if (projectId != 0) {
//                        val task = Task(
//                            0,
//                            TxtTitle.text.toString().trim(),
//                            TxtNote.text.toString().trim(),
//                            date,
//                            projectId,
//                            deadline
//                        )
//                        taskViewModel.insert(task)
//                    }
//                }
                    finish()
                }
            }


            /*// Text from the input-sorta-thingy in new view
        val name = taskNameEditText.text.toString()
        if(name.length >0)
        {
            // Create the intent, set it to as result, and close the window
            var intent: Intent = Intent()
            intent.putExtra("id", name)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
        else
        {
            taskNameLayout.error = "Fill in "
        }*/

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
////        // Handle action bar item clicks here. The action bar will
////        // automatically handle clicks on the Home/Up button, so long
////        // as you specify a parent activity in AndroidManifest.xml.
//        R.id.action_save -> {
//            val task = Task(1,TxtTitle.text.toString(),TxtNote.text.toString(),date,,deadline)
//            taskViewModel.insert(task)
//////        }
//////        else -> {
//////
//////        }
////
////    }
}
