package br.com.digio.androidtest.data.mappers

import br.com.digio.androidtest.data.dto.CashDto
import br.com.digio.androidtest.data.dto.DigioProductsDto
import br.com.digio.androidtest.data.source.local.entities.DigioProductsEntity
import br.com.digio.androidtest.domain.mappers.DomainMapper
import br.com.digio.androidtest.domain.mappers.EntityMapper
import br.com.digio.androidtest.domain.mappers.FromEntityMapper
import br.com.digio.androidtest.domain.model.DigioProducts

class DigioProductsMapper : EntityMapper<DigioProducts, DigioProductsEntity>,
    DomainMapper<DigioProductsDto, DigioProducts>,
    FromEntityMapper<DigioProducts, DigioProductsEntity> {
    override fun toEntity(from: DigioProducts): DigioProductsEntity {
        return DigioProductsEntity(
            cash = from.cash,
            products = from.products,
            spotlights = from.spotlights
        )
    }

    override fun toDomain(from: DigioProductsDto): DigioProducts {
        return DigioProducts(
            cash = CashMapper().toDomain(from.cash ?: CashDto()),
            products = from.products?.map { ProductMapper().toDomain(it) } ?: emptyList(),
            spotlights = from.spotlight?.map { SpotlightMapper().toDomain(it) } ?: emptyList()
        )
    }

    override fun fromEntity(from: DigioProductsEntity): DigioProducts {
        return DigioProducts(
            cash = from.cash,
            products = from.products,
            spotlights = from.spotlights
        )
    }
}

