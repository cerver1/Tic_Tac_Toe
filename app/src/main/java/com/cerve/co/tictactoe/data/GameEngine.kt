package com.cerve.co.tictactoe.data


import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import com.cerve.co.tictactoe.data.GameEngineState.Companion.Grid3x3
import com.cerve.co.tictactoe.data.GameEngineState.Companion.example
import com.cerve.co.tictactoe.data.model.Player
import com.cerve.co.tictactoe.data.model.Player.Piece.EMPTY
import com.cerve.co.tictactoe.data.model.Player.Piece.X

fun main(args: Array<String>) {
    GameEngineState.exampleHash.values.forEach(::println)
    println()
    println(
        GameEngineState()
            .updateGameStatus(
                position = Position(2, 2),
                piece = X
            )
    )
}

class GameEngineState(
    initialBoard: List<Square> = Grid3x3,
    initialHashBoard: HashMap<Position, Square> = exampleHash
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

        return row(row, piece = piece)
//        return _squares.all {
//            it.position.x == row && it.piece == piece
//        }

    }

    sealed class GameStatus(val player: Player? = null) {

        class OnGoing: GameStatus()
        class Tied: GameStatus()
        data class Won(val winner: Player) : GameStatus(winner)

    }

    fun row(x: Int, piece: Player.Piece) : Boolean {
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

        val exampleHash = hashMapOf(
            Position(x=0, y=0) to Square(piece=EMPTY, position=Position(x=0, y=0)),
            Position(x=0, y=1) to Square(piece=EMPTY, position=Position(x=0, y=1)),
            Position(x=0, y=2) to Square(piece=EMPTY, position=Position(x=0, y=2)),
            Position(x=1, y=0) to Square(piece=EMPTY, position=Position(x=1, y=0)),
            Position(x=1, y=1) to Square(piece=EMPTY, position=Position(x=1, y=1)),
            Position(x=1, y=2) to Square(piece=EMPTY, position=Position(x=1, y=2)),
            Position(x=2, y=0) to Square(piece=X, position=Position(x=2, y=0)),
            Position(x=2, y=1) to Square(piece=X, position=Position(x=2, y=1)),
            Position(x=2, y=2) to Square(piece=X, position=Position(x=2, y=2))
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