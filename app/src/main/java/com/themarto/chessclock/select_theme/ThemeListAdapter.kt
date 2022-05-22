package com.themarto.chessclock.select_theme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.themarto.chessclock.R
import com.themarto.chessclock.select_theme.models.Theme

class ThemeListAdapter (private val context: Context, private val themes: List<Theme>, val onClick: (Int) -> Unit) :
    BaseAdapter() {

    override fun getCount(): Int = themes.size

    override fun getItem(position: Int): Any = themes[position]

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.theme_item, parent, false)

        val colorOne: ImageView = view.findViewById(R.id.color_one)
        val colorTwo: ImageView = view.findViewById(R.id.color_two)
        val isSelected: ImageView = view.findViewById(R.id.is_selected)

        themes[position].let { theme ->
            colorOne.setColorFilter(ContextCompat.getColor(context, theme.playerOneColor))
            colorTwo.setColorFilter(ContextCompat.getColor(context, theme.playerTwoColor))

            if (ThemeUtils.getSelectedThemeId(context) == theme.id) {
                isSelected.visibility = View.VISIBLE
            } else {
                isSelected.visibility = View.INVISIBLE
            }

            view.setOnClickListener {
                onClick(theme.id)
            }
        }

        return view
    }
}
