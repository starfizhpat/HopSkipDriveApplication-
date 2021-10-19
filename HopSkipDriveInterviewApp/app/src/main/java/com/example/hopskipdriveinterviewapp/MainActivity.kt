package com.example.hopskipdriveinterviewapp

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleView_main.layoutManager = LinearLayoutManager(this)
        //recycleView_main.adapter = MainAdapter()

        fetchAPI()
    }

    fun fetchAPI() {
        val url = "https://storage.googleapis.com/hsd-interview-resources/simplified_my_rides_response.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val ridesPD = gson.fromJson(body, HomeFeed::class.java)

                //var datetime = LocalDateTime.parse(ridesPD.rides[0].starts_at.subSequence(0, ridesPD.rides[0].starts_at.length - 1))

                //println(datetime.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US))

                val homeFeed = listOf<HomeFeed>(HomeFeed(ridesPD.rides.subList(0,3)), HomeFeed(listOf<Rides>(ridesPD.rides[3])))

                runOnUiThread{
                    recycleView_main.adapter = MainAdapter(homeFeed)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }
        })

    }
}

class HomeFeed(val rides: List<Rides>)

class Rides(val trip_id: Int, val in_series: Boolean, val starts_at: String, val ends_at: String, val estimated_earnings_cents: Float,
            val estimated_ride_minutes: Int, val estimated_ride_miles: Float, val ordered_waypoints: List<Waypoint>)

class Waypoint(val way_id: Int, val anchor: Boolean, val passengers: List<Passenger>, val location: LocDets)

class Passenger(val pass_id: Int, val booster_seat: Boolean, val first_name: String)

class LocDets(val addy: String, val lat: Float, val lng: Float)