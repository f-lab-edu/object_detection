package com.example.objectdetection

import com.example.objectdetection.repository.UnsplashRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class UnsplashRepositoryTest {
    private lateinit var apiService: UnsplashApiService
    private lateinit var repository: UnsplashRepository
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val json = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        apiService = retrofit.create(UnsplashApiService::class.java)
        repository = UnsplashRepository(apiService)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun responseTest() = runTest {
        println("Given : MockWebServer 에 mock 응답 설정")
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(
                """{
        "total": 133,
        "total_pages": 7,
        "results": [
        {
            "id": "eOLpJytrbsQ",
            "created_at": "2014-11-18T14:35:36-05:00",
            "width": 4000,
            "height": 3000,
            "color": "#A7A2A1",
            "blur_hash": "LaLXMa9Fx[D%~q%MtQM|kDRjtRIU",
            "likes": 286,
            "liked_by_user": false,
            "description": "A man drinking a coffee.",
            "user": {
            "id": "Ul0QVz12Goo",
            "username": "ugmonk",
            "name": "Jeff Sheldon",
            "first_name": "Jeff",
            "last_name": "Sheldon",
            "instagram_username": "instantgrammer",
            "twitter_username": "ugmonk",
            "portfolio_url": "http://ugmonk.com/",
            "profile_image": {
            "small": "https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=7cfe3b93750cb0c93e2f7caec08b5a41",
            "medium": "https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=5a9dc749c43ce5bd60870b129a40902f",
            "large": "https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=32085a077889586df88bfbe406692202"
        },
            "links": {
            "self": "https://api.unsplash.com/users/ugmonk",
            "html": "http://unsplash.com/@ugmonk",
            "photos": "https://api.unsplash.com/users/ugmonk/photos",
            "likes": "https://api.unsplash.com/users/ugmonk/likes"
        }
        },
            "current_user_collections": [],
            "urls": {
            "raw": "https://images.unsplash.com/photo-1416339306562-f3d12fefd36f",
            "full": "https://hd.unsplash.com/photo-1416339306562-f3d12fefd36f",
            "regular": "https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=92f3e02f63678acc8416d044e189f515",
            "small": "https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=263af33585f9d32af39d165b000845eb",
            "thumb": "https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=8aae34cf35df31a592f0bef16e6342ef"
        },
            "links": {
            "self": "https://api.unsplash.com/photos/eOLpJytrbsQ",
            "html": "http://unsplash.com/photos/eOLpJytrbsQ",
            "download": "http://unsplash.com/photos/eOLpJytrbsQ/download"
        }
        }
        ]
        }""")

        mockWebServer.enqueue(mockResponse)

        println("When : 검색 요청을 통해 결과 가져오기")
        val result = repository.searchPhotos("cat")

        println("Then : 결과가 정상적으로 반환되었는지 검증")
        assert(result.isNotEmpty())
        assertEquals(1, result.size)
        assertEquals("eOLpJytrbsQ", result[0].id)
    }
}