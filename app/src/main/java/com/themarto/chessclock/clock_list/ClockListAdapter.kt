package com.themarto.chessclock.clock_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.themarto.chessclock.R
import com.themarto.chessclock.database.ChessClock
import com.themarto.chessclock.utils.ChessUtils.Companion.BLITZ_THUMBNAIL
import com.themarto.chessclock.utils.ChessUtils.Companion.BULLET_THUMBNAIL
import com.themarto.chessclock.utils.ChessUtils.Companion.RAPID_THUMBNAIL
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_MINUTE

class ClockListAdapter() : RecyclerView.Adapter<ClockListAdapter.ViewHolder>() {

    private lateinit var context: Context

    var data = listOf<ChessClock>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
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

        fun bind(clock: ChessClock) {
            gameType.text = clock.gameType
            gameTimes.text = itemView.context.getString(R.string.clock_item_times,
                clock.firstPlayerTime / ONE_MINUTE,
                clock.secondPlayerTime / ONE_MINUTE)
            clockThumbnail.setImageResource(when (clock.thumbnail) {
                BULLET_THUMBNAIL -> R.drawable.ic_bullet_game
                BLITZ_THUMBNAIL -> R.drawable.ic_blitz_game
                RAPID_THUMBNAIL -> R.drawable.ic_rapid_game
                else -> R.drawable.ic_classic_game
            })
        }
    }
}