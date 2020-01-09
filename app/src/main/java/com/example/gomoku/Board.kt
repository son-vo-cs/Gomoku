package com.example.gomoku
import android.content.Context
import android.view.*
import android.widget.*
import android.graphics.*
import android.graphics.drawable.GradientDrawable


// A board that contains 2d array int
class Board{
    lateinit var board: Array<IntArray>
    var startRow: Int = 0
    var endRow: Int = 0
    var startCol: Int = 0
    var endCol: Int = 0
    var total = 0
    var putDistance: Int = 1
    var s: Int = 0
    var depth = 0
    lateinit var minimax: Minimax
    var prev_select = IntArray(2)
    var winner = 0

    // depth: how deep we need to search on a tree in Minimax algorithm
    constructor(size: Int, depth: Int, manage: Manager) {
        board = Array(size) { IntArray(size) }
        s = size
        minimax = Minimax(board)
        initial()
        this.depth =depth

    }

    fun initial()
    {
        for (i in 0 until s)
            for (j in 0 until s)
                board[i][j] = 0
        prev_select[0] = -1
        prev_select[1] = -1

    }

    fun setPrevSelect(row: Int, col: Int)
    {
        prev_select[0] = row
        prev_select[1] = col
    }

    fun getPrevSelect() :  IntArray
    {
        return prev_select
    }



    fun getArrayInt(): Array<IntArray> {
        return board
    }



    // check if is there any available room for placing a stone
    fun isEmpty(): Boolean {
        return total == board.size * board.size
    }

    fun put(row: Int, col: Int, num: Int) {
        if (board[row][col] !=0)
            return
        board[row][col] = num
        total++;
        setPrevSelect(row,col)
        checkWinner(num)

    }

    fun setPut(p: Int) {
        putDistance = p
    }

    fun nextMove(): IntArray?
    {
        var move = minimax.getNextMove(depth)
        return move
    }

    fun checkWinner(num: Int)
    {
        var isBlack = if (num == 1) true else false
        if(minimax.getScore(true, isBlack) >= minimax.getWinScore())
        {
            winner = 2
        };
        if(minimax.getScore(false, !isBlack) >= minimax.getWinScore()){
            winner =  1
        };

    }
    fun getWiner(): Int
    {
        return winner;
    }

}