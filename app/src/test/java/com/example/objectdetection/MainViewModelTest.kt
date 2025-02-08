package com.example.objectdetection

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.objectdetection.data.Links
import com.example.objectdetection.data.LinksX
import com.example.objectdetection.data.Photo
import com.example.objectdetection.data.ProfileImage
import com.example.objectdetection.data.Urls
import com.example.objectdetection.data.User
import com.example.objectdetection.repository.UnsplashRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private val unsplashRepository: UnsplashRepository = mockk()
    private val query = "cat"

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = MainViewModel(unsplashRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testSearchPhotosSuccess() = runTest {
        val mockPhotos = listOf(
            Photo(
                blur_hash = "LDCtq6Me0_kp3mof%MofUwkp,cRP",
                color = "#598c73",
                created_at = "2018-01-02T10:20:47Z",
                current_user_collections = listOf(),
                description = "Gipsy the Cat was sitting on a bookshelf one afternoon and just stared right at me, kinda saying: “Will you take a picture already?”",
                height = 3458,
                id = "gKXKBY-C-Dk",
                liked_by_user = false,
                likes = 1701,
                links = Links(
                    download = "https://unsplash.com/photos/gKXKBY-C-Dk/download?ixid=M3w2OTU2NDZ8MHwxfHNlYXJjaHwxfHxjYXR8ZW58MHx8fHwxNzM4OTA5NDkzfDA",
                    html = "https://unsplash.com/photos/black-and-white-cat-lying-on-brown-bamboo-chair-inside-room-gKXKBY-C-Dk",
                    self = "https://api.unsplash.com/photos/black-and-white-cat-lying-on-brown-bamboo-chair-inside-room-gKXKBY-C-Dk"
                ),
                urls = Urls(
                    full = "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?crop=entropy&cs=srgb&fm=jpg&ixid=M3w2OTU2NDZ8MHwxfHNlYXJjaHwxfHxjYXR8ZW58MHx8fHwxNzM4OTA5NDkzfDA&ixlib=rb-4.0.3&q=85",
                    raw = "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?ixid=M3w2OTU2NDZ8MHwxfHNlYXJjaHwxfHxjYXR8ZW58MHx8fHwxNzM4OTA5NDkzfDA&ixlib=rb-4.0.3",
                    regular = "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2OTU2NDZ8MHwxfHNlYXJjaHwxfHxjYXR8ZW58MHx8fHwxNzM4OTA5NDkzfDA&ixlib=rb-4.0.3&q=80&w=1080",
                    small = "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2OTU2NDZ8MHwxfHNlYXJjaHwxfHxjYXR8ZW58MHx8fHwxNzM4OTA5NDkzfDA&ixlib=rb-4.0.3&q=80&w=400",
                    thumb = "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2OTU2NDZ8MHwxfHNlYXJjaHwxfHxjYXR8ZW58MHx8fHwxNzM4OTA5NDkzfDA&ixlib=rb-4.0.3&q=80&w=200"
                ),
                user = User(
                    first_name = "Manja",
                    id = "wBu1hC4QlL0",
                    instagram_username = "makawee_photography",
                    last_name = "Vitolic",
                    links = LinksX(
                        html = "https://unsplash.com/@madhatterzone",
                        likes = "https://api.unsplash.com/users/madhatterzone/likes",
                        photos = "https://api.unsplash.com/users/madhatterzone/photos",
                        self = "https://api.unsplash.com/users/madhatterzone"
                    ),
                    name = "Manja Vitolic",
                    portfolio_url = "https://www.instagram.com/makawee_photography/?hl=en",
                    profile_image = ProfileImage(
                        large = "https://images.unsplash.com/profile-fb-1514888261-0e72294039e0.jpg?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128",
                        medium = "https://images.unsplash.com/profile-fb-1514888261-0e72294039e0.jpg?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64",
                        small = "https://images.unsplash.com/profile-fb-1514888261-0e72294039e0.jpg?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32"
                    ),
                    twitter_username = null,
                    username = "madhatterzone"
                ),
                width = 5026
            )
        )

        coEvery { unsplashRepository.searchPhotos(query) } returns mockPhotos

        val observer = Observer<List<Photo>?> { result ->
            assertEquals(mockPhotos, result)
        }
        viewModel.photos.observeForever(observer)

        viewModel.searchPhotos(query)

        advanceUntilIdle()

        coVerify { unsplashRepository.searchPhotos(query) }

        viewModel.photos.removeObserver(observer)
    }

    @Test
    fun testSearchPhotosFailure() = runTest {
        val exceptionMessage = "API Error"

        coEvery { unsplashRepository.searchPhotos(query) } throws RuntimeException(exceptionMessage)

        val observer = Observer<String?> { error ->
            assertEquals(exceptionMessage, error)
        }
        viewModel.apiError.observeForever(observer)

        viewModel.searchPhotos(query)

        advanceUntilIdle()

        coVerify { unsplashRepository.searchPhotos(query) }

        viewModel.apiError.removeObserver(observer)
    }
}

