package com.example.bugtrackingapp.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bugtrackingapp.ui.theme.BugTrackingAppTheme
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BugTrackingAppTheme {
                val context = LocalContext.current
                val activity = LocalContext.current as Activity

                Scaffold(topBar = {
                    Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Bug Tracking", Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color.Blue)
                                .align(Alignment.CenterVertically)
                            ,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                ) {
                    Column(
                        Modifier
                            .padding(it)
                    ) {
                        Column(Modifier.fillMaxSize()) {

                            Box(
                                modifier = Modifier
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {

                                Button(
                                    onClick = {
                                        val bitmap =
                                            getScreenShotFromView(activity.window.decorView.rootView)
                                        if (bitmap != null) {
                                            val filePath = storeAndGetImage(bitmap, context)
                                            Log.d(
                                                TAG,
                                                "filePath$filePath"
                                            ) // e.getMessage());

                                            val intent =
                                                Intent(context, ScreenshotActivity::class.java)
                                            intent.putExtra("key", filePath)
                                            context.startActivity(intent)
                                        }
                                    }) {
                                    Text(
                                        text = "Take a Screenshot", Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp), textAlign = TextAlign.Center
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {

                                Button(
                                    onClick = {
                                        context.startActivity(
                                            Intent(
                                                context,
                                                PickImageActivity::class.java
                                            )
                                        )
                                    }) {
                                    Text(
                                        text = "Select Image", Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp), textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BugTrackingAppTheme {
    }
}

private fun getScreenShotFromView(v: View): Bitmap? {
    // create a bitmap object
    var screenshot: Bitmap? = null
    try {
        screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(screenshot)
        v.draw(canvas)
    } catch (e: Exception) {
        Log.e("GFG", "Failed to capture screenshot because:" + e.message)
    }
    Log.e("GFG", "Failed to capture screenshot because:" + screenshot)
    return screenshot
}

private fun storeAndGetImage(image: Bitmap, mContext: Context): String? {
    val pictureFile = getOutputMediaFile()
    if (pictureFile == null) {
        Log.d(
            TAG,
            "Error creating media file, check storage permissions: "
        )
        return null
    }
    try {
        val fos = FileOutputStream(pictureFile)
        image.compress(Bitmap.CompressFormat.PNG, 90, fos)
        fos.close()
        return pictureFile.absolutePath
    } catch (e: FileNotFoundException) {
        Log.d(TAG, "File not found: " + e.message)
    } catch (e: IOException) {
        Log.d(TAG, "Error accessing file: " + e.message)
    }
    return null
}

/** Create a File for saving an image or video  */
private fun getOutputMediaFile(): File? {

    val mediaStorageDir =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    // Create the storage directory if it does not exist
    if (!mediaStorageDir.exists()) {
        if (!mediaStorageDir.mkdirs()) {
            return null
        }
    }
    // Create a media file name
    val timeStamp: String = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
    val mediaFile: File
    val mImageName = "MI_$timeStamp.jpg"
    mediaFile = File(mediaStorageDir.path + File.separator + mImageName)
    return mediaFile
}

