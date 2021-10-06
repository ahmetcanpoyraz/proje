package com.example.appcentproje.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.appcentproje.R
import com.example.appcentproje.model.DetailOfGamesModel
import com.example.appcentproje.service.DetailOfGamesAPI
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    private val BASE_URL = "http://api.rawg.io/api/"
    var name : String? = null
    var description : String? = null
    var metacritic : String? = null
    var released : String? = null
    var url : String? = null
    var gameid: String? = null
    var rating: String? = null
    var fav_status: String?=null
    var idOfIntent: String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

       idOfIntent = intent.getStringExtra("idOfGame")
        fav_status = "0"
        loadData()

        val  likeButton  = findViewById<Button>(R.id.likeButton)
        likeButton.setOnClickListener{
            if(fav_status.equals("0")){
            runBlocking {
                launch {
                        delay(200)
                        fav_status="1"
                        likeButton.setBackgroundResource(R.drawable.ic_fav_filled)
                        saveToDatabase()
                    }
                 }
            }
            else{
                runBlocking {
                    launch {
                        delay(200)
                        fav_status="0"
                        likeButton.setBackgroundResource(R.drawable.ic_fav_outline)
                        deleteFromDB()
                    }
                }
            }
        }
    }
    private fun saveToDatabase(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var a = intent.getStringExtra("idOfGame")
        val urltext = "games/" + a + "?key=85a898c97b504b868c144aa5f22df29d&page_size=50"
        val service = retrofit.create(DetailOfGamesAPI::class.java)
        val call = service.getData(urltext)

        call.enqueue(object : Callback<DetailOfGamesModel> {
            override fun onResponse(
                call: Call<DetailOfGamesModel>,
                response: Response<DetailOfGamesModel>
            ) {
                if(response.isSuccessful) {

                     name = response.body()?.name.toString()
                     description = response.body()?.description.toString()
                     metacritic = response.body()?.metacritic.toString()
                     released = response.body()?.released.toString()
                     url = response.body()?.background_image.toString()
                     gameid=response.body()?.id.toString()
                     rating=response.body()?.rating.toString()

                    if (fav_status=="1"){
                        try {
                            val database = applicationContext.openOrCreateDatabase("Favs", MODE_PRIVATE,null)
                            database.execSQL("CREATE TABLE IF NOT EXISTS favs(id INTEGER PRİMARY KEY,name VARCHAR,description VARCHAR,metacritic VARCHAR,released VARCHAR,url VARCHAR,gameid VARCHAR,rating VARCHAR,fav_status VARCHAR)")
                            database.execSQL("DELETE FROM favs WHERE gameid= '${idOfIntent}' ")
                            val sqlString="INSERT INTO favs (name, description,metacritic,released,url,gameid,rating,fav_status) VALUES(?,?,?,?,?,?,?,?)"
                            val statement = database.compileStatement(sqlString)
                            statement.bindString(1,name)
                            statement.bindString(2,description)
                            statement.bindString(3,metacritic)
                            statement.bindString(4,released)
                            statement.bindString(5,url)
                            statement.bindString(6,gameid)
                            statement.bindString(7,rating)
                            statement.bindString(8,fav_status)
                            statement.execute()
                        }
                        catch(e : Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<DetailOfGamesModel>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun deleteFromDB(){
        try {
            val database = applicationContext.openOrCreateDatabase("Favs", MODE_PRIVATE,null)
            database.execSQL("DELETE FROM favs WHERE gameid= '${idOfIntent}' ")
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun loadData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val a = intent.getStringExtra("idOfGame")
        val urltext = "games/" + a + "?key=85a898c97b504b868c144aa5f22df29d&page_size=50"
        val service = retrofit.create(DetailOfGamesAPI::class.java)
        val call = service.getData(urltext)

        call.enqueue(object : Callback<DetailOfGamesModel> {
            override fun onResponse(
                call: Call<DetailOfGamesModel>,
                response: Response<DetailOfGamesModel>
            ) {
                if(response.isSuccessful) {

                    text_detail_nameOfGame.text = response.body()?.name.toString()
                    text_detail_description.text = response.body()?.description.toString()
                    text_detail_metacritic.text = response.body()?.metacritic.toString()
                    text_detail_release.text = response.body()?.released.toString()
                    Picasso.get().load(response.body()?.background_image).into(image_detail_background)
                }

                try {
                    val database = applicationContext.openOrCreateDatabase("Favs", MODE_PRIVATE,null)
                    database.execSQL("CREATE TABLE IF NOT EXISTS favs(id INTEGER PRİMARY KEY,name VARCHAR,description VARCHAR,metacritic VARCHAR,released VARCHAR,url VARCHAR,gameid VARCHAR,rating VARCHAR,fav_status VARCHAR)")
                    val cursor = database.rawQuery("SELECT * FROM favs WHERE gameid=${idOfIntent}",null)

                    while (cursor.moveToNext()){
                       fav_status=cursor.getString(cursor.getColumnIndex("fav_status"))
                        if(fav_status != null && fav_status.equals("1")){
                            likeButton.setBackgroundResource(R.drawable.ic_fav_filled)
                        }else if (fav_status != null && fav_status.equals("0")){
                            likeButton.setBackgroundResource(R.drawable.ic_fav_outline)
                        }
                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<DetailOfGamesModel>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}