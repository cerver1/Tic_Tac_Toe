package com.cerve.co.tictactoe.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerve.co.tictactoe.data.GameEngineState
import com.cerve.co.tictactoe.model.Player
import com.cerve.co.tictactoe.model.Position
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicTacToeViewModel @Inject constructor() : ViewModel() {

    private val _ticTacToeState = MutableStateFlow(GameEngineState())
    val ticTacToeState : StateFlow<GameEngineState> = _ticTacToeState.asStateFlow()

    fun updateBoard(
        position: Position, player: Player
    ) = viewModelScope.launch {
        _ticTacToeState.value.placePiece(position, player)
    }

}