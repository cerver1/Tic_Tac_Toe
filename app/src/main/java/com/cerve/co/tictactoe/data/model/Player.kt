package com.cerve.co.tictactoe.data.model

data class Player(val piece: Piece) {

    enum class Piece {
        X,
        Circle,
        EMPTY
    }
}