package com.flickr.images

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flickr.images.data.model.FlickrItem
import com.flickr.images.data.model.FlickrResponse
import com.flickr.images.data.model.Media
import com.flickr.images.repository.FlickrRepository
import com.flickr.images.ui.screens.view.SearchViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    class CoroutineTestRule(
        val dispatcher: TestDispatcher = StandardTestDispatcher()
    ) : TestWatcher() {
        override fun starting(description: Description?) {
            Dispatchers.setMain(dispatcher)
        }

        override fun finished(description: Description?) {
            Dispatchers.resetMain()
        }
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: SearchViewModel

    @Mock
    lateinit var repository: FlickrRepository

    @Mock
    lateinit var flickrItem: FlickrItem

    @Before
    fun setup() {
        repository = mock(FlickrRepository::class.java)
        flickrItem = mock(FlickrItem::class.java)

        viewModel = SearchViewModel(repository)
    }

    @Test
    fun `searchPhotos updates view state correctly`() = runBlocking {
        val mockResponse = FlickrResponse(listOf(
            FlickrItem("Title 1", "Link 1", Media("Media 1"), "2023-10-21T16:26:37-08:00", "Description 1", "2024-05-05T22:31:43Z", "Author 1", "id 1", "tag 1"),
            FlickrItem("Title 2", "Link 2", Media("Media 2"), "2023-10-21T16:26:37-08:00", "Description 2", "2019-11-25T00:45:59Z", "Author 2", "id 2", "tag 2"),
        ))

        `when`(repository.searchPhotos("forest")).thenReturn(mockResponse)

        viewModel.searchPhotos("forest")

        assert(viewModel.viewState.value?.isLoading == true)

        coroutineTestRule.dispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.viewState.value?.isLoading == false)
        assert(viewModel.viewState.value?.searchResults == mockResponse.items)
    }

    @Test
    fun `onItemClicked updates showDetail correctly`() {
        val item = FlickrItem("Title 1", "Link 1", Media("Media 1"), "2023-10-21T16:26:37-08:00", "Description 1", "2024-05-05T22:31:43Z", "Author 1", "id 1", "tag 1")

        viewModel.onItemClicked(item)

        assertEquals(viewModel.viewState.value?.showDetail, item)
    }
}