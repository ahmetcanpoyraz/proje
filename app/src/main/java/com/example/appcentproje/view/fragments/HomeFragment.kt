package com.example.appcentproje.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.appcentproje.R
import com.example.appcentproje.adapter.RecyclerViewAdapter
import com.example.appcentproje.adapter.ViewPagerAdapter
import com.example.appcentproje.model.GamesModel
import com.example.appcentproje.model.Results
import com.example.appcentproje.service.GamesAPI
import com.example.appcentproje.view.DetailActivity
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_home.*
import me.relex.circleindicator.CircleIndicator3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() ,RecyclerViewAdapter.Listener{

    private val BASE_URL = "http://api.rawg.io/api/"
    private var gamesModels : ArrayList<Results>? = null
    private var recyclerViewAdapter: RecyclerViewAdapter?=null
    private var imagesList = ArrayList<String>()
    private var idList = ArrayList<String>()
    private var tempList = ArrayList<Results>()
    private val tempList2 = ArrayList<Results>()
    private val adapter = RecyclerViewAdapter(tempList2,this@HomeFragment)
    private val adapter2=ViewPagerAdapter(imagesList,idList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val viewPager=view.findViewById<ViewPager2>(R.id.view_pager2)
        val indicator=view.findViewById<CircleIndicator3>(R.id.indicator)
        val searchView2=view.findViewById<SearchView>(R.id.searchView)
        val sorryText=view.findViewById<TextView>(R.id.text_sorry)
        sorryText.isGone=true
        val rv : RecyclerView = view.findViewById(R.id.recyclerView)
        rv.adapter=adapter
        rv.layoutManager=LinearLayoutManager(this.context)
        rv.setHasFixedSize(true)

       searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                tempList.clear()
                val searchText = p0!!.toLowerCase()
                if(searchText.length>=3){
                    gamesModels?.forEach{
                        if(it.name.toLowerCase().contains(searchText.toLowerCase())){
                            tempList.add(it)
                            print(tempList)
                            sorryText.isGone=true
                        }
                        if (tempList.isEmpty()){
                            sorryText.isGone=false
                        }
                    }
                    viewPager.isGone=true
                    indicator.isGone=true
                    recyclerViewAdapter = RecyclerViewAdapter(tempList,this@HomeFragment)
                    recyclerView.adapter=recyclerViewAdapter
                    recyclerView.adapter?.notifyDataSetChanged()
                }
                else{
                    tempList.clear()
                    viewPager.isGone=false
                    indicator.isGone=false
                    sorryText.isGone=true
                    recyclerViewAdapter = gamesModels?.let { RecyclerViewAdapter(it,this@HomeFragment) }
                    recyclerView.adapter=recyclerViewAdapter
                    recyclerView.adapter?.notifyDataSetChanged()
                }
                return false
            }

        })

        return view
    }


    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(GamesAPI::class.java)
        val call = service.getData()

        call.enqueue(  object : Callback<GamesModel> {
            override fun onResponse(
                call: Call<GamesModel>,
                response: Response<GamesModel>
            ) {
                if(response.isSuccessful){
                    response.body()?.result?.let {
                        gamesModels = ArrayList(it)
                        gamesModels?.let {

                            imagesList.clear()
                            idList.clear()

                            for(i in 0..2){

                                imagesList.add(gamesModels?.get(i)?.background_image.toString())
                                idList.add(gamesModels?.get(i)?.id.toString())
                            }
                        }
                    }
                    gamesModels?.forEach {
                        tempList2.add(it) }

                    val vp= view?.findViewById<ViewPager2>(R.id.view_pager2)
                    if (vp != null) {
                        vp.adapter = ViewPagerAdapter(imagesList,idList)
                        vp.orientation=ViewPager2.ORIENTATION_HORIZONTAL
                    }
                    val indicator = view?.findViewById<CircleIndicator3>(R.id.indicator)
                    if (indicator != null) {
                        indicator.setViewPager(view_pager2)
                    }
                }
            }
            override fun onFailure(call: Call<GamesModel>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onItemClick(results: Results) {
        val intent = Intent(activity,DetailActivity::class.java)
        intent.putExtra("idOfGame",results.id.toString())
        startActivity(intent)
    }
}