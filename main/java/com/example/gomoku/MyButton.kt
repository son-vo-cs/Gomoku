package com.example.gomoku
import android.content.Context
import android.view.*
import android.widget.*
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ScaleDrawable
import androidx.core.content.ContextCompat


// a customed button class
// each button will be changed when it's clicked by the user
class MyButton {
    var button : ImageButton
    var row : Int
    var col : Int
    var type : Int
    var used : Boolean
    var black = true
    lateinit var data: Board
    lateinit var dataArr: Array<IntArray>
    lateinit var parent: Array<Array<MyButton?>>


    constructor(but : ImageButton, r : Int, c : Int, x : Int, y : Int, w:Int, layout: LinearLayout, cont : Context, board: Array<Array<MyButton?>>, data:Board)
    {
        button = but
        row = r
        col = c
        type = 0
        used = false
        setUp(x,y,w,layout, cont)
        parent = board
        this.data = data
        dataArr = data.getArrayInt()


    }


    // draw a squre
    fun drawSquare(isSelect: Boolean)
    {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        if (!isSelect)
            drawable.setStroke(2, Color.BLACK)
        else
            drawable.setStroke(2, Color.RED)
        drawable.setColor(Color.YELLOW)

        button.setBackgroundDrawable(drawable)
    }

    fun drawStone(isBlack: Boolean)
    {
        // draw black stone
        if (isBlack && type == 0 && data.winner == 0) {
            drawSquare(true)
            button.setImageResource(R.drawable.black_oval)
            data.put(row,col,2)

        }
        else if (!isBlack && type == 0 && data.winner == 0) {
            drawSquare(true)
            button.setImageResource(R.drawable.white_oval)
            data.put(row,col,1)
        }
    }

    // de-selecting a stone whenever we make a new move
    fun deSelect()
    {
        if (data.winner != 0)
            return
        var prev_row = data.getPrevSelect()[0]
        var prev_col = data.getPrevSelect()[1]

        if (prev_row != -1 && prev_col != -1)
        {
            var isBlack = if (dataArr[prev_row][prev_col] == 2) true else false
            parent[prev_row][prev_col]?.drawSquare(false)
            if (isBlack)
                parent[prev_row][prev_col]?.button?.setImageResource(R.drawable.black_oval)
            else
                parent[prev_row][prev_col]?.button?.setImageResource(R.drawable.white_oval)
        }
    }

    fun initial()
    {
        type = 0;
        black = true;
        drawSquare(false)

    }


    fun setUp(x: Int, y: Int, w: Int, layout: LinearLayout, cont : Context)
    {
        drawSquare(false)
//        button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        button.setOnClickListener {
            deSelect()
            drawStone(black)
            if (data.winner == 0)
            {
                var nextMove = data.nextMove() as IntArray
                deSelect()
                parent[nextMove[0]][nextMove[1]]?.drawStone(!black)

            }

//            var temp = ContextCompat.getDrawable(button.context, R.drawable.white_oval)
//            temp?.setBounds(0,0,15,15)
//            temp?.setLevel(3)
//            button.setImageDrawable(temp)
        }



        // add Button to LinearLayout
    }

    // set location of the stone
    fun setBound(x: Int, y: Int, w: Int)
    {
        val params = RelativeLayout.LayoutParams(w, w) // size of button in dp
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
//        params.setMargins(0, y, x, y)
        button.setLayoutParams(params)
//        button.setLeftTopRightBottom(x,y,0,0)
//        button.set

    }

    fun attach(layout: LinearLayout)
    {
        layout.addView(button)
    }


}