package com.example.gomoku





class Minimax {
    private val winScore = 100000000
    lateinit var board: Array<IntArray>

    constructor(b: Array<IntArray>)
    {
        board = b
    }

    fun getWinScore(): Int {
        return winScore
    }



    fun getNextMove(depth: Int): IntArray? {
        var move: IntArray = IntArray(2)
        val startTime = System.currentTimeMillis()

        // Check if we can finish the game right away
        var bestMove = searchWinningMove(board)
        if (bestMove != null) {
            move[0] = bestMove[1] as Int
            move[1] = bestMove[2] as Int

        } else {
            // search best move
            bestMove = minmax(depth, board, true, -1.0, getWinScore().toDouble())
            if (bestMove[1] == null) {
//                move = null
            } else {
                move[0] = bestMove[1] as Int
                move[1] = bestMove[2] as Int
            }
        }

        return move


    }


    /*
    minimax with alpha and beta pruning
	 * */
    private fun minmax(depth: Int, board: Array<IntArray>, max: Boolean, alpha: Double, beta: Double): Array<Any?> {
        var alpha = alpha
        var beta = beta
        if (depth == 0) {


            return arrayOf<Any?>(evaluate(!max), null, null)
        }

        val allPossibleMoves = generateMove(board)

        if (allPossibleMoves.size === 0) {
            return arrayOf<Any?>(evaluate(!max), null, null)
        }

        var bestMove = arrayOfNulls<Any>(3)


        if (max) {
            bestMove[0] = -1.0
            for (move in allPossibleMoves) {
                // place a temporary stone
                addStone(board, move[0], move[1], 1)
                val tempMove = minmax(depth - 1, board, !max, alpha, beta)
                // backtraking, remove the stone
                removeStone(board, move[0], move[1])
                // Updating alpha
                if (tempMove[0] as Double > alpha) {
                    alpha = tempMove[0] as Double
                }
                // Pruning with beta
                if (tempMove[0] as Double >= beta) {
                    return tempMove
                }
                if (tempMove[0] as Double > bestMove[0] as Double) {
                    bestMove = tempMove
                    bestMove[1] = move[0]
                    bestMove[2] = move[1]
                }
            }

        } else {
            bestMove[0] = 100000000.0
            bestMove[1] = allPossibleMoves[0][0]
            bestMove[2] = allPossibleMoves[0][1]
            for (move in allPossibleMoves) {
                // place a temporary stone
                addStone(board, move[0], move[1], 2)
                val tempMove = minmax(depth - 1, board, !max, alpha, beta)
                // backtracking, remove the stone
                removeStone(board, move[0], move[1])
                if ((tempMove[0] as Double) < beta) {
                    beta = tempMove[0] as Double
                }
                // Pruning with alpha
                if (tempMove[0] as Double <= alpha) {
                    return tempMove
                }
                var temp1 = tempMove[0] as Double
                var temp2 = bestMove[0] as Double
                if (temp1 < temp2)
                {
                    bestMove = tempMove
                    bestMove[1] = move[0]
                    bestMove[2] = move[1]
                }
            }
        }
        return bestMove
    }

    fun generateMove(board: Array<IntArray>): ArrayList<IntArray> {
        val result = ArrayList<IntArray>()
        for (i in board.indices) {
            for (j in 0 until board[i].size) {
                if (validMove(i, j)) {
                    val temp = IntArray(2)
                    temp[0] = i
                    temp[1] = j
                    result.add(temp)
                }
            }
        }
        return result
    }

    fun validMove(row: Int, col: Int): Boolean {
        if (board[row][col] > 0)
            return false;

        val direction = intArrayOf(0, 1, -1)
        for (i in direction) {
            for (j in direction) {
                if (i == 0 && j == 0)
                    continue
                val new_row = row + i
                val new_col = col + j
                if (new_row < 0 || new_col < 0 || new_row >= board[0].size || new_col >= board[0].size)
                    continue
                if (board[new_row][new_col] > 0)
                    return true
            }
        }
        return false


    }


    private fun searchWinningMove(board: Array<IntArray>): Array<Any?>? {
        val allPossibleMoves = generateMove(board)
        val winningMove = arrayOfNulls<Any>(3)

        for (move in allPossibleMoves) {
            // place a temporary stone
            addStone(board, move[0], move[1], 1)
            val temp_score = getScore(false, false)
            // backtracking, remove the stone
            removeStone(board, move[0], move[1])
            if (temp_score >= winScore) {
                winningMove[1] = move[0]
                winningMove[2] = move[1]
                return winningMove
            }
        }
        return null

    }

    fun evaluateHorizontal(forBlack: Boolean, playersTurn: Boolean): Int {
        var score = 0
        for (i in board.indices)
        {
            var consecutive = 0
            var blocks = 1
            for (j in 0 until board[0].size)
            {
                if (j == board[0].size - 1)
                    blocks++
                if (board[i][j] == (if (forBlack) 2 else 1))
                {
                    consecutive++
                    if (consecutive >= 5)
                        return winScore
                }
                else if (board[i][j] == 0)
                {
                    if (consecutive > 0)
                        score += evaluateConsec(consecutive, blocks, forBlack == playersTurn)
                    consecutive = 0
                    blocks = 0
                }
                else
                {
                    if (consecutive > 0)
                    {
                        blocks++
                        score += evaluateConsec(consecutive, blocks, forBlack == playersTurn)

                    }
                    blocks = 1
                    consecutive = 0
                }
            }
            if (consecutive > 0)
                score += evaluateConsec(consecutive, blocks, forBlack == playersTurn)

        }
        return score
    }

