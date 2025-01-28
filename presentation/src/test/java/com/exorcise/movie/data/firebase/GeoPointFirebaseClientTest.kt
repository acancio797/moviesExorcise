import com.exorcise.data.data.firebase.GeoPointFirebaseClient
import com.exorcise.data.utils.toMap
import com.exorcise.data.utils.toMovieGeolocation
import com.exorcise.domain.model.ImageFile
import com.exorcise.domain.model.MapPoint
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.type.DateTime
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import net.bytebuddy.implementation.InvokeDynamic.lambda
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
        // Arrange
        val collectionRef = mockk<CollectionReference>()
        val documentSnapshot = mockk<DocumentSnapshot>()
        val querySnapshot = mockk<QuerySnapshot>()

        every { db.collection("movie") } returns collectionRef
        val mockTask = mockk<com.google.android.gms.tasks.Task<QuerySnapshot>>()
        every { collectionRef.get() } returns mockTask

        every { mockTask.addOnSuccessListener(any()) } answers {
            firstArg<OnSuccessListener<QuerySnapshot>>().onSuccess(querySnapshot)
            mockTask
        }
        every { mockTask.addOnFailureListener(any()) } answers { mockTask }
        every { querySnapshot.documents } returns listOf(documentSnapshot)

        val mockGeoPoint = mockk<GeoPoint> {
            every { latitude } returns 40.7128
            every { longitude } returns -74.0060
        }
        val mockTimestamp = mockk<com.google.firebase.Timestamp> {
            every { toDate() } returns java.util.Date(1599962112) // Example date
        }
        every { documentSnapshot.data } returns mapOf(
            "position" to mockGeoPoint,
            "time" to mockTimestamp
        )
        every { documentSnapshot.id } returns "123"

        // Act
        val result = geoPointFirebaseClient.getMoviesGeo()

        // Assert
        assertNotNull(result)
        assertEquals(1, result.size)
        val mapPoint = result[0]
        assertEquals("123", mapPoint?.id)
        assertEquals(40.7128, mapPoint?.position?.latitude)
        assertEquals(-74.0060, mapPoint?.position?.longitude)
        assertEquals(1599962000, mapPoint?.time?.seconds?.times(1000))
    }


    @Test
    fun `insertMovie should add a movie to Firestore`() = runTest {
        // Arrange
        val collectionRef = mockk<CollectionReference>()
        val mockTask =
            mockk<com.google.android.gms.tasks.Task<com.google.firebase.firestore.DocumentReference>>()
        val movie = MapPoint(
            position = LatLng(34.0522, -118.2437), // Los Angeles
            time = DateTime.getDefaultInstance(),
            id = "456"
        )

        every { db.collection("movie") } returns collectionRef
        every { collectionRef.add(any()) } returns mockTask

        every { mockTask.addOnSuccessListener(any()) } answers {
            firstArg<OnSuccessListener<com.google.firebase.firestore.DocumentReference>>().onSuccess(
                mockk()
            )
            mockTask
        }

        every { mockTask.addOnFailureListener(any()) } answers { mockTask }

        // Act
        geoPointFirebaseClient.insertMovie(movie)

        // Assert
        verify { db.collection("movie") }
        verify { collectionRef.add(movie.toMap()) }
    }


    @Test
    fun `uploadImageToFirebase should upload an image and return its path`() = runTest {
        // Arrange
        val storageRef = mockk<StorageReference>()
        val childRef = mockk<StorageReference>()
        val uploadTask = mockk<UploadTask>()
        val taskSnapshot = mockk<UploadTask.TaskSnapshot>()
        val imageFile = ImageFile(
            fileName = "test.jpg",
            uri = mockk(relaxed = true)
        )

        every { storage.reference } returns storageRef
        every { storageRef.child("images/${imageFile.fileName}") } returns childRef
        every { childRef.putFile(imageFile.uri) } returns uploadTask

        // Mocking the success callback for UploadTask
        every { uploadTask.addOnSuccessListener(any<OnSuccessListener<UploadTask.TaskSnapshot>>()) } answers {
            firstArg<OnSuccessListener<UploadTask.TaskSnapshot>>().onSuccess(taskSnapshot)
            uploadTask
        }

        // Mocking failure callback for UploadTask
        every { uploadTask.addOnFailureListener(any<OnFailureListener>()) } answers { uploadTask }

        // Mocking the path returned from the upload
        every { taskSnapshot.uploadSessionUri?.path } returns "/images/test.jpg"

        // Act
        val result = geoPointFirebaseClient.uploadImageToFirebase(imageFile)

        // Assert
        assertNotNull(result)
        assertEquals("/images/test.jpg", result)

        verify { storage.reference }
        verify { storageRef.child("images/${imageFile.fileName}") }
        verify { childRef.putFile(imageFile.uri) }
    }

    @Test
    fun `getMoviesGeo should handle exception and return empty list`() = runTest {
        // Arrange
        val collectionRef = mockk<CollectionReference>()
        val mockTask = mockk<com.google.android.gms.tasks.Task<QuerySnapshot>>(relaxed = true)

        every { db.collection("movie") } returns collectionRef
        every { collectionRef.get() } returns mockTask

        val exception = Exception("Firestore error")
        every { mockTask.addOnSuccessListener(any()) } answers {
            // Do nothing for success to simulate the failure case
            mockTask
        }
        every { mockTask.addOnFailureListener(any()) } answers {
            firstArg<OnFailureListener>().onFailure(exception)
            mockTask
        }

        // Act
        try {
            val result = geoPointFirebaseClient.getMoviesGeo()
        } catch (e: Exception) {
            assertEquals("Firestore error", e.message)
        }


    }


    @Test
    fun `insertMovie should handle exception`() = runTest {
        // Arrange
        val collectionRef = mockk<CollectionReference>()
        every { db.collection("movie") } returns collectionRef

        val movie = MapPoint(
            position = LatLng(34.0522, -118.2437), // Los Angeles
            time = com.google.type.DateTime.getDefaultInstance(),
            id = "456"
        )
        val exception = Exception("Firestore error")

        val mockTask = mockk<com.google.android.gms.tasks.Task<DocumentReference>>(relaxed = true)
        every { collectionRef.add(any()) } returns mockTask

        every { mockTask.addOnSuccessListener(any()) } answers {
            // Just return the mocked task for chaining
            mockTask
        }
        every { mockTask.addOnFailureListener(any()) } answers {
            // Simulate the failure by invoking the failure listener with the exception
            firstArg<OnFailureListener>().onFailure(exception)
            mockTask
        }

        // Act
        val thrownException = runCatching {
            geoPointFirebaseClient.insertMovie(movie)
        }.exceptionOrNull()

        // Assert
        assertNotNull("Expected exception, but it was null", thrownException)
        assertEquals("Firestore error", thrownException?.message)
        verify { db.collection("movie") }
        verify { collectionRef.add(movie.toMap()) }
    }


}
