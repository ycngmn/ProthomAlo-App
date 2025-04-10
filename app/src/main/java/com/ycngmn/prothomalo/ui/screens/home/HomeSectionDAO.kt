package com.ycngmn.prothomalo.ui.screens.home

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.ycngmn.prothomalo.prothomalo.containers.HomeSectionContainer
import com.ycngmn.prothomalo.prothomalo.subs.PaloKeys
import org.json.JSONObject

class HomeSectionConverter {

    @TypeConverter
    fun fromKey(key: PaloKeys): String = key.name

    @TypeConverter
    fun toKey(key: String): PaloKeys = PaloKeys.valueOf(key)

    @TypeConverter
    fun fromMap(map: Map<String, String>?): String {
        return if (map != null) JSONObject(map).toString()
        else JSONObject().toString()
    }

    @TypeConverter
    fun toMap(jsonString: String?): Map<String, String> {
        val map = mutableMapOf<String, String>()
        if (jsonString == null) return map

        val jsonObject = JSONObject(jsonString)
        val iterator = jsonObject.keys()
        while (iterator.hasNext()) {
            val key = iterator.next()
            map[key] = jsonObject.getString(key)
        }
        return map
    }
}

@Dao
interface HomeSectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSection(section: HomeSectionContainer)

    @Query("SELECT * FROM home_sections WHERE paloKey = :key")
    suspend fun getSection(key: PaloKeys): HomeSectionContainer?
}


@Database(entities = [HomeSectionContainer::class], version = 1, exportSchema = false)
@TypeConverters(HomeSectionConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeSectionDao(): HomeSectionDao
}

object HomeSectionDBHelper {
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }
    }

    private fun buildDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "home_section_database"
        ).build()
    }
}
