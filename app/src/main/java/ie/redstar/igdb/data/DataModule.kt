package ie.redstar.igdb.data

import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ie.redstar.igdb.BuildConfig
import ie.redstar.igdb.data.local.IGDBDatabase
import ie.redstar.igdb.data.remote.IgdbApi
import ie.redstar.igdb.data.remote.interceptor.AuthInterceptor
import ie.redstar.igdb.data.repository.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<Gson> {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }
    single {
        val builder = OkHttpClient.Builder().addInterceptor(AuthInterceptor())
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)
        }

        builder.build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
    single<IgdbApi> {
        (get() as Retrofit).create(IgdbApi::class.java)
    }
    single {
        Room.databaseBuilder(
            get(),
            IGDBDatabase::class.java,
            "igdb_db"
        ).build()
    }
    single{ (get() as IGDBDatabase).gameListDao() }
    single{ (get() as IGDBDatabase).gameDetailDao() }
    single<GameListRepository> { GameListDataSource(get(), get()) }
    single<GameDetailRepository> { GameDetailDataSource(get(), get()) }
}