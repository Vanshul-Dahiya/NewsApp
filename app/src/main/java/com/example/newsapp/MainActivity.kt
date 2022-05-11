package com.example.newsapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsListAdapter.NewsItemClicked {
    private lateinit var aDapter  : NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Swipe to refresh
        swipeRefresh.setOnRefreshListener {
            Toast.makeText(this,"News is being refreshed",Toast.LENGTH_SHORT).show()
            swipeRefresh.isRefreshing = false
        }

//        defining the layout of recycler view
        recyclerViewNewsFeed.layoutManager = LinearLayoutManager(this)

//       fetching data
         FetchData()

//        providing data to a  variable to bind and store it in
       aDapter = NewsListAdapter(this)

//        connecting the variable to adapter of recycler view
        recyclerViewNewsFeed.adapter  = aDapter
    }

    private fun FetchData() {

        val NewsUrl = "https://saurav.tech/NewsAPI/top-headlines/category/business/in.json"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            NewsUrl,null,
            {
//              phle json array of articles nikalege fr uske ander ki details jaise title , url , image , author
                  val newsJsonArray = it.getJSONArray("articles")
                val newsList = ArrayList<Newsdata>()
//                articles ka array iterate krege or usme s title image sb bhar nikal lege
                for (i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)

//                    parsing the data
                    val news = Newsdata(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )

//                    ab is news object ko leke news list m dalna h
                    newsList.add(news)
//                    ab is data ko adapter m pass krna h
                }
//                news k data ko parse krdega ye
                aDapter.updatenews(newsList)
            },
            {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }


     @SuppressLint("ResourceType")
     override fun onItemClicked(item: Newsdata) {
//         it will take user to chrome on clicking item

         val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
         val customTabsIntent : CustomTabsIntent= builder.build()
         customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }

}