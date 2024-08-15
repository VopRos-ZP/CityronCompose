package ru.cityron.domain.usecase.def

import ru.cityron.domain.model.Filter
import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.usecase.all.UpsertM3AllUseCase
import ru.cityron.domain.usecase.events.UpsertFilterUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertDefaultUseCase @Inject constructor(
    private val upsertM3AllUseCase: UpsertM3AllUseCase,
    private val upsertFilterUseCase: UpsertFilterUseCase,
) {

    suspend operator fun invoke() {
        upsertM3AllUseCase(M3All())
        upsertFilterUseCase(Filter())
    }

}