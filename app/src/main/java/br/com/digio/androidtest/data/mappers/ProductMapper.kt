package br.com.digio.androidtest.data.mappers

import br.com.digio.androidtest.data.dto.ProductDto
import br.com.digio.androidtest.domain.mappers.DomainMapper
import br.com.digio.androidtest.domain.model.Product

class ProductMapper : DomainMapper<ProductDto, Product> {
    override fun toDomain(from: ProductDto): Product {
        return Product(
            name = from.name,
            imageURL = from.imageURL,
            description = from.description
        )
    }
}
