package br.com.digio.androidtest.data.mappers

import br.com.digio.androidtest.data.dto.CashDto
import br.com.digio.androidtest.domain.mappers.DomainMapper
import br.com.digio.androidtest.domain.model.Cash

class CashMapper : DomainMapper<CashDto, Cash> {
    override fun toDomain(from: CashDto): Cash {
        return Cash(
            title = from.title?: String(),
            bannerURL = from.bannerURL?: String()
        )
    }
}