package ru.cityron.domain.usecase.def

import ru.cityron.data.local.PreferencesStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDefaultUseCase @Inject constructor(
    private val preferencesStore: PreferencesStore
) {

    suspend operator fun invoke(onFirstLaunch: suspend () -> Unit) {
        preferencesStore.isFirstLaunch.collect {
            if (it) {
                onFirstLaunch()
            }
        }
    }

    suspend fun setIsFirstLaunch(value: Boolean) {
        preferencesStore.setFirstLaunch(value)
    }

}