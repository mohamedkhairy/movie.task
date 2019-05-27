package khairy.com.movietask

import android.app.SearchManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import khairy.com.movietask.extensions.isNull
import khairy.com.movietask.fragments.movieSearch.MovieSearshFragment
import khairy.com.movietask.searchSuggestions.SearchSuggestionAdapter
import khairy.com.movietask.searchSuggestions.SimpleSearchSuggestionsProvider

class MainActivity : AppCompatActivity() {


    private lateinit var mSuggestionAdapter: SearchSuggestionAdapter

    var movieSearshFragment =  MovieSearshFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleIntent(intent)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleIntent(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        setupSearchView(searchItem)
        return true
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.getAction()) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            val suggestions = SearchRecentSuggestions(
                this,
                SimpleSearchSuggestionsProvider.AUTHORITY, SimpleSearchSuggestionsProvider.MODE
            )
            suggestions.saveRecentQuery(query, null)
        }
    }


    private fun setupSearchView(searchItem: MenuItem?) {
        val searchManager = this.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))

        mSuggestionAdapter = SearchSuggestionAdapter(this, null, 0)
        searchView.suggestionsAdapter = mSuggestionAdapter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                startResultFragment(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val cursor = getRecentSuggestions(newText)
                mSuggestionAdapter.swapCursor(cursor)
                return false
            }
        })

        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener{
            override fun onSuggestionSelect(position: Int): Boolean {

                return true
            }

            override fun onSuggestionClick(position: Int): Boolean {

                val movieName = mSuggestionAdapter.getSuggestionText(position)
                startResultFragment(movieName)
                searchView.setQuery(mSuggestionAdapter.getSuggestionText(position), true)

                return false
            }
        })
    }

    fun getRecentSuggestions(query: String): Cursor? {
        val uriBuilder = Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(SimpleSearchSuggestionsProvider.AUTHORITY)

        uriBuilder.appendPath(SearchManager.SUGGEST_URI_PATH_QUERY)

        val selection = " ?"
        val selArgs = arrayOf(query)

        val uri = uriBuilder.build()

        return contentResolver.query(uri, null, selection, selArgs, null)
    }

    private fun startResultFragment(movieName: String){
        movieSearshFragment = MovieSearshFragment.newInstance(movieName)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_view,
                movieSearshFragment, MovieSearshFragment.TAG)
            .commit()
    }
}
