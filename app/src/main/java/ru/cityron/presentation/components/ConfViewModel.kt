package ru.cityron.presentation.components

import androidx.lifecycle.ViewModel

abstract class ConfViewModel : ViewModel() {
    abstract fun conf(key: String, value: Any)
}