package com.cerve.co.tictactoe.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cerve.co.tictactoe.ui.screen.TicTacToeScreen
import com.cerve.co.tictactoe.ui.state.TicTacToeViewModel
import com.cerve.co.tictactoe.ui.theme.TicTacToeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm : TicTacToeViewModel = viewModel()

            TicTacToeTheme {

                val state by vm.ticTacToeState.collectAsState()

                val status by state.status.collectAsState()

                TicTacToeScreen(board = state.squaresHash) { position, player ->
                    vm.updateBoard(position, player)
                }


            }
        }
    }
}
