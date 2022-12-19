package com.example.myapplication.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.TodoListAdapter

class SwipeToCompleteCallback(private var todoListAdapter: TodoListAdapter,
                              private var context: Context,
                              private var isComplete : Boolean
                              ) : ItemTouchHelper.SimpleCallback(0,  ItemTouchHelper.RIGHT){
    private var icon : Drawable = ContextCompat.getDrawable(context,R.drawable.ic_baseline_archive_24)!!
    private var background : ColorDrawable = ColorDrawable(ContextCompat.getColor(context,R.color.isCheckColor))

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (!isComplete) {

            todoListAdapter.onItemComplete(position)
        } else {
            if (direction == ItemTouchHelper.RIGHT) {
                todoListAdapter.onItemNotComplete(position)

            }
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView

        when(isComplete){
            true -> {
                when{
                    dX > 0->{
                        val iconMargin: Int = icon.intrinsicWidth / 2
                        val iconTop: Int =
                            itemView.top + (itemView.height - icon.intrinsicHeight) / 2
                        val iconBottom = iconTop + icon.intrinsicHeight
                        val iconLeft: Int = itemView.left + iconMargin
                        val iconRight: Int =
                            itemView.left + iconMargin + icon.intrinsicWidth

                        icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_unarchive_24)!!
                        background = ColorDrawable(ContextCompat.getColor(context, R.color.redColor))

                        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        background.setBounds(
                            itemView.left, itemView.top,
                            itemView.left + dX.toInt() , itemView.bottom
                        )
                    }

                    else -> { // view is unSwiped
                        background.setBounds(0, 0, 0, 0)
                        icon.setBounds(0,0,0,0)
                    }
                }
                background.draw(c)
                icon.draw(c)
            }
            false ->{
                val iconMargin: Int = icon.intrinsicWidth / 2
                val iconTop: Int =
                    itemView.top + (itemView.height - icon.intrinsicHeight) / 2
                val iconBottom = iconTop + icon.intrinsicHeight
                when {
                    dX > 0 ->{// Swiping to the right

                        icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_archive_24)!!
                        background = ColorDrawable(ContextCompat.getColor(context, R.color.isCheckColor))


                        val iconLeft: Int = itemView.left + iconMargin
                        val iconRight: Int = itemView.left + iconMargin + icon.intrinsicWidth
                        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        background.setBounds(
                            itemView.left  , itemView.top,
                            itemView.left  + dX.toInt(), itemView.bottom
                        )
                    }
                    else -> {
                        background.setBounds(0,0,0,0)
                        icon.setBounds(0,0,0,0)
                    }

                }
                background.draw(c)
                icon.draw(c)
            }

        }

    }




}