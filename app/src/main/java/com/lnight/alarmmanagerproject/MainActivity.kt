package com.lnight.alarmmanagerproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lnight.alarmmanagerproject.ui.theme.AlarmManagerProjectTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scheduler = AndroidAlarmScheduler(this)
        var alarmItem: AlarmItem? = null
        setContent {
            AlarmManagerProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var secondsText by rememberSaveable {
                        mutableStateOf("")
                    }
                    var message by rememberSaveable {
                        mutableStateOf("")
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            value = secondsText,
                            onValueChange = { secondsText = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(text = "Trigger alarm in seconds")
                            }
                        )
                        OutlinedTextField(
                            value = message,
                            onValueChange = { message = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(text = "Message")
                            }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(onClick = {
                                alarmItem = AlarmItem(
                                    time = LocalDateTime.now()
                                        .plusSeconds(secondsText.toLongOrNull() ?: 5),
                                    message = message
                                )
                                alarmItem?.let(scheduler::schedule)
                                secondsText = ""
                                message = ""
                            }) {
                                Text(text = "Schedule")
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(onClick = {
                                alarmItem?.let(scheduler::cancel)
                            }) {
                                Text(text = "Cancel")
                            }
                        }
                    }
                }
            }
        }
    }
}