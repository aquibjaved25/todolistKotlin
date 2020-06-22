package kotlincodes.com.retrofitwithkotlin.retrofit

import com.retrofit.model.TodoDataModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    // its for coroutine
    @GET("todos")
    suspend fun getTodos(): Response<MutableList<TodoDataModel>>

    // its for Retrofit
//    @GET("todos")
//     fun getTodos(): Call<List<TodoDataModel>>

}