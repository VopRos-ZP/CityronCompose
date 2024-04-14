package com.vopros.cityron.core.data.usecase

abstract class FetchingUseCase {

    protected var isFetching: Boolean = false

    fun start() { isFetching = true }

    fun stop() { isFetching = false }

}