package ie.redstar.igdb.data.remote.interceptor

import ie.redstar.igdb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder().apply {
            header("Authorization", String.format("Bearer %s", BuildConfig.BEARER_TOKEN))
            header("Client-ID", BuildConfig.CLIENT_ID)
            header("Accept", "application/json")
        }

        return chain.proceed(builder.build())
    }
}
