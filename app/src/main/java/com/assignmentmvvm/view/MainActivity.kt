package com.assignmentmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignmentmvvm.R
import com.assignmentmvvm.viewmodel.MainViewModel
import com.retrofit.adapter.TodoAdapter
import com.retrofit.model.TodoDataModel
import com.retrofit.utility.Utils
import kotlincodes.com.retrofitwithkotlin.retrofit.ApiClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var layoutManager: RecyclerView.LayoutManager
    var TododataList = ArrayList<TodoDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)
        rvTodoList.layoutManager = layoutManager

        rvTodoList.adapter = TodoAdapter(this, TododataList)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        registerObservers()

        if (checkAndGetDataFromServer()) {
            // getCoroutineData()
            mainViewModel.getTodos()
        }

        text_reload.setOnClickListener {
            if (checkAndGetDataFromServer()) {
                // getCoroutineData()
                mainViewModel.getTodos()
            } else {
                Toast.makeText(this, "Please connect to internet first", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkAndGetDataFromServer(): Boolean {
        if (Utils.hasNetworkAvailable(this)) {
            group_no_internet.visibility = View.GONE
            return true
            // getCoroutineData()
        } else {
            group_no_internet.visibility = View.VISIBLE
            shimmer_view_container.visibility = View.GONE
            return false
        }
    }

    private fun getCoroutineData() {

        shimmer_view_container.startShimmerAnimation()
        shimmer_view_container.visibility = View.VISIBLE
        rvTodoList.visibility = View.GONE

        val service = ApiClient.getRetrofit()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getTodos()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.
                        shimmer_view_container.stopShimmerAnimation()
                        shimmer_view_container.visibility = View.GONE
                        rvTodoList.visibility = View.VISIBLE

                        TododataList.clear()
                        TododataList.addAll(response.body()!!)
                        rvTodoList.adapter?.notifyDataSetChanged()
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Error: ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()

                        shimmer_view_container.stopShimmerAnimation()
                        shimmer_view_container.visibility = View.GONE
                        rvTodoList.visibility = View.GONE
                    }
                } catch (e: HttpException) {
                    shimmer_view_container.stopShimmerAnimation()
                    shimmer_view_container.visibility = View.GONE
                    rvTodoList.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Exception ${e.message}", Toast.LENGTH_LONG)
                        .show()

                } catch (e: Throwable) {
                    shimmer_view_container.stopShimmerAnimation()
                    shimmer_view_container.visibility = View.GONE
                    rvTodoList.visibility = View.GONE
                    Toast.makeText(
                        this@MainActivity,
                        "Ooops: Something else went wrong",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }


    private fun registerObservers() {

        shimmer_view_container.startShimmerAnimation()
        shimmer_view_container.visibility = View.VISIBLE
        rvTodoList.visibility = View.GONE


        mainViewModel.usersSuccessLiveData.observe(this, Observer { todoList ->

            //if it is not null then we will display all users
            todoList?.let {

                shimmer_view_container.stopShimmerAnimation()
                shimmer_view_container.visibility = View.GONE
                rvTodoList.visibility = View.VISIBLE

                TododataList.clear()
                TododataList.addAll(it)
                rvTodoList.adapter?.notifyDataSetChanged()
            }
        })

        mainViewModel.usersFailureLiveData.observe(this, Observer { isFailed ->

            shimmer_view_container.stopShimmerAnimation()
            shimmer_view_container.visibility = View.GONE
            rvTodoList.visibility = View.VISIBLE

            //if it is not null then we will display all users
            isFailed?.let {
                Toast.makeText(this, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }

}
