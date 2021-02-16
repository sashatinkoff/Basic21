package com.isidroid.b21.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request();
        val originalHttpUrl = original.url;


        val request = original.newBuilder()
            .header("Cookie", "")
            .header("X-CSRFToken", "f68TFcXLf8XrDtNJZBuw7ukwrvdtrsDW")
            .header("Host", "www.instagram.com")
            .header("Referer", "https://www.instagram.com/")
            .header("X-Instagram-AJAX", "1")
            .header(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:50.0) Gecko/20100101 Firefox/50.0"
            )
            .header("Accept-Encoding", "gzip, deflate, br")
            .header("X-Requested-With", "XMLHttpRequest")
            .method(original.method, original.body)
            .build();

        return chain.proceed(request);
    }
}