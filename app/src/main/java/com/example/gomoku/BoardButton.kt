package com.example.gomoku
import android.content.Context
import android.view.*
import android.widget.*
import android.graphics.*
import android.graphics.drawable.GradientDrawable


class BoardButton{
    var w: Int = 0
    var h: Int = 0
    var current_p: Int = 0
    var s: Int = 0
    var startX = 0
    var startY = 0
    var prev_select = IntArray(2)
    var depth = 0


//    lateinit var recent: IntArray
    lateinit var board: Array<Array<MyButton?>>
    lateinit var data: Board
    constructor(s: Int, w: Int, x: Int, y: Int,layouts: Array<LinearLayout>, cont: Context, d: Int, manager: Manager){
        depth = d
        this.w = w
        this.s = s
//        recent = location
        data = Board(s,depth, manager)
        startX = x
        startY = y
        board = Array<Array<MyButton?>>(s) { arrayOfNulls<MyButton>(s) }
        for (i in 0 until s) {
            for (j in 0 until s)
            {
                var tempBut = ImageButton(cont)
                board[i][j] = MyButton(tempBut, i, j, 0, 0, 0, layouts[i+2], cont, board,data,manager)
            }
        }
        prev_select[0] = -1
        prev_select[1] = -1
        setBound(layouts)
    }



    fun setBound(layouts: Array<LinearLayout>) {
        var x = startX
        var y = startY
        for (i in 0 until s) {
            for (j in 0 until s) {
                //				System.out.print("(" + x + "," + y+")" + " ");
                board[i][j]?.setBound(x, y, w)
                board[i][j]?.attach(layouts[i+2])
                x = x + w
            }
            x = startX
            y = startY + (i + 1) * w
//            println()
        }
    }




}