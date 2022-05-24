package com.football.core.network.api

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException

class ApiInterceptor(private val newsResponseString: String): Interceptor {
    companion object {
        private const val returnMockedSuccessResponse = true
    }

    override fun intercept(chain: Interceptor.Chain): Response =
        if (returnMockedSuccessResponse) {
            chain.request().mockResponseWithFile()
        } else {
            mockResponseWithError()
        }

    private fun Request.mockResponseWithFile(): Response {
        Thread.sleep(2000)
        return Response.Builder()
            .request(this)
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(newsResponseString)
            .body(
                newsResponseString.toByteArray()
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader(
                "content-type", "application/json"
            )
            .build()
    }

    private fun mockResponseWithError(): Response {
        Thread.sleep(2000)
        throw IOException("Mock failed request response!")
    }
}