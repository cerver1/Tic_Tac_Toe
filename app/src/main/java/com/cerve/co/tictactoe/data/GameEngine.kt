package com.cerve.co.tictactoe.data

import androidx.compose.runtime.mutableStateMapOf
import com.cerve.co.tictactoe.model.Player
import com.cerve.co.tictactoe.model.Player.Piece.Circle
import com.cerve.co.tictactoe.model.Player.Piece.EMPTY
import com.cerve.co.tictactoe.model.Position
import com.cerve.co.tictactoe.model.Square
import com.cerve.co.tictactoe.systemutils.Logging.logIt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

fun main() {

    GameEngineState().apply {
        squaresHash.values.forEach(::println)

        placePiece(position = Position(0, 0), Player(Circle))
        placePiece(position = Position(1, 1), Player(Circle))
        placePiece(position = Position(2, 2), Player(Circle))

        println("\n${status.value}\n")
        squaresHash.values.forEach(::println)
    }

}

class GameEngineState(initialHashBoard: HashMap<Position, Square> = Grid3x3Hash) {

    private val _squaresHash = mutableStateMapOf<Position, Square>().apply { putAll(initialHashBoard) }
    val squaresHash: Map<Position, Square> = _squaresHash

    private val _status: MutableStateFlow<GameStatus> = MutableStateFlow(GameStatus.OnGoing)
    val status: StateFlow<GameStatus> = _status.asStateFlow()

    fun placePiece(position: Position, player: Player) {
        "new placement at $position".logIt("placePiece")
        _squaresHash[position]?.let { square ->
            _squaresHash[position] = square.copy(piece = player.piece)
        } //TODO report error if position isn't found in table
        _status.update { updateGameStatus(position, player = player) }
    }

    private fun updateGameStatus(position: Position, player: Player) : GameStatus {

        return when {
            row(position.x, piece = player.piece) ||
            column(position.y, piece = player.piece) ||
            ascendingDiagonal(position, piece = player.piece) ||
            descendingDiagonal(position, piece = player.piece) -> { GameStatus.Won(player) }
            _squaresHash.none { it.value.piece == EMPTY } -> { GameStatus.Tied
            }
            else -> { GameStatus.OnGoing
            }
        }

    }

    sealed class GameStatus {

        object OnGoing : GameStatus()
        object Tied : GameStatus()
        data class Won(val winner: Player) : GameStatus()

    }

    private fun row(x: Int, piece: Player.Piece): Boolean {
        var won = false
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

        inline fun <K, V> Map(size: Int, init: (index: Int) -> Pair<K , V>): HashMap<K, V> {
            val map = hashMapOf<K, V>()
            repeat(size) { index ->
                init(index).also { (key, value) ->
                    map[key] = value
                }
            }
            return map
        }

        val Grid3x3Hash = Map(9) {
            val position = Position(x = it / 3, y = it % 3)

            position to Square(position = position)
        }

    }

}
