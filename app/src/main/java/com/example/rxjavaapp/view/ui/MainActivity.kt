package com.example.rxjavaapp.view.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rxjavaapp.R
import com.example.rxjavaapp.data.model.PostModel
import com.example.rxjavaapp.view.adapter.PostRecyclerAdapter
import com.example.rxjavaapp.viewmodel.PostViewModel

import java.util.*

class MainActivity : AppCompatActivity() {

    /**
     * Initialize view model instance for using mvvm arch
     */
    private lateinit var viewModel: PostViewModel

    /**
     * Initialize recycler view and its adapter
     */
    private lateinit var postRecycler: RecyclerView
    private lateinit var postAdapter: PostRecyclerAdapter

    /**
     * Initialize swipe refresh to notify recycler adapter when data updated
     */
    private lateinit var swipeRefresh: SwipeRefreshLayout

    /**
     * Initialize a list to save the retrieved data from rest api
     */
    private lateinit var postsList: List<PostModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        initViewModel()

        initSwipeRefresh()

    }

    /**
     * Initialize recycler view and its adapter
     */
    private fun initRecyclerView() {
        postRecycler = findViewById(R.id.postRecycler)
        postRecycler.layoutManager = LinearLayoutManager(this)
        postRecycler.setHasFixedSize(true)

        /**
         * Initialize recycler adapter
         */
        postAdapter = PostRecyclerAdapter()
        /**
         * Assign the recycler view adapter
         */
        postRecycler.adapter = postAdapter

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        viewModel = PostViewModel()
        viewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
        /**
         * Invoke get data method from view model class
         */
        viewModel.getData()

        /**
         * Observe mutable live data in view model to update the list in the recycler adapter
         */
        viewModel.data.observe(this, Observer<List<PostModel>> { list ->
            postAdapter.postsList = list
            postsList = list
            postAdapter.notifyDataSetChanged()

        })
    }


    /**
     * Reload the update data into the recycler adapter
     */
    private fun initSwipeRefresh() {
        swipeRefresh = findViewById(R.id.swipeRefresh)




        swipeRefresh.setOnRefreshListener {

            // on below line we are setting is refreshing to false.
            swipeRefresh.isRefreshing = false

            /**
             * Shuffle the data to simulate data updated process
             */
            Collections.shuffle(postsList, Random(System.currentTimeMillis()))
            postAdapter.postsList = postsList
            postAdapter.notifyDataSetChanged()

        }
    }

    /**
     * Display search menu to get the user suggestion
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // below line is to get our inflater
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        // Declare menu item.
        val searchItem = menu.findItem(R.id.actionSearch)
        val searchView = searchItem.actionView as SearchView


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                /**
                 * Invoke filter method to display related posts
                 */
                filter(newText)
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {

        val filteredlist = ArrayList<PostModel>()
        val clearList = ArrayList<PostModel>()


        for (post in postsList) {
            // Compare post title with the entered text
            if (post.title.toLowerCase().contains(text.lowercase(Locale.getDefault()))) {

                filteredlist.add(post)
            }
        }
        if (filteredlist.isEmpty()) {

            postAdapter.filteredData(clearList)

            Toast.makeText(this, "Not Found ...", Toast.LENGTH_SHORT).show()
        } else {

            postAdapter.filteredData(filteredlist)
        }
    }
}

