package com.themarto.chessclock.clock_list

import android.text.format.DateUtils
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.themarto.chessclock.R
import com.themarto.chessclock.database.ChessClock
import com.themarto.chessclock.utils.ChessUtils
import com.themarto.chessclock.utils.ChessUtils.Companion.BLITZ
import com.themarto.chessclock.utils.ChessUtils.Companion.BULLET
import com.themarto.chessclock.utils.ChessUtils.Companion.RAPID
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_MINUTE

class ClockListAdapter(
    var currentClockId: Long,
    private val clockItemListener: ClockItemListener
) : RecyclerView.Adapter<ClockListAdapter.ViewHolder>() {

    var data = listOf<ChessClock>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, clockItemListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], currentClockId)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View, private val clockItemListener: ClockItemListener) :
        RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {

        private val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)
        private val clockThumbnail: ImageView = itemView.findViewById(R.id.clock_item_thumbnail)
        private val gameType: TextView = itemView.findViewById(R.id.clock_item_game_type)
        private val gameTimes: TextView = itemView.findViewById(R.id.clock_item_game_times)
        private lateinit var chessClock: ChessClock

        companion object {
            fun from(parent: ViewGroup, clockItemListener: ClockItemListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.chess_clock_item_list, parent, false)
                return ViewHolder(view, clockItemListener)
            }
        }

        init {
            itemView.setOnClickListener {
                clockItemListener.onClickItem(chessClock.id)
            }
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bind(clock: ChessClock, currentClockId: Long) {
            this.chessClock = clock
            gameTimes.text = ChessUtils.getTimesText(
                itemView.resources,
                chessClock.firstPlayerTime,
                chessClock.secondPlayerTime,
                chessClock.increment
            )
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
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.black
                    )
                )
                gameType.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                gameTimes.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            } else {
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
                gameType.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                gameTimes.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
            }

        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val editItem = menu?.add("Edit") //todo: extract string
            val deleteItem = menu?.add("Delete")

            editItem?.setOnMenuItemClickListener {
                clockItemListener.onEditItem(chessClock.id)
                true
            }

            deleteItem?.setOnMenuItemClickListener {
                clockItemListener.onRemoveItem(chessClock.id)
                true
            }
        }
    }
}

interface ClockItemListener {
    fun onClickItem(clockId: Long)
    fun onEditItem(clockId: Long)
    fun onRemoveItem(clockId: Long)
}