package ru.cityron.presentation.screens.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.utils.utilsBitGet
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val m3Repository: M3Repository
) : ViewModel() {

    private val _alerts = MutableStateFlow(emptyList<String>())
    val alerts = _alerts.asStateFlow()

    private val titles = arrayOf(
        "Авария приточного вентилятора (DI1)",
        "Авария вытяжного вентилятора (DI2)",
        "Угроза замерзания по термостату/перегрев электронагревателя (DI3)",
        "Засорение входного фильтра (DI4)",
        "Пожарная тревога (DI5)",
        "Ошибка датчика температуры в канале (NTC1)",
        "Ошибка датчика температуры обратной воды (NTC3)",
        "Температура меньше допустимой",
        "Температура больше допустимой",
    )

    fun fetchAlerts() {
        viewModelScope.launch(Dispatchers.IO) {
            m3Repository.state.collect {
                _alerts.value = titles.filterIndexed { i, _ -> utilsBitGet(it.alarms, i) }
            }
        }
    }

}