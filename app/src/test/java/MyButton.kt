//package com.example.gomoku
//
//import java.lang.reflect.Constructor
////import com.example.gomoku.Board
////import com.example.gomoku.Board
//
//
//
//
//
//
//class Minimax {
//    private val winScore = 100000000
//    lateinit var board: Array<IntArray>
//
//    constructor(b: Array<IntArray>)
//    {
//        board = b
//    }
//
//    fun getWinScore(): Int {
//        return winScore
//    }
//
//    fun evaluateBoardForWhite(blacksTurn: Boolean): Double {
//
//        var blackScore = getScore(true, blacksTurn).toDouble()
//        val whiteScore = getScore(false, blacksTurn).toDouble()
//
//        if (blackScore == 0.0) blackScore = 1.0
//        //		recentMoves.clear();
//
//        return whiteScore / blackScore
//
//    }
//
//    fun getScore(forBlack: Boolean, blacksTurn: Boolean): Int {
//
//        return evaluateHorizontal(forBlack, blacksTurn) +
//                evaluateVertical(forBlack, blacksTurn) +
//                evaluateDiagonal(forBlack, blacksTurn)
//    }
//
//    fun calculateNextMove(depth: Int): IntArray? {
//        //		board.thinkingStarted();
//        var move: IntArray = IntArray(2)
//        val startTime = System.currentTimeMillis()
//        // Check if any available move can finish the game
//        //		recentMoves.clear();
//        var bestMove = searchWinningMove(board)
//        if (bestMove != null) {
//            move[0] = bestMove[1] as Int
//            move[1] = bestMove[2] as Int
//
//        } else {
//            // If there is no such move, search the minimax tree with suggested depth.
//            bestMove = minimaxSearchAB(depth, board, true, -1.0, getWinScore().toDouble())
//            if (bestMove[1] == null) {
////                move = null
//            } else {
//                move[0] = bestMove[1] as Int
//                move[1] = bestMove[2] as Int
//            }
//        }
//
//        return move
//
//
//    }
//
//
//    /*
//	 * alpha : Best AI Move (Max)
//	 * beta : Best Player Move (Min)
//	 * returns: {score, move[0], move[1]}
//	 * */
//    private fun minimaxSearchAB(
//        depth: Int,
//        board: Array<IntArray>,
//        max: Boolean,
//        alpha: Double,
//        beta: Double
//    ): Array<Any?> {
//        var alpha = alpha
//        var beta = beta
//        if (depth == 0) {
//
//
////			recentMoves.clear();
//            return arrayOf<Any?>(evaluateBoardForWhite(!max), null, null)
//        }
//
//        val allPossibleMoves = generateMove(board)
//
//        if (allPossibleMoves.size === 0) {
//
////			recentMoves.clear();
//            return arrayOf<Any?>(evaluateBoardForWhite(!max), null, null)
//        }
//
//        var bestMove = arrayOfNulls<Any>(3)
//
//
//        if (max) {
//            bestMove[0] = -1.0
//            // Iterate for all possible moves that can be made.
//            for (move in allPossibleMoves) {
//                // Create a temporary board that is equivalent to the current board
//                //				Board dummyBoard = new Board(board);
//                // Play the move to that temporary board without drawing anything
//                //				dummyBoard.addStoneNoGUI(move[1], move[0], false);
//                addStone(board, move[0], move[1], 1)
//                //				recentMoves.add(move);
//                // Call the minimax function for the next depth, to look for a minimum score.
//                val tempMove = minimaxSearchAB(depth - 1, board, !max, alpha, beta)
//
//                removeStone(board, move[0], move[1])
//                // Updating alpha
//                if (tempMove[0] as Double > alpha) {
//                    alpha = tempMove[0] as Double
//                }
//                // Pruning with beta
//                if (tempMove[0] as Double >= beta) {
//                    return tempMove
//                }
//                if (tempMove[0] as Double > bestMove[0] as Double) {
//                    bestMove = tempMove
//                    bestMove[1] = move[0]
//                    bestMove[2] = move[1]
//                }
//            }
//
//        } else {
//            bestMove[0] = 100000000.0
//            bestMove[1] = allPossibleMoves[0][0]
//            bestMove[2] = allPossibleMoves[0][1]
//            for (move in allPossibleMoves) {
//                //				Board dummyBoard = new Board(board);
//                //				dummyBoard.addStoneNoGUI(move[1], move[0], true);
//                addStone(board, move[0], move[1], 2)
//                //				recentMoves.add(move);
//                val tempMove = minimaxSearchAB(depth - 1, board, !max, alpha, beta)
//                removeStone(board, move[0], move[1])
//                // Updating beta
//                if ((tempMove[0] as Double) < beta) {
//                    beta = tempMove[0] as Double
//                }
//                // Pruning with alpha
//                if (tempMove[0] as Double <= alpha) {
//                    return tempMove
//                }
//                var temp1 = tempMove[0] as Double
//                var temp2 = bestMove[0] as Double
//                if (temp1 < temp2)
//                {
//                    bestMove = tempMove
//                    bestMove[1] = move[0]
//                    bestMove[2] = move[1]
//                }
//            }
//        }
//        return bestMove
//    }
//
//    private fun searchWinningMove(board: Array<IntArray>): Array<Any?>? {
//        val allPossibleMoves = generateMove(board)
//        val winningMove = arrayOfNulls<Any>(3)
//
//        // Iterate for all possible moves
//        for (move in allPossibleMoves) {
//            // Create a temporary board that is equivalent to the current board
//            //			Board dummyBoard = new Board(board);
//            // Play the move to that temporary board without drawing anything
//            //			dummyBoard.addStoneNoGUI(move[1], move[0], false);
//            addStone(board, move[0], move[1], 1)
//
//            val temp_score = getScore(false, false)
//            removeStone(board, move[0], move[1])
//            // If the white player has a winning score in that temporary board, return the move.
//            if (temp_score >= winScore) {
//                winningMove[1] = move[0]
//                winningMove[2] = move[1]
//                return winningMove
//            }
//        }
//        return null
//
//    }
//
//    fun evaluateHorizontal(forBlack: Boolean, playersTurn: Boolean): Int {
//
//        var consecutive = 0
//        var blocks = 2
//        var score = 0
//
//        for (i in board.indices) {
//            for (j in 0 until board[0].size) {
//
//                if (board[i][j] == (if (forBlack) 2 else 1)) {
//                    consecutive++
//                } else if (board[i][j] == 0) {
//                    if (consecutive > 0) {
//                        blocks--
//                        score += getConsecutiveSetScore(
//                            consecutive,
//                            blocks,
//                            forBlack == playersTurn
//                        )
//                        consecutive = 0
//                        blocks = 1
//                    } else {
//                        blocks = 1
//                    }
//                } else if (consecutive > 0) {
//                    score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn)
//                    consecutive = 0
//                    blocks = 2
//                } else {
//                    blocks = 2
//                }
//
//            }
//            if (consecutive > 0) {
//                score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn)
//
//            }
//            consecutive = 0
//            blocks = 2
//
//        }
//        return score
//    }
//
//    fun evaluateVertical(forBlack: Boolean, playersTurn: Boolean): Int {
//
//        var consecutive = 0
//        var blocks = 2
//        var score = 0
//        //		for (int[] move : recentMoves) {
//        //			int j = move[1];
//        for (j in 0 until board[0].size) {
//            for (i in board.indices) {
//                if (board[i][j] == (if (forBlack) 2 else 1)) {
//                    consecutive++
//                } else if (board[i][j] == 0) {
//                    if (consecutive > 0) {
//                        blocks--
//                        score += getConsecutiveSetScore(
//                            consecutive,
//                            blocks,
//                            forBlack == playersTurn
//                        )
//                        consecutive = 0
//                        blocks = 1
//                    } else {
//                        blocks = 1
//                    }
//                } else if (consecutive > 0) {
//                    score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn)
//                    consecutive = 0
//                    blocks = 2
//                } else {
//                    blocks = 2
//                }
//            }
//            if (consecutive > 0) {
//                score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn)
//
//            }
//            consecutive = 0
//            blocks = 2
//
//        }
//        return score
//    }
//
//    fun evaluateDiagonal(forBlack: Boolean, playersTurn: Boolean): Int {
//        var consecutive = 0
//        var blocks = 2
//        var score = 0
//        // From bottom-left to top-right diagonally
//        for (k in 0..2 * (board.size - 1)) {
//            val iStart = Math.max(0, k - board.size + 1)
//            val iEnd = Math.min(board.size - 1, k)
//            for (i in iStart..iEnd) {
//                val j = k - i
//
//                if (board[i][j] == (if (forBlack) 2 else 1)) {
//                    consecutive++
//                } else if (board[i][j] == 0) {
//                    if (consecutive > 0) {
//                        blocks--
//                        score += getConsecutiveSetScore(
//                            consecutive,
//                            blocks,
//                            forBlack == playersTurn
//                        )
//                        consecutive = 0
//                        blocks = 1
//                    } else {
//                        blocks = 1
//                    }
//                } else if (consecutive > 0) {
//                    score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn)
//                    consecutive = 0
//                    blocks = 2
//                } else {
//                    blocks = 2
//                }
//
//            }
//            if (consecutive > 0) {
//                score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn)
//
//            }
//            consecutive = 0
//            blocks = 2
//        }
//        // From top-left to bottom-right diagonally
//        for (k in 1 - board.size until board.size) {
//            val iStart = Math.max(0, k)
//            val iEnd = Math.min(board.size + k - 1, board.size - 1)
//            for (i in iStart..iEnd) {
//                val j = i - k
//
//                if (board[i][j] == (if (forBlack) 2 else 1)) {
//                    consecutive++
//                } else if (board[i][j] == 0) {
//                    if (consecutive > 0) {
//                        blocks--
//                        score += getConsecutiveSetScore(
//                            consecutive,
//                            blocks,
//                            forBlack == playersTurn
//                        )
//                        consecutive = 0
//                        blocks = 1
//                    } else {
//                        blocks = 1
//                    }
//                } else if (consecutive > 0) {
//                    score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn)
//                    consecutive = 0
//                    blocks = 2
//                } else {
//                    blocks = 2
//                }
//
//            }
//            if (consecutive > 0) {
//                score += getConsecutiveSetScore(consecutive, blocks, forBlack == playersTurn)
//
//            }
//            consecutive = 0
//            blocks = 2
//        }
//        return score
//    }
//
//    fun getConsecutiveSetScore(count: Int, blocks: Int, currentTurn: Boolean): Int {
//        val winGuarantee = 1000000
//        if (blocks == 2 && count < 5) return 0
//        when (count) {
//            5 -> {
//                return winScore
//            }
//            4 -> {
//                return if (currentTurn)
//                    winGuarantee
//                else {
//                    if (blocks == 0)
//                        winGuarantee / 4
//                    else
//                        200
//                }
//            }
//            3 -> {
//                return if (blocks == 0) {
//                    if (currentTurn)
//                        50000
//                    else
//                        200
//                } else {
//                    if (currentTurn)
//                        10
//                    else
//                        5
//                }
//            }
//            2 -> {
//                return if (blocks == 0) {
//                    if (currentTurn)
//                        7
//                    else
//                        5
//                } else {
//                    3
//                }
//            }
//            1 -> {
//                return 1
//            }
//        }
//        return winScore * 2
//    }
//
//
//    fun generateMove(board: Array<IntArray>): ArrayList<IntArray> {
//        val result = ArrayList<IntArray>()
//        for (i in board.indices) {
//            for (j in 0 until board[i].size) {
//                if (validMove(i, j)) {
//                    val temp = IntArray(2)
//                    temp[0] = i
//                    temp[1] = j
//                    result.add(temp)
//                }
//            }
//        }
//        return result
//    }
//
//    fun validMove(row: Int, col: Int): Boolean {
//        if (board[row][col] > 0)
//            return false;
//
//        val direction = intArrayOf(0, 1, -1)
//        for (i in direction) {
//            for (j in direction) {
//                if (i == 0 && j == 0)
//                    continue
//                val new_row = row + i
//                val new_col = col + j
//                if (new_row < 0 || new_col < 0 || new_row >= board[0].size || new_col >= board[0].size)
//                    continue
//                if (board[new_row][new_col] > 0)
//                    return true
//            }
//        }
//        return false
//
//
//    }
//
//    fun addStone(board: Array<IntArray>, row: Int, col: Int, kind: Int) {
//        board[row][col] = kind
//    }
//
//    fun removeStone(board: Array<IntArray>, row: Int, col: Int) {
//        board[row][col] = 0
//    }
//}