package com.mantequilla.jetpackcomposetemplate.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.mantequilla.jetpackcomposetemplate.navigation.AppNavGraph
import com.mantequilla.jetpackcomposetemplate.ui.theme.JetpackComposeTemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            runApp()
        } else {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNotificationPermission()
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    runApp()
                }
                else -> {
                    setContent {
                        NotificationPermissionDialog(
                            onGranted = { requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) },
                            onDenied = { finish() }
                        )
                    }
                }
            }
        } else {
            runApp()
        }
    }

    private fun runApp() {
        setContent {
            JetpackComposeTemplateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavGraph(navController = navController)
                }
            }
        }
    }
}

@Composable
fun NotificationPermissionDialog(
    onGranted: () -> Unit,
    onDenied: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDenied() },
        title = { Text("Notification Permission") },
        text = { Text("This app needs notification permission to function properly. Grant permission?") },
        confirmButton = {
            Button(onClick = { onGranted() }) {
                Text("Grant")
            }
        },
        dismissButton = {
            Button(onClick = { onDenied() }) {
                Text("Deny")
            }
        }
    )
}