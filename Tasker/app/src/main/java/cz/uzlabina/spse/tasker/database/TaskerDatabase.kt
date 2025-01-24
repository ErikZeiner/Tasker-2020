package cz.uzlabina.spse.tasker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.uzlabina.spse.tasker.model.Project
import cz.uzlabina.spse.tasker.model.Task


@Database(entities = arrayOf(Task::class, Project::class), version = 1, exportSchema = false)
  abstract class TaskerDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun projectDao(): ProjectDao

    companion object {
        @Volatile
        private var INSTANCE: TaskerDatabase? = null

        fun getDatabase(context: Context) : TaskerDatabase
        {
            val tempInstance = INSTANCE
            if (tempInstance != null)
            {
                return tempInstance
            }

            synchronized(this)
            {
                val MIGRATION_2_3 = object : Migration(2,3)
                {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("ALTER TABLE recipes ADD COLUMN  price REAL NOT NULL DEFAULT 0")
                    }
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskerDatabase::class.java,
                    "tasker_database"
                ).addMigrations(MIGRATION_2_3)
                    //.fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return  instance
            }
        }
    }
}