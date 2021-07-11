package com.themarto.chessclock.clock_list

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.themarto.chessclock.R
import com.themarto.chessclock.database.ChessClock
import com.themarto.chessclock.utils.ChessUtils.Companion.BLITZ
import com.themarto.chessclock.utils.ChessUtils.Companion.BULLET
import com.themarto.chessclock.utils.ChessUtils.Companion.CURRENT_CLOCK_KEY
import com.themarto.chessclock.utils.ChessUtils.Companion.RAPID
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_MINUTE

class ClockListAdapter(var currentClockId: Long) : RecyclerView.Adapter<ClockListAdapter.ViewHolder>() {

    private var onClickItem: (Long) -> Unit = { }

    var data = listOf<ChessClock>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun putOnClickItem(onClickItem: (Long) -> Unit) {
        this.onClickItem = onClickItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], currentClockId, onClickItem)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clockThumbnail: ImageView = itemView.findViewById(R.id.clock_item_thumbnail)
        val gameType: TextView = itemView.findViewById(R.id.clock_item_game_type)
        val gameTimes: TextView = itemView.findViewById(R.id.clock_item_game_times)

        companion object {
            fun from (parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.chess_clock_item_list, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(clock: ChessClock, currentClockId: Long, onClickItem: (Long) -> Unit) {
            gameTimes.text = itemView.context.getString(R.string.clock_item_times,
                clock.firstPlayerTime / ONE_MINUTE,
                clock.secondPlayerTime / ONE_MINUTE)
            when (clock.gameType) {
                BULLET -> {
                    clockThumbnail.setImageResource(R.drawable.ic_bullet_game)
                    gameType.text = itemView.resources.getString(R.string.bullet_type)
                }
                BLITZ -> {
                    clockThumbnail.setImageResource(R.drawable.ic_blitz_game)
                    gameType.text = itemView.resources.getString(R.string.blitz_type)
                }
                RAPID -> {
                    clockThumbnail.setImageResource(R.drawable.ic_rapid_game)
                    gameType.text = itemView.resources.getString(R.string.rapid_type)
                }
                else -> {
                    clockThumbnail.setImageResource(R.drawable.ic_classic_game)
                    gameType.text = itemView.resources.getString(R.string.classic_type)
                }
            }

            if (currentClockId == clock.id) {
                itemView.rootView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.black))
                gameType.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                gameTimes.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            } else {
                itemView.rootView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
                gameType.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                gameTimes.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
            }

            itemView.setOnClickListener{
                onClickItem(clock.id)
            }
        }
    }
}