package ru.cityron.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.cityron.domain.model.m3.M3All

interface M3Repository {
    val all: Flow<M3All>
}