package com.shrikant.network.database

import androidx.room.*
import com.shrikant.domain.news.Article
import retrofit2.http.DELETE


@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllArticle(list: List<Article>): Array<Long>


    @Query("DELETE FROM article")
    fun deleteAll()


    @Query("SELECT * FROM article")
    fun getAllList(): List<Article>


}