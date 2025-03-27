package com.ycngmn.prothomalo.ui.screens.bookmark


import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.ycngmn.prothomalo.scraper.ArticleContainer
import com.ycngmn.prothomalo.scraper.NewsContainer
import org.json.JSONArray
import org.json.JSONObject

class NewsConverters {

    @TypeConverter
    fun fromPairList(value: List<Pair<String, String>>?): String {
        return JSONArray().apply {
            value?.forEach { put(JSONObject().apply {
                put("first", it.first)
                put("second", it.second)
            }) }
        }.toString()
    }

    @TypeConverter
    fun toPairList(value: String): List<Pair<String, String>> {
        val jsonArray = JSONArray(value)
        val list = mutableListOf<Pair<String, String>>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(jsonObject.getString("first") to jsonObject.getString("second"))
        }
        return list
    }

    @TypeConverter
    fun fromArticleList(value: List<ArticleContainer>?): String {
        return JSONArray().apply {
            value?.forEach { put(JSONObject().apply {
                put("title", it.title)
                put("thumbnail", it.thumbnail)
                put("url", it.url)
                put("date", it.date)
                put("subHead", it.subHead)
            }) }
        }.toString()
    }

    @TypeConverter
    fun toArticleList(value: String): List<ArticleContainer> {
        val jsonArray = JSONArray(value)
        val list = mutableListOf<ArticleContainer>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(
                ArticleContainer(
                    title = jsonObject.getString("title"),
                    thumbnail = jsonObject.getString("thumbnail"),
                    url = jsonObject.getString("url"),
                    date = jsonObject.getString("date"),
                    subHead = jsonObject.getString("subHead")
                )
            )
        }
        return list
    }
}

@Dao
interface BookmarkDao {
    @Query("Select * from bookmarks Order by newsId DESC")
    suspend fun getBookmarks(): List<NewsContainer>

    @Query("Delete from bookmarks where newsId = :newsId")
    suspend fun deleteBookmark(newsId: Int)

    @Insert
    suspend fun insertBookmark(newsContainer: NewsContainer)

}

@Database(entities = [NewsContainer::class], version = 1, exportSchema = false)
@TypeConverters(NewsConverters::class)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}

object BookmarkDatabaseHelper {
    private var INSTANCE: BookmarkDatabase? = null

    fun getInstance(context: Context): BookmarkDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }
    }

    private fun buildDatabase(context: Context): BookmarkDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BookmarkDatabase::class.java,
            "app_database"
        ).build()
    }
}



