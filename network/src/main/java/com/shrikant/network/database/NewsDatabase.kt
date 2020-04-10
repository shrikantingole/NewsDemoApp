package com.shrikant.network.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shrikant.domain.news.Article

@Database(
    entities = [Article::class],
    version = 1, exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao


    companion object {
        /**
         * The only instance
         */
        private var sInstance: NewsDatabase? = null

        /**
         * Gets the singleton instance of NewsDatabase.
         *
         * @param context The context.
         * @return The singleton instance of NewsDatabase.
         */

        @Synchronized
        fun getInstance(context: Context): NewsDatabase {
            if (sInstance == null) {
                sInstance = Room
                    .databaseBuilder(context, NewsDatabase::class.java, "newsdatabase.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

            }
            return sInstance!!

        }

    }
}