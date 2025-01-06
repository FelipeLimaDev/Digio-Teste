package br.com.digio.androidtest.data.mappers

import br.com.digio.androidtest.data.dto.SpotlightDto
import br.com.digio.androidtest.domain.mappers.DomainMapper
import br.com.digio.androidtest.domain.model.Spotlight

class SpotlightMapper : DomainMapper<SpotlightDto, Spotlight> {
    override fun toDomain(from: SpotlightDto): Spotlight {
        return Spotlight(
            name = from.name ?: String(),
            bannerURL = from.bannerURL ?: String(),
            description = from.description ?: String()
        )
    }
}
