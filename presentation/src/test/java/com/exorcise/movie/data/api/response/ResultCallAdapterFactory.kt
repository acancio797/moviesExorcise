package com.exorcise.movie.data.api.response

import com.exorcise.data.api.retrofit.adapters.ResultCall
import com.exorcise.data.api.retrofit.adapters.ResultCallAdapterFactory
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactoryTest {

    private val factory = ResultCallAdapterFactory()

    @Test
    fun getShouldReturnCallAdapterForCallResult() {
        // Arrange
        val returnType = createParameterizedType(Call::class.java, createParameterizedType(Result::class.java, String::class.java))
        val annotations = emptyArray<Annotation>()
        val retrofit = Retrofit.Builder().baseUrl("https://example.com").build()

        // Act
        val adapter = factory.get(returnType, annotations, retrofit)

        // Assert
        assertNotNull("Adapter should not be null", adapter)
        assertTrue("Adapter should be a CallAdapter", adapter is CallAdapter<*, *>)
        assertEquals(String::class.java, (adapter as CallAdapter<Any, *>).responseType())
    }

    @Test
    fun getShouldReturnNullWhenReturnTypeIsNotCall() {
        // Arrange
        val returnType = String::class.java
        val annotations = emptyArray<Annotation>()
        val retrofit = Retrofit.Builder().baseUrl("https://example.com").build()

        // Act
        val adapter = factory.get(returnType, annotations, retrofit)

        // Assert
        assertNull("Adapter should be null when returnType is not Call", adapter)
    }

    @Test
    fun getShouldReturnNullWhenReturnTypeIsNotParameterized() {
        // Arrange
        val returnType = Call::class.java
        val annotations = emptyArray<Annotation>()
        val retrofit = Retrofit.Builder().baseUrl("https://example.com").build()

        // Act
        val adapter = factory.get(returnType, annotations, retrofit)

        // Assert
        assertNull("Adapter should be null when returnType is not Parameterized", adapter)
    }

    @Test
    fun getShouldReturnNullWhenUpperBoundIsNotResult() {
        // Arrange
        val returnType = createParameterizedType(Call::class.java, String::class.java)
        val annotations = emptyArray<Annotation>()
        val retrofit = Retrofit.Builder().baseUrl("https://example.com").build()

        // Act
        val adapter = factory.get(returnType, annotations, retrofit)

        // Assert
        assertNull("Adapter should be null when upper bound is not Result", adapter)
    }

    @Test
    fun getShouldReturnNullWhenResultIsNotParameterized() {
        // Arrange
        val returnType = createParameterizedType(Call::class.java, Result::class.java)
        val annotations = emptyArray<Annotation>()
        val retrofit = Retrofit.Builder().baseUrl("https://example.com").build()

        // Act
        val adapter = factory.get(returnType, annotations, retrofit)

        // Assert
        assertNull("Adapter should be null when Result is not Parameterized", adapter)
    }

    @Test
    fun adaptShouldReturnResultCall() {
        // Arrange
        val call = createMockCall()
        val returnType = createParameterizedType(Call::class.java, createParameterizedType(Result::class.java, String::class.java))
        val annotations = emptyArray<Annotation>()
        val retrofit = Retrofit.Builder().baseUrl("https://example.com").build()

        val adapter = factory.get(returnType, annotations, retrofit) as CallAdapter<Any, Call<Result<Any>>>

        // Act
        val adaptedCall = adapter.adapt(call)

        // Assert
        assertNotNull("Adapted call should not be null", adaptedCall)
        assertTrue("Adapted call should be an instance of ResultCall", adaptedCall is ResultCall<*>)
    }

    // Helper method to create parameterized types
    private fun createParameterizedType(rawType: Type, vararg typeArgs: Type): ParameterizedType {
        return object : ParameterizedType {
            override fun getRawType(): Type = rawType
            override fun getActualTypeArguments(): Array<out Type> = typeArgs
            override fun getOwnerType(): Type? = null
        }
    }

    // Helper method to mock a Call instance
    private fun createMockCall(): Call<Any> {
        return object : Call<Any> {
            override fun enqueue(callback: retrofit2.Callback<Any>) {}
            override fun isExecuted(): Boolean = false
            override fun clone(): Call<Any> = this
            override fun isCanceled(): Boolean = false
            override fun cancel() {}
            override fun execute(): Response<Any> = Response.success(null)
            override fun request(): okhttp3.Request = okhttp3.Request.Builder().url("https://example.com").build()
            override fun timeout(): okio.Timeout = okio.Timeout()
        }
    }
}
