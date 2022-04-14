package com.example.drugstore.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.drugstore.data.models.News
import com.example.drugstore.data.models.TopicNews
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.api.ApiConfig
import com.example.drugstore.utils.api.NewsApi
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
//https://newsapi.org/v2/top-headlines?country=us&category=health&apiKey=54db42e432d84365af90fcde5c12c8ff
class NewsRepo {

    private val collection = FirebaseFirestore.getInstance().collection("news")

    fun fetchAllTopic(): MutableLiveData<List<TopicNews>>{
        val result:MutableLiveData<List<TopicNews>> = MutableLiveData()
        collection
            .get()
            .addOnSuccessListener {
                val model = mutableListOf<TopicNews>()
                for(i in it.documents){
                    i.toObject(TopicNews::class.java)?.let { it1 -> model.add(it1) }
                }
                result.postValue(model)
            }
        return result
    }

//    fun getNewsFromAPI(topic: String): News {
//        val retrofit = Retrofit
//            .Builder()
//            .baseUrl(Constants.BASE_URL_NEWS)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        var newsList:News = News()
//        val service: NewsApi = retrofit.create(NewsApi::class.java)
//        service.getNews("us","health","","54db42e432d84365af90fcde5c12c8ff")
//            .enqueue(object : Callback<News>{
//            override fun onResponse(call: Call<News>, response: Response<News>) {
//                if(response.isSuccessful){
//                    newsList = response.body()!!
//                    Log.e("----",newsList.toString())
//                }else{
//                    when(response.code()){
//                        400 -> {
//                            Log.e("API 400","Bad Connection")
//                        }
//                        404 -> {
//                            Log.e("API 400","Not Found")
//                        }else -> {
//                            Log.e("API Error","Generic Error")
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<News>, t: Throwable) {
//                Log.e("----",t.message.toString())
//            }
//
//        })
//        Log.e("----c",newsList.toString())
//        return newsList
//    }

    suspend fun fetchNewsByTopicFromApi(topic: String) = ApiConfig.apiService.getNews(topic = topic)
}