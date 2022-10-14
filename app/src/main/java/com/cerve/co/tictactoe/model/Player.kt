package com.cerve.co.tictactoe.model

data class Player(val piece: Piece) {

    enum class Piece(val value: String) {
        X("X"),
        Circle("O"),
        EMPTY("")
    }
}