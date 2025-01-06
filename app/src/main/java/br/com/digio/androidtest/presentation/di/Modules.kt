package br.com.digio.androidtest.presentation.di

import androidx.room.Room
import br.com.digio.androidtest.data.repository.ProductsRepositoryImpl
import br.com.digio.androidtest.data.source.local.DigioDatabase
import br.com.digio.androidtest.data.source.remote.DigioEndpoint
import br.com.digio.androidtest.data.source.remote.RemoteDataSource
import br.com.digio.androidtest.domain.repository.ProductsRepository
import br.com.digio.androidtest.domain.usecase.FetchDigioProductsUseCase
import br.com.digio.androidtest.presentation.viewmodels.ProductsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import br.com.digio.androidtest.BuildConfig
import retrofit2.converter.gson.GsonConverterFactory


private fun providerDigioApiService(): DigioEndpoint {
    val clientBuilder = OkHttpClient.Builder()

    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        clientBuilder.addInterceptor(logging)
    }

    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientBuilder.build())
        .build()
        .create(DigioEndpoint::class.java)
}


private val networkModule = module {
    singleOf(::providerDigioApiService)
    singleOf(::RemoteDataSource)
}

private val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            DigioDatabase::class.java,
            DigioDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<DigioDatabase>().digioProductsDao }
}

private val repositoryModule = module {
    singleOf(::ProductsRepositoryImpl).bind<ProductsRepository>()
}

private val viewModelModule = module {
    viewModelOf(::ProductsViewModel)
}

private val useCaseModule = module {
    factoryOf(::FetchDigioProductsUseCase)
}

val modulesDigio = listOf(
    networkModule,
    repositoryModule,
    viewModelModule,
    useCaseModule,
    databaseModule
)