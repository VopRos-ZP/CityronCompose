package ru.cityron.data.room.all.state

import ru.cityron.domain.model.Set
import ru.cityron.domain.model.m3.M3AlgoMin
import ru.cityron.domain.model.m3.M3State

fun M3StateDto.fromDto() = M3State(
    alarms = alarms,
    algo = M3AlgoMin(
        tempPv = tempPv,
        tempPu = tempPu,
        tempExt = tempExt,
        tempBWater = tempBWater,
    ),
    ipLoc = ipLoc,
    set = Set(
        fan = fan,
        power = power,
        temp = temp,
    ),
    rtcTime = rtcTime,
)

fun M3State.toDto() = M3StateDto(
    alarms = alarms,
    temp = set.temp,
    tempPv = algo.tempPv,
    tempExt = algo.tempExt,
    tempBWater = algo.tempBWater,
    tempPu = algo.tempPu,
    fan = set.fan,
    power = set.power,
    ipLoc = ipLoc,
    rtcTime = rtcTime
)