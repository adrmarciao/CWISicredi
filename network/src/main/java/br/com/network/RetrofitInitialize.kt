package br.com.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


class RetrofitInitialize {

    private var isConnected = false

    fun init(context: Context): Retrofit {
        checkNetworkStatus(context)
        val client = OkHttpClient.Builder()
            .addInterceptor(provideOfflineCacheInterceptor(context))
            .cache(provideCache(context)).build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun checkNetworkStatus(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    isConnected = true
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    isConnected = false
                }
            })
        }
    }

    private fun provideCache(context: Context) = Cache(
        File(context.cacheDir, "http-cache"),
        10 * 1024 * 1024
    )

    private fun provideOfflineCacheInterceptor(context: Context): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            var request: Request = chain.request()
            if (!isConnected(context)) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .removeHeader("Cache-Control")
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }
    }

    private fun isConnected(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return isConnected
        }
        return getConnectionStatus(context)

    }
    @Suppress("DEPRECATION")
    private fun getConnectionStatus(context: Context): Boolean {
        return try {
            val e = context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            val activeNetwork = e.activeNetworkInfo
            activeNetwork != null && activeNetwork.isConnectedOrConnecting
        } catch (e: Exception) {
            false
        }
    }

}