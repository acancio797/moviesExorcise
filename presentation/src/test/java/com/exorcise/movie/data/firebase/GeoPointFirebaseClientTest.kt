import com.exorcise.data.data.firebase.GeoPointFirebaseClient
import com.exorcise.data.utils.toMap
import com.exorcise.data.utils.toMovieGeolocation
import com.exorcise.domain.model.ImageFile
import com.exorcise.domain.model.MapPoint
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.type.DateTime
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GeoPointFirebaseClientTest {

    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var geoPointFirebaseClient: GeoPointFirebaseClient

    @Before
    fun setup() {
        db = mockk(relaxed = true)
        storage = mockk(relaxed = true)
        geoPointFirebaseClient = GeoPointFirebaseClient(db, storage)
    }

    @Test
    fun `getMoviesGeo should return a list of MapPoint`() = runTest {
        val collectionRef = mockk<CollectionReference>(relaxed = true)
        val documentSnapshot = mockk<DocumentSnapshot>(relaxed = true)

        every { db.collection("movie") } returns collectionRef
        coEvery { collectionRef.get().await().documents } returns listOf(documentSnapshot)

        val mockMapPoint = MapPoint(
            position = LatLng(40.7128, -74.0060), // Example position (New York City)
            time = DateTime.getDefaultInstance(), // Current time
            id = "123"
        )
        every { documentSnapshot.toMovieGeolocation() } returns mockMapPoint

        val result = geoPointFirebaseClient.getMoviesGeo()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(mockMapPoint, result[0])
        verify { db.collection("movie") }
    }

    @Test
    fun `insertMovie should add a movie to Firestore`() = runTest {
        val collectionRef = mockk<CollectionReference>(relaxed = true)
        val movie = MapPoint(
            position = LatLng(34.0522, -118.2437), // Los Angeles
            time = DateTime.getDefaultInstance(),
            id = "456"
        )

        every { db.collection("movie") } returns collectionRef
        coEvery { collectionRef.add(any()) } returns mockk(relaxed = true)

        geoPointFirebaseClient.insertMovie(movie)

        verify { db.collection("movie") }
        coVerify { collectionRef.add(movie.toMap()) }
    }

    @Test
    fun `uploadImageToFirebase should upload an image and return its path`() = runTest {
        val storageRef = mockk<StorageReference>(relaxed = true)
        val imageFile = ImageFile(
            fileName = "test.jpg",
            uri = mockk(relaxed = true)
        )

        every { storage.reference } returns storageRef
        every { storageRef.child("images/${imageFile.fileName}") } returns storageRef
        coEvery { storageRef.putFile(imageFile.uri).await().uploadSessionUri?.path } returns "/images/test.jpg"

        val result = geoPointFirebaseClient.uploadImageToFirebase(imageFile)

        assertEquals("/images/test.jpg", result)
        verify { storage.reference }
        verify { storageRef.child("images/${imageFile.fileName}") }
    }
}
