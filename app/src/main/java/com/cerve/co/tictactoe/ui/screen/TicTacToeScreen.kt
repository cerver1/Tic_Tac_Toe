package com.cerve.co.tictactoe.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cerve.co.tictactoe.data.GameEngineState.Companion.Grid3x3Hash
import com.cerve.co.tictactoe.model.Player
import com.cerve.co.tictactoe.model.Position
import com.cerve.co.tictactoe.model.Square
import com.cerve.co.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun TicTacToeScreen(
    board: Map<Position, Square>,
    placePiece: (Position, Player) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar {

            }
        }
    ) { bounds ->

        BoxWithConstraints(
            modifier = Modifier.padding(bounds),
            contentAlignment = Alignment.Center
        ) {

            val size by remember { mutableStateOf(minOf(maxWidth, maxHeight) / 3) }

            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(3) { x ->
                    Row(
                        modifier = Modifier.width(IntrinsicSize.Max),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(3) { y ->
                            board[Position(x, y)]?.let { square ->

                                BoardTile(
                                    value = square.piece.value,
                                    color = MaterialTheme.colors.primary,
                                    modifier = Modifier.size(size)
                                ) { placePiece(square.position, Player(Player.Piece.Circle)) }

                            }
                        }
                    }

                }

            }

        }

    }
}

@Composable
fun BoardTile(
    value: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.clickable { onClick() },
        contentAlignment = Alignment.Center
    ) { Text(text = value) }
}

@Preview
@Composable
fun TicTacToeScreenPreview() {
    TicTacToeTheme {
        TicTacToeScreen(board = Grid3x3Hash) { _, _ ->

        }
    }
}