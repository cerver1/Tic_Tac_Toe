package com.cerve.co.tictactoe.model

import androidx.compose.runtime.Immutable

@Immutable
data class Square(
    val piece: Player.Piece = Player.Piece.EMPTY,
    val position: Position
)

