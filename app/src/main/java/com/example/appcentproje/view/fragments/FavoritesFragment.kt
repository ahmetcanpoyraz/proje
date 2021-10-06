package com.example.appcentproje.view.fragments


import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentproje.R
import com.example.appcentproje.adapter.RecyclerViewAdapter
import com.example.appcentproje.model.Results
import com.example.appcentproje.view.DetailActivity
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList


class FavoritesFragment : Fragment() ,RecyclerViewAdapter.Listener{
    private var templist : ArrayList<Results>?=null
    private val gameList = ArrayList<Results>()
    private val adapter = RecyclerViewAdapter(gameList,this@FavoritesFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getdata()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_favorites, container, false)
        val rv : RecyclerView = view.findViewById(R.id.aa)

        rv.adapter=adapter
        rv.layoutManager=LinearLayoutManager(this.context)
        rv.setHasFixedSize(true)
        return view
    }

    fun getdata(){

        try {
            val database = this.activity?.openOrCreateDatabase("Favs", MODE_PRIVATE, null)
            val cursor = database?.rawQuery("SELECT * FROM favs", null)
            val favNamesIx = cursor?.getColumnIndex("name")
            val releasedIx = cursor?.getColumnIndex("released")
            val urlIx = cursor?.getColumnIndex("url")
            val gameidIx = cursor?.getColumnIndex("gameid")
            val ratingIx = cursor?.getColumnIndex("rating")

            gameList.clear()

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val name = favNamesIx?.let { cursor.getString(it) }
                    val released = releasedIx?.let { cursor.getString(it) }
                    val url = urlIx?.let { cursor.getString(it) }
                    val gameid = gameidIx?.let { cursor.getString(it) }
                    val rating = ratingIx?.let { cursor.getString(it) }

                    val a = gameid?.toInt()
                    val b = rating?.toFloat()
                    val obj = Results(a!!, name!!, url!!, b!!, released!!)

                    listOf(obj).let {
                        templist = ArrayList(it) }
                        templist?.forEach {
                            gameList.add(it) }
                }
                    cursor.close()
            }
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }
    override fun onItemClick(results: Results) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("idOfGame",results.id.toString())
        startActivity(intent)
    }

}