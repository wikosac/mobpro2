package org.d3if4097.mobpro2

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import org.d3if4097.mobpro2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 1
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var preview: Preview
    private lateinit var imageAnalyzer: ImageAnalysis
    private lateinit var detector: FaceDetector

    private var lastImage: ImageProxy? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermission()
            binding.mulaiButton.setOnClickListener { requestPermission() }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        results: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        if (requestCode == REQUEST_CODE_PERMISSIONS && allPermissionsGranted()) {
            startCamera()
            binding.mulaiButton.visibility = View.GONE
        } else {
            Toast.makeText(
                this, getString(R.string.tidak_mendapat_izin_kamera),
                Toast.LENGTH_LONG
            ).show()
            binding.mulaiButton.visibility = View.VISIBLE
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        val options = FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
        detector = FaceDetection.getClient(options)

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.imageView.surfaceProvider)

            val executor = ContextCompat.getMainExecutor(this)
            imageAnalyzer = ImageAnalysis.Builder().build()
            imageAnalyzer.setAnalyzer(executor) { analyze(it) }

            cameraLive()
        }, ContextCompat.getMainExecutor(this))
    }

    private fun cameraLive() {
        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error kamera: " + e.message)
        }
    }

    @OptIn(ExperimentalGetImage::class)
    private fun analyze(imageProxy: ImageProxy) {
        lastImage = imageProxy
        val mediaImage = imageProxy.image ?: return
        val rotation = imageProxy.imageInfo.rotationDegrees
        val image = InputImage.fromMediaImage(mediaImage, rotation)

        detector.process(image)
            .addOnSuccessListener {
                updateUI(it)
                lastImage?.close()
            }
            .addOnFailureListener { e ->
                Log.e("MainActivity", "Error deteksi wajah: " + e.message)
            }
    }

    private fun updateUI(faces: MutableList<Face>) {
        if (faces.isEmpty()) {
            binding.textView.setText(R.string.hasil_deteksi_wajah_0)
            return
        }
        binding.textView.text = getString(R.string.hasil_deteksi_wajah_1,
            faces[0].rightEyeOpenProbability!! * 100,
            faces[0].leftEyeOpenProbability!! * 100,
            faces [0].smilingProbability!! * 100
        )
    }
}