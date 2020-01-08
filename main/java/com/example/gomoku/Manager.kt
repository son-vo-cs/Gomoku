package com.example.gomoku

import android.content.Context
import android.view.*
import android.widget.*
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.widget.LinearLayout



class Manager{
    var size = 0
    var width_parent = 0
    var height_parent = 0
    lateinit var cont: Context
    lateinit var layouts: Array<LinearLayout>
    lateinit var parentLayout: LinearLayout
    lateinit var boardBut: BoardButton

    constructor(s: Int, c: Context, w:Int, h: Int, layout:LinearLayout)
    {
        size = s
        cont = c
        width_parent = w
        height_parent = h
        parentLayout = layout
        layouts = Array(s+3){LinearLayout(cont)}
        setUp()

        boardBut = BoardButton(size,width_parent/size as Int,10,10,layouts, c,3, this)

    }

    fun addNotification(won: Boolean)
    {
        var last = layouts.size-1
        val tv_dynamic = TextView(cont)
        tv_dynamic.textSize = 80f
        if (won)
            tv_dynamic.text = "YOU WON"
        else
            tv_dynamic.text = " AI WON"
        tv_dynamic.setPadding(80,80,0,0)
        tv_dynamic.setTextColor(Color.RED)
        tv_dynamic.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM or Gravity.RIGHT or Gravity.LEFT
        // add TextView to LinearLayout
        layouts[last].addView(tv_dynamic)
    }

    fun setUp()
    {

        for (i in 0 until size+3)
        {
            layouts[i] = LinearLayout(cont)
            layouts[i].setLayoutParams(LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            layouts[i].setOrientation(LinearLayout.HORIZONTAL)
            parentLayout.addView(layouts[i])
        }
//        layouts[size+2].setPaddingRelative(50,50,50,50)




    }


}