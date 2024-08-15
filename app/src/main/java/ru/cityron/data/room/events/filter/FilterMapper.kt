package ru.cityron.data.room.events.filter

import ru.cityron.domain.model.Filter

fun FilterDto.fromDto() = Filter(
    count = count,
    types = types,
    reasons = reasons,
    sources = sources
)

fun Filter.toDto() = FilterDto(
    count = count,
    types = types,
    reasons = reasons,
    sources = sources
)
