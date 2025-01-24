package cz.uzlabina.spse.tasker.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.DateFormat

@Entity(tableName = "tasks")
class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    @NonNull
    val title: String,

    @ColumnInfo(name = "note")
    val note: String?,

    @ColumnInfo(name = "date")
    val date: String?,

    @ColumnInfo(name = "project")
    val project: Int,

    @ColumnInfo(name = "deadline")
    val deadline: String?
): Serializable

