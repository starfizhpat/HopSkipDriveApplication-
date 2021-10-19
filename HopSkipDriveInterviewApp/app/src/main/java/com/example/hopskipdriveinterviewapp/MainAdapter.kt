package com.example.hopskipdriveinterviewapp

import android.opengl.Visibility
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_item_rv_main.view.*
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*

class MainAdapter(val listHome: List<HomeFeed>): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return listHome.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val mainItem = layoutInflater.inflate(R.layout.card_item_rv_main, parent, false)
        return CustomViewHolder(mainItem)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        var rideLength = listHome[position].rides.size

        when (rideLength) {
            1 -> {holder.view?.card_two.isGone = true
                 holder.view?.card_three.isGone = true }
            2 -> holder.view?.card_three.isGone = true
            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }

        var datetime = LocalDateTime.parse(listHome[position].rides[0].starts_at.subSequence(0, listHome[position].rides[0].starts_at.length - 1))

        var veryStartTimeHr = LocalDateTime.parse(listHome[position].rides[0].starts_at.subSequence(0, listHome[position].rides[0].starts_at.length - 1)).hour
        var veryStartTimeMn = LocalDateTime.parse(listHome[position].rides[0].starts_at.subSequence(0, listHome[position].rides[0].starts_at.length - 1)).minute

        var veryEndTimeHr = LocalDateTime.parse(listHome[position].rides[rideLength - 1].ends_at.subSequence(0, listHome[position].rides[0].ends_at.length - 1)).hour
        var veryEndTimeMn = LocalDateTime.parse(listHome[position].rides[rideLength - 1].ends_at.subSequence(0, listHome[position].rides[0].ends_at.length - 1)).minute

        var sumEst = 0.0
        for (i in 0..rideLength-1) {
            sumEst += listHome[position].rides[i].estimated_earnings_cents
            var StartTimeHr = LocalDateTime.parse(listHome[position].rides[i].starts_at.subSequence(0, listHome[position].rides[i].starts_at.length - 1)).hour
            var StartTimeMn = LocalDateTime.parse(listHome[position].rides[i].starts_at.subSequence(0, listHome[position].rides[i].starts_at.length - 1)).minute
            var EndTimeHr = LocalDateTime.parse(listHome[position].rides[i].ends_at.subSequence(0, listHome[position].rides[i].ends_at.length - 1)).hour
            var EndTimeMn = LocalDateTime.parse(listHome[position].rides[i].ends_at.subSequence(0, listHome[position].rides[i].ends_at.length - 1)).minute

            var cost = listHome[position].rides[i].estimated_earnings_cents / 100
            when (i) {
                0 -> {holder.view?.card_starToEnd_time_1.text = "" + StartTimeHr + ":" + StartTimeMn + " - " + EndTimeHr + ":" + EndTimeMn
                      holder.view?.card_price_1.text = "$" + cost}
                1 -> {holder.view?.card_starToEnd_time_2.text = "" + StartTimeHr + ":" + StartTimeMn + " - " + EndTimeHr + ":" + EndTimeMn
                      holder.view?.card_price_2.text = "$" + cost}
                2 -> {holder.view?.card_starToEnd_time_3.text = "" + StartTimeHr + ":" + StartTimeMn + " - " + EndTimeHr + ":" + EndTimeMn
                      holder.view?.card_price_3.text = "$" + cost}
                else -> { // Note the block
                    print("x is neither 1 nor 2")
                }
            }
        }

        holder.view?.main_dayOfWeek.text = datetime.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US)
        holder.view?.main_date.text = "" + datetime.monthValue + "/" + datetime.dayOfMonth
        holder.view?.main_time_vFirst.text = "" + veryStartTimeHr + ":" + veryStartTimeMn
        holder.view?.main_time_vLast.text = "" + veryEndTimeHr + ":" + veryEndTimeMn
        holder.view?.main_totalPrice.text = "$" + sumEst/100
    }

}