    fun evaluateVertical(forBlack: Boolean, playersTurn: Boolean): Int {

        var score = 0
        //		for (int[] move : recentMoves) {
        //			int j = move[1];
        for (j in 0 until board[0].size) {
            var consecutive = 0
            var blocks = 1
            for (i in board.indices)
            {
                if (i == board[0].size - 1)
                    blocks++
                if (board[i][j] == (if (forBlack) 2 else 1))
                {
                    consecutive++
                    if (consecutive >= 5)
                        return winScore
                }
                else if (board[i][j] == 0)
                {
                    if (consecutive > 0)
                        score += evaluateConsec(consecutive, blocks, forBlack == playersTurn)
                    blocks = 0
                    consecutive = 0
                }
                else
                {
                    if (consecutive > 0)
                    {
                        blocks++
                        score += evaluateConsec(consecutive, blocks, forBlack == playersTurn)
                    }
                    consecutive = 0
                    blocks = 1
                }

            }
            if (consecutive > 0)
                score += evaluateConsec(consecutive, blocks, forBlack == playersTurn)

        }
        return score
    }
    fun evaluateDiagonal(forBlack: Boolean, playersTurn: Boolean): Int {
        var score = 0
        // From bottom-left to top-right diagonally
        for (k in 0..2 * (board.size - 1)) {
            val iStart = Math.max(0, k - board.size + 1)
            val iEnd = Math.min(board.size - 1, k)
            var consecutive = 0
            var blocks = 1
            for (i in iStart..iEnd) {
                val j = k - i

                if (i == board[0].size - 1)
                    blocks++
                if (board[i][j] == (if (forBlack) 2 else 1))
                {
                    consecutive++
                    if (consecutive >= 5)
                        return winScore
                }
                else if (board[i][j] == 0)
                {
                    if (consecutive > 0)
                        score += evaluateConsec(consecutive, blocks, forBlack == playersTurn)
                    blocks = 0
                    consecutive = 0
                }
                else
                {
                    if (consecutive > 0)
                    {
                        blocks++
                        score += evaluateConsec(consecutive, blocks, forBlack == playersTurn)
                    }
                    consecutive = 0
                    blocks = 1
                }

            }
            if (consecutive > 0) {
                score += evaluateConsec(consecutive, blocks, forBlack == playersTurn)

            }
        }
        // From top-left to bottom-right diagonally
        for (k in 1 - board.size until board.size) {
            val iStart = Math.max(0, k)
            val iEnd = Math.min(board.size + k - 1, board.size - 1)
            var consecutive = 0
            var blocks = 1
            for (i in iStart..iEnd) {
                val j = i - k

                if (i == board[0].size - 1)
                    blocks++
                if (board[i][j] == (if (forBlack) 2 else 1))
                {
                    consecutive++
                    if (consecutive >= 5)
                        return winScore
                }
                else if (board[i][j] == 0)
                {
                    if (consecutive > 0)
                        score += evaluateConsec(consecutive, blocks, forBlack == playersTurn)
                    blocks = 0
                    consecutive = 0
                }
                else
                {
                    if (consecutive > 0)
                    {
                        blocks++
                        score += evaluateConsec(consecutive, blocks, forBlack == playersTurn)
                    }
                    consecutive = 0
                    blocks = 1
                }

            }
            if (consecutive > 0) {
                score += evaluateConsec(consecutive, blocks, forBlack == playersTurn)

            }
        }
        return score
    }

    fun evaluateConsec(count: Int, blocks: Int, currentTurn: Boolean): Int {
        if (blocks == 2 && count < 5) return 0
        when (count) {
            5 -> {
                return winScore
            }
            4 -> {
                return if (currentTurn)
                {
                    1000000
                }
                else {
                    if (blocks == 0)
                        250000
                    else
                        300
                }
            }
            3 -> {
                return if (blocks == 0) {
                    if (currentTurn)
                        50000
                    else
                        300
                } else {
                    if (currentTurn)
                        20
                    else
                        7
                }
            }
            2 -> {
                return if (blocks == 0) {
                    if (currentTurn)
                        9
                    else
                        7
                } else {
                    5
                }
            }
            1 -> {
                return 1
            }
        }
        return winScore * 2
    }


    fun evaluate(blacksTurn: Boolean): Double {

        var blackScore = getScore(true, blacksTurn).toDouble()
        val whiteScore = getScore(false, blacksTurn).toDouble()

        if (blackScore == 0.0) blackScore = 1.0
        //		recentMoves.clear();

        return whiteScore / blackScore

    }


    fun addStone(board: Array<IntArray>, row: Int, col: Int, kind: Int) {
        board[row][col] = kind
    }

    fun removeStone(board: Array<IntArray>, row: Int, col: Int) {
        board[row][col] = 0
    }


    fun getScore(forBlack: Boolean, blacksTurn: Boolean): Int {

        return evaluateHorizontal(forBlack, blacksTurn) +
                evaluateVertical(forBlack, blacksTurn) +
                evaluateDiagonal(forBlack, blacksTurn)
    }
}