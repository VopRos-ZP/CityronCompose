package ru.cityron.presentation.screens.algo.pi1

sealed interface AlgoPi1ViewIntent {
    data object Launch : AlgoPi1ViewIntent
    data class OnPiAutoEnChange(val value: Int) : AlgoPi1ViewIntent
    data class OnPi1KofPChange(val value: Int) : AlgoPi1ViewIntent
    data class OnPi1KofIChange(val value: Int) : AlgoPi1ViewIntent
    data class OnPi1ErrChange(val value: Int) : AlgoPi1ViewIntent
    data object OnSaveClick : AlgoPi1ViewIntent
}