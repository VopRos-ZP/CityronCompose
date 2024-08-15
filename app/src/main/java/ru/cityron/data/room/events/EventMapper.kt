package ru.cityron.data.room.events

import ru.cityron.domain.model.Event

fun EventDto.fromDto() = Event(
    id = id,
    date = date,
    time = time,
    type = type,
    text = text,
)

fun Event.toDto() = EventDto(
    id = id,
    date = date,
    time = time,
    type = type,
    text = text,
)