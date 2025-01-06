package br.com.digio.androidtest.utils

import br.com.digio.androidtest.data.dto.CashDto
import br.com.digio.androidtest.data.dto.DigioProductsDto
import br.com.digio.androidtest.data.dto.ProductDto
import br.com.digio.androidtest.data.dto.SpotlightDto
import br.com.digio.androidtest.data.source.local.entities.DigioProductsEntity
import br.com.digio.androidtest.domain.model.Cash
import br.com.digio.androidtest.domain.model.DigioProducts
import br.com.digio.androidtest.domain.model.Product
import br.com.digio.androidtest.domain.model.Spotlight
import br.com.digio.androidtest.domain.utils.DataError

object TesteUtils {

    val mockCash = Cash(
        bannerURL = "https://s3-sa-east-1.amazonaws.com/digio-exame/cash_banner.png",
        title = "digio Cash"
    )

    val mockProduct = Product(
        imageURL = "https://s3-sa-east-1.amazonaws.com/digio-exame/xbox_icon.png",
        name = "XBOX",
        description = "Com o e-Gift Card Xbox você adquire créditos para comprar games, música, filmes, programas de TV e muito mais!"
    )

    val mockSpotlight = Spotlight(
        bannerURL = "https://s3-sa-east-1.amazonaws.com/digio-exame/recharge_banner.png",
        name = "Recarga",
        description = "Agora ficou mais fácil colocar créditos no seu celular! A digio Store traz a facilidade de fazer recargas... direto pelo seu aplicativo, com toda segurança e praticidade que você procura."
    )

    val mockDigioProducts = DigioProducts(
        cash = mockCash,
        products = listOf(mockProduct),
        spotlights = listOf(mockSpotlight)
    )

    val mockDigioProductsEntity = DigioProductsEntity(
        id = 1,
        cash = mockCash,
        products = listOf(mockProduct),
        spotlights = listOf(mockSpotlight)
    )

    val mockCashDto = CashDto(
        bannerURL = "https://s3-sa-east-1.amazonaws.com/digio-exame/cash_banner.png",
        title = "digio Cash"
    )

    val mockProductDto = ProductDto(
        imageURL = "https://s3-sa-east-1.amazonaws.com/digio-exame/xbox_icon.png",
        name = "XBOX",
        description = "Com o e-Gift Card Xbox você adquire créditos para comprar games, música, filmes, programas de TV e muito mais!"
    )

    val mockSpotlightDto = SpotlightDto(
        bannerURL = "https://s3-sa-east-1.amazonaws.com/digio-exame/recharge_banner.png",
        name = "Recarga",
        description = "Agora ficou mais fácil colocar créditos no seu celular! A digio Store traz a facilidade de fazer recargas... direto pelo seu aplicativo, com toda segurança e praticidade que você procura."
    )

    val mockDigioProductsDto = DigioProductsDto(
        cash = mockCashDto,
        products = listOf(mockProductDto),
        spotlight = listOf(mockSpotlightDto)
    )

    val mockDataErrorRemoteNoInternet = DataError.Remote.NO_INTERNET
    val mockDataErrorLocalUnknown = DataError.Local.UNKNOWN
}