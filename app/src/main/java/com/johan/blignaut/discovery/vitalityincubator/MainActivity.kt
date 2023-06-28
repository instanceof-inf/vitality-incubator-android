package com.johan.blignaut.discovery.vitalityincubator

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.johan.blignaut.discovery.vitalityincubator.adapter.PlacesAdapter
import com.johan.blignaut.discovery.vitalityincubator.databinding.ActivityMainBinding
import com.johan.blignaut.discovery.vitalityincubator.models.City
import com.johan.blignaut.discovery.vitalityincubator.models.GeoDB
import com.johan.blignaut.discovery.vitalityincubator.network.ApiService
import com.johan.blignaut.discovery.vitalityincubator.network.RetrofitClientInstance.retrofitInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : Activity() {

    private val apiService: ApiService = retrofitInstance.create(
        ApiService::class.java
    )
    private var compositeDisposable = CompositeDisposable()
    private var adapter = PlacesAdapter(onClickListener = {
        Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
    })
    private lateinit var binding : ActivityMainBinding

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar(binding.toolbar)
        binding.edtSearch.on(EditorInfo.IME_ACTION_SEARCH) {
            hideKeyboardIfViewFocused()
            binding.progressBar.visibility = View.VISIBLE
            binding.profilesRecyclerView.visibility = View.GONE
            getPlaces(binding.edtSearch.text.toString())
        }
        binding.profilesRecyclerView.adapter = adapter
        binding.refreshContainer.setOnRefreshListener {
            getPlaces(binding.edtSearch.text.toString())
        }
        getPlaces("")
    }

    private fun EditText.on(actionId: Int, func: () -> Unit) {
        setOnEditorActionListener { _, receivedActionId, _ ->
            if (actionId == receivedActionId) {
                func()
            }
            true
        }
    }

    private fun hideKeyboardIfViewFocused() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun getPlacesObs(): Observable<GeoDB> {
        return apiService.getPlaces()
    }

    fun getFilteredPlacesObs(filterByName: String): Observable<GeoDB> {
        return apiService.getFilteredPlaces(filterByName)
    }

    fun getPlaces(filterByName: String) {
        val call = if (filterByName.isNotEmpty())
            getFilteredPlacesObs(filterByName)
        else
            getPlacesObs()
        compositeDisposable.add(
            call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { places ->
                        Log.d("HomeActivity", "call success: " + places.data.count() + " items returned")
                        binding.refreshContainer.isRefreshing = false
                        displayPlaces(places.data)
                        binding.progressBar.visibility = View.GONE
                        binding.profilesRecyclerView.visibility = View.VISIBLE
                        binding.relCallFailed.visibility = View.GONE
                    },
                    { throwable ->
                        Log.e("HomeActivity", throwable.message ?: "onError")
                        binding.refreshContainer.isRefreshing = false
                        binding.progressBar.visibility = View.GONE
                        binding.profilesRecyclerView.visibility = View.GONE
                        binding.relCallFailed.visibility = View.VISIBLE
                    }
                )
        )
    }

    private fun displayPlaces(places: List<City>) {
        adapter.items.clear()
        adapter.items.addAll(places)
        adapter.notifyDataSetChanged()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//
//        // Associate searchable configuration with the SearchView
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        (menu.findItem(R.id.appSearchBar).actionView as SearchView).apply {
//            setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        }
//        return true
//
////        val search = menu.findItem(R.id.appSearchBar)
////        val searchView = search.actionView as SearchView
////        searchView.queryHint = "Search places"
////        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
////            override fun onQueryTextSubmit(query: String?): Boolean {
////                return false
////            }
////            override fun onQueryTextChange(newText: String?): Boolean {
//////                adapter.filter.filter(newText)
////                return true
////            }
////        })
////        return super.onCreateOptionsMenu(menu)
//    }
}