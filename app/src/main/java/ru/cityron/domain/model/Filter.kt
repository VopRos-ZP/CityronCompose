package ru.cityron.domain.model

import ru.cityron.domain.utils.Filters

data class Filter(
    val count: Int = Filters.COUNT,
    val types: Int = Filters.TYPES,
    val reasons: Int = Filters.REASONS,
    val sources: Int = Filters.SOURCES,
)
