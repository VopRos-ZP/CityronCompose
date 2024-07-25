package ru.cityron.presentation.screens.algo.pi2

sealed interface AlgoPi2ViewIntent {
    data object Launch : AlgoPi2ViewIntent
    data object OnSaveClick : AlgoPi2ViewIntent
    data class OnPi2KofPChange(val value: Int) : AlgoPi2ViewIntent
    data class OnPi2KofIChange(val value: Int) : AlgoPi2ViewIntent
    data class OnPi2ErrChange(val value: Int) : AlgoPi2ViewIntent
}