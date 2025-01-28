package com.exorcise.movie.data.api.response

import com.exorcise.data.api.retrofit.adapters.ResultCall
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import org.junit.Assert.*
import org.junit.Test
import io.mockk.*

class ResultCallTest {

    @Test
    fun enqueueShouldCallOnResponseWithSuccessResultWhenResponseIsSuccessful() {
        // Arrange
        val mockDelegate = mockk<Call<String>>()
        val mockCallback = mockk<Callback<Result<String>>>(relaxed = true)
        val response = Response.success("Success")

        every { mockDelegate.enqueue(any()) } answers {
            val callback = it.invocation.args[0] as Callback<String>
            callback.onResponse(mockDelegate, response)
        }

        val resultCall = ResultCall(mockDelegate)

        // Act
        resultCall.enqueue(mockCallback)

        // Assert
        verify {
            mockCallback.onResponse(
                resultCall,
                match {
                    it.isSuccessful &&
                            it.body()?.isSuccess == true &&
                            it.body()?.getOrNull() == "Success"
                }
            )
        }
    }

    @Test
    fun enqueueShouldCallOnResponseWithFailureResultWhenResponseIsNotSuccessful() {
        // Arrange
        val mockDelegate = mockk<Call<String>>()
        val mockCallback = mockk<Callback<Result<String>>>(relaxed = true)
        val responseBody = mockk<ResponseBody>()
        {
            every { contentType() } returns null
            every { contentLength() } returns 0L
        }
        val response = Response.error<String>(400, responseBody)

        every { mockDelegate.enqueue(any()) } answers {
            val callback = it.invocation.args[0] as Callback<String>
            callback.onResponse(mockDelegate, response)
        }

        val resultCall = ResultCall(mockDelegate)

        // Act
        resultCall.enqueue(mockCallback)

        // Assert
        verify {
            mockCallback.onResponse(
                resultCall,
                match {
                    it.isSuccessful &&
                            it.body()?.isFailure == true &&
                            it.body()?.exceptionOrNull() is HttpException
                }
            )
        }
    }

    @Test
    fun enqueueShouldCallOnResponseWithFailureResultWhenDelegateFails() {
        // Arrange
        val mockDelegate = mockk<Call<String>>()
        val mockCallback = mockk<Callback<Result<String>>>(relaxed = true)
        val exception = RuntimeException("Network error")

        every { mockDelegate.enqueue(any()) } answers {
            val callback = it.invocation.args[0] as Callback<String>
            callback.onFailure(mockDelegate, exception)
        }

        val resultCall = ResultCall(mockDelegate)

        // Act
        resultCall.enqueue(mockCallback)

        // Assert
        verify {
            mockCallback.onResponse(
                resultCall,
                match {
                    it.isSuccessful &&
                            it.body()?.isFailure == true &&
                            it.body()?.exceptionOrNull() == exception
                }
            )
        }
    }

    @Test
    fun executeShouldReturnSuccessResultWhenResponseIsSuccessful() {
        // Arrange
        val mockDelegate = mockk<Call<String>>()
        val response = Response.success("Success")

        every { mockDelegate.execute() } returns response

        val resultCall = ResultCall(mockDelegate)

        // Act
        val resultResponse = resultCall.execute()

        // Assert
        assertTrue(resultResponse.isSuccessful)
        assertNotNull(resultResponse.body())
        assertTrue(resultResponse.body()?.isSuccess == true)
        assertEquals("Success", resultResponse.body()?.getOrNull())
    }

    @Test
    fun executeShouldThrowExceptionWhenDelegateExecuteFails() {
        // Arrange
        val mockDelegate = mockk<Call<String>>()
        val exception = RuntimeException("Network error")

        every { mockDelegate.execute() } throws exception

        val resultCall = ResultCall(mockDelegate)

        // Act & Assert
        try {
            resultCall.execute()
            fail("Expected an exception to be thrown")
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }

    @Test
    fun isExecutedShouldReturnTheSameValueAsDelegate() {
        // Arrange
        val mockDelegate = mockk<Call<String>>()
        every { mockDelegate.isExecuted } returns true

        val resultCall = ResultCall(mockDelegate)

        // Act
        val isExecuted = resultCall.isExecuted()

        // Assert
        assertTrue(isExecuted)
    }

    @Test
    fun isCanceledShouldReturnTheSameValueAsDelegate() {
        // Arrange
        val mockDelegate = mockk<Call<String>>()
        every { mockDelegate.isCanceled } returns true

        val resultCall = ResultCall(mockDelegate)

        // Act
        val isCanceled = resultCall.isCanceled()

        // Assert
        assertTrue(isCanceled)
    }

    @Test
    fun cloneShouldReturnANewInstanceOfResultCall() {
        // Arrange
        val mockDelegate = mockk<Call<String>>()
        val mockClone = mockk<Call<String>>()
        every { mockDelegate.clone() } returns mockClone

        val resultCall = ResultCall(mockDelegate)

        // Act
        val clonedResultCall = resultCall.clone()

        // Assert
        assertNotNull(clonedResultCall)
        assertTrue(clonedResultCall is ResultCall<*>)
    }

    @Test
    fun requestShouldReturnTheSameRequestAsDelegate() {
        // Arrange
        val mockDelegate = mockk<Call<String>>()
        val mockRequest = mockk<Request>()
        every { mockDelegate.request() } returns mockRequest

        val resultCall = ResultCall(mockDelegate)

        // Act
        val request = resultCall.request()

        // Assert
        assertEquals(mockRequest, request)
    }

    @Test
    fun timeoutShouldReturnTheSameTimeoutAsDelegate() {
        // Arrange
        val mockDelegate = mockk<Call<String>>()
        val mockTimeout = mockk<Timeout>()
        every { mockDelegate.timeout() } returns mockTimeout

        val resultCall = ResultCall(mockDelegate)

        // Act
        val timeout = resultCall.timeout()

        // Assert
        assertEquals(mockTimeout, timeout)
    }
}
