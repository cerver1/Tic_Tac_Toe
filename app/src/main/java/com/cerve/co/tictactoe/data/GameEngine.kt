package com.cerve.co.tictactoe.data


import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.Matrix
import com.cerve.co.tictactoe.data.GameEngineState.Companion.ascendingDiagonalExampleHash
import com.cerve.co.tictactoe.data.GameEngineState.Companion.descendingDiagonalExampleHash
import com.cerve.co.tictactoe.data.model.Player
import com.cerve.co.tictactoe.data.model.Player.Piece.EMPTY
import com.cerve.co.tictactoe.data.model.Player.Piece.X

fun main(args: Array<String>) {
    ascendingDiagonalExampleHash.values.forEach(::println)

    println()
    println(
        GameEngineState(initialHashBoard = ascendingDiagonalExampleHash)
            .updateGameStatus(
                position = Position(0, 2),
                piece = X
            )
    )
}

class GameEngineState(
    initialBoard: List<Square> = Grid3x3,
    initialHashBoard: HashMap<Position, Square>
) {

    private val _squares: MutableList<Square> = mutableStateListOf(*initialBoard.toTypedArray())
    val squares: List<Square> = _squares

    private val _squaresHash: MutableMap<Position, Square> = mutableStateMapOf<Position, Square>().apply { putAll(initialHashBoard) }
    val squaresHash: Map<Position, Square> = _squaresHash

    var status : GameStatus = GameStatus.OnGoing()

    fun placePiece(position: Int, piece: Player.Piece) {
        _squares[position] = _squares[position].copy(piece = piece)
    }

    fun updateGameStatus(position: Position, piece: Player.Piece) : Boolean {
        //TODO vertical win condition

        val column = position.y
        val row = position.x

//       return _squares.filter {
//           it.position.y == column
//       }

//        return row(row, piece = piece)
//        return column(column, piece = piece)
        return ascendingDiagonal(position, piece = piece)
//        return _squares.all {
//            it.position.x == row && it.piece == piece
//        }

    }

    sealed class GameStatus(val player: Player? = null) {

        class OnGoing: GameStatus()
        class Tied: GameStatus()
        data class Won(val winner: Player) : GameStatus(winner)

    }

    private fun row(x: Int, piece: Player.Piece): Boolean {
        var won: Boolean = false
        var point = 0

        while (point < 3) {

            won = _squaresHash[Position(x, y = point)]?.piece?.let {
                it == piece
            } ?: false

            if (!won) {
                break
            }

            point++
        }

        return won
    }
    private fun column(y: Int, piece: Player.Piece): Boolean {
        var won = false
        var point = 0

        while (point < 3) {
            won = _squaresHash[Position(point, y = y)]?.piece?.let {
                it == piece
            } ?: false

            if (!won) {
                break
            }

            point++
        }

        return won
    }

    private fun ascendingDiagonal(position: Position, piece: Player.Piece) : Boolean {

        if(position.x != (3 - position.y -1)) return false

        var won = false
        var points = 0

        while (!won || points < 3) {

            val x = (3 - points -1)

            println("\nx: $x, y: $points")

            won = _squaresHash[Position(x,points)]?.piece?.let {
                it == piece
            } ?: false

            if (!won) {
                break
            }

            points++
        }

        return won
    }

    /*
     * Description:
     *  Prints the secondary diagonal of a square matrix
     * Parameters:
     *  mat - a pointer to the the matrix
     *  rows - the number of rows
     *  columns - the number of columns
     * Returns:
     *  Nothing
     */


    private fun descendingDiagonal(position: Position, piece: Player.Piece): Boolean {

       if(position.x != position.y) return false

        var won = false
        var points = 0

        while (!won || points < 3) {

            won = _squaresHash[Position(points,points)]?.piece?.let {
                it == piece
            } ?: false

            if (!won) {
                break
            }

            points++
        }

        return won

    }

    companion object {
        val Grid3x3 = List(9) {
            Square(
                piece = EMPTY,
                position = Position(x = it / 3, y = it % 3)
            )
        }

        val example = listOf(
            Square(piece=EMPTY, position=Position(x=0, y=0)),
            Square(piece=EMPTY, position=Position(x=0, y=1)),
            Square(piece=EMPTY, position=Position(x=0, y=2)),
            Square(piece=EMPTY, position=Position(x=1, y=0)),
            Square(piece=EMPTY, position=Position(x=1, y=1)),
            Square(piece=EMPTY, position=Position(x=1, y=2)),
            Square(piece=X, position=Position(x=2, y=0)),
            Square(piece=X, position=Position(x=2, y=1)),
            Square(piece=X, position=Position(x=2, y=2))
        )

        val rowAndColumnExampleHash = hashMapOf(
            Position(x=0, y=0) to Square(piece=EMPTY, position=Position(x=0, y=0)),
            Position(x=0, y=1) to Square(piece=EMPTY, position=Position(x=0, y=1)),
            Position(x=0, y=2) to Square(piece=X, position=Position(x=0, y=2)),
            Position(x=1, y=0) to Square(piece=EMPTY, position=Position(x=1, y=0)),
            Position(x=1, y=1) to Square(piece=EMPTY, position=Position(x=1, y=1)),
            Position(x=1, y=2) to Square(piece=X, position=Position(x=1, y=2)),
            Position(x=2, y=0) to Square(piece=X, position=Position(x=2, y=0)),
            Position(x=2, y=1) to Square(piece=X, position=Position(x=2, y=1)),
            Position(x=2, y=2) to Square(piece=X, position=Position(x=2, y=2))
        )

        val descendingDiagonalExampleHash = hashMapOf(
            Position(x=0, y=0) to Square(piece=X, position=Position(x=0, y=0)),
            Position(x=0, y=1) to Square(piece=EMPTY, position=Position(x=0, y=1)),
            Position(x=0, y=2) to Square(piece=EMPTY, position=Position(x=0, y=2)),
            Position(x=1, y=0) to Square(piece=EMPTY, position=Position(x=1, y=0)),
            Position(x=1, y=1) to Square(piece=X, position=Position(x=1, y=1)),
            Position(x=1, y=2) to Square(piece=EMPTY, position=Position(x=1, y=2)),
            Position(x=2, y=0) to Square(piece=EMPTY, position=Position(x=2, y=0)),
            Position(x=2, y=1) to Square(piece=EMPTY, position=Position(x=2, y=1)),
            Position(x=2, y=2) to Square(piece=X, position=Position(x=2, y=2))
        )

        val ascendingDiagonalExampleHash = hashMapOf(
            Position(x=0, y=0) to Square(piece=EMPTY, position=Position(x=0, y=0)),
            Position(x=0, y=1) to Square(piece=EMPTY, position=Position(x=0, y=1)),
            Position(x=0, y=2) to Square(piece=X, position=Position(x=0, y=2)),
            Position(x=1, y=0) to Square(piece=EMPTY, position=Position(x=1, y=0)),
            Position(x=1, y=1) to Square(piece=X, position=Position(x=1, y=1)),
            Position(x=1, y=2) to Square(piece=EMPTY, position=Position(x=1, y=2)),
            Position(x=2, y=0) to Square(piece=X, position=Position(x=2, y=0)),
            Position(x=2, y=1) to Square(piece=EMPTY, position=Position(x=2, y=1)),
            Position(x=2, y=2) to Square(piece=EMPTY, position=Position(x=2, y=2))
        )
    }

}

@Immutable
data class Square(
    val piece: Player.Piece,
    val position: Position
)

@Immutable
data class Position(
    val x: Int,
    val y: Int
)