package com.codescreen.image_gallery.ui

import com.codescreen.image_gallery.domain.model.PhotoInfo
import com.codescreen.image_gallery.domain.model.repository.Repository
import com.codescreen.image_gallery.services.repository.Resource
import io.mockk.*
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class ImageGalleryViewModelTest {

    @Mock
    val repository: Repository = mockk()

    private lateinit var viewModel: ImageGalleryViewModel

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = ImageGalleryViewModel(repository)
    }

    @Test
    fun `initial uiState should be GalleryList`() {
        assertEquals(
            ImageGalleryViewModel.ImageGalleryScreenState.GalleryList,
            viewModel.uiState.value
        )
    }

    @Test
    fun `initial photos should be empty`() {
        assertTrue(viewModel.photos.value.isEmpty())
    }

    @Test
    fun `initial photoSelected should be null`() {
        assertNull(viewModel.photoSelected.value)
    }

    @Test
    fun `initial isLoading should be true`() {
        assertTrue(viewModel.isLoading.value)
    }

    @Test
    fun `test addPage increments page index and calls getPhotoList`() = runBlocking {
        coEvery { repository.getPhotoList(1, any()) } returns flowOf(
            Resource.Loading(),
            Resource.Success(listOf())
        )

        viewModel.addPage()

        coVerify(exactly = 1) { repository.getPhotoList(1, any()) }

        assertEquals(
            ImageGalleryViewModel.ImageGalleryScreenState.GalleryList,
            viewModel.uiState.value
        )
    }

    @Test
    fun `test selectItem sets photoSelected and uiState`() {
        val photo = mockk<PhotoInfo>()

        viewModel.selectItem(photo)

        assertEquals(photo, viewModel.photoSelected.value)
        assertEquals(
            ImageGalleryViewModel.ImageGalleryScreenState.ItemDetail,
            viewModel.uiState.value
        )
    }

    @Test
    fun `test goBack resets photoSelected and uiState`() {
        viewModel.selectItem(mockk())

        viewModel.goBack()

        assertNull(viewModel.photoSelected.value)
        assertEquals(
            ImageGalleryViewModel.ImageGalleryScreenState.GalleryList,
            viewModel.uiState.value
        )
    }

    @Test
    fun `test getListPhotoInfo sets isLoading to true while loading`() = runBlocking {
        coEvery { repository.getPhotoList(1, any()) } returns flowOf(Resource.Loading())

        viewModel.addPage()

        assertTrue(viewModel.isLoading.value)
    }
}