package com.example.bugtrackingapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bugtrackingapp.view.ui.theme.BugTrackingAppTheme
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class ScreenshotActivity : ComponentActivity() {

    private val viewModel by viewModels<ScreenshotViewModel>()
    var fileName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fileName = intent.getStringExtra("key").toString()

        setContent {
            BugTrackingAppTheme {

                val context = LocalContext.current
                Scaffold(topBar = {
                    Row {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    val intent = Intent(context, MainActivity::class.java)
                                    startActivity(intent)
                                },
                            tint = Color.White
                        )
                    }
                }
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = fileName,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(350.dp),
                                    contentDescription = null,
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            var description by remember { mutableStateOf(TextFieldValue("")) }
                            TextField(
                                value = description,
                                onValueChange = { newText ->
                                    description = newText
                                },
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                label = { Text(text = "Description") },
                                placeholder = { Text(text = "Input description") },
                            )

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = {
                                        val file = File(fileName)

                                        val requestBody = file.asRequestBody()
                                        val multipartBody = MultipartBody.Part.createFormData(
                                            "file",
                                            file.name,
                                            requestBody
                                        )
                                        viewModel.uploadImage(multipartBody, description.text)
                                        if (viewModel.res) {
                                            val intent = Intent(context, MainActivity::class.java)
                                            startActivity(intent)
                                        }
                                    }
                                ) {
                                    Text(text = "upload to server")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    BugTrackingAppTheme {
        Greeting2("Android")
    }
}