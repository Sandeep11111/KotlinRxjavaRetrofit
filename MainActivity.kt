package com.example.sandeepkumarsingh.kotlinrxjavaretrofit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import model.Post
import model.adapter.PostAdapter
import retrofit.IMyApi
import retrofit.RetrofitClient

class MainActivity : AppCompatActivity() {

    internal lateinit var jsonApi: IMyApi
    var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init Api
        val retrofit = RetrofitClient.instance
        jsonApi = retrofit.create(IMyApi::class.java)

        //view
        recycler_posts.setHasFixedSize(true)
        recycler_posts.layoutManager = LinearLayoutManager(this)
        fetchData()
    }

    private fun fetchData() {
        compositeDisposable?.add(jsonApi.posts.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{posts->displayData(posts)})
    }

    private fun displayData(posts:List<Post>?) {
        val adapter = PostAdapter(this,posts!!)
        recycler_posts.adapter = adapter
    }
}
