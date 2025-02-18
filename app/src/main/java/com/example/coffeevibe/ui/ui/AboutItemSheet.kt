package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutItemSheet(state: Boolean = false, onClose: (Boolean) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(state) }
    var password by remember { mutableStateOf("") }

    CoffeeVibeTheme(content = {
        val colorScheme = MaterialTheme.colorScheme

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                    onClose(showBottomSheet)
                },
                sheetState = sheetState,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorScheme.onBackground,
                                unfocusedBorderColor = colorScheme.onBackground,
                            ),
                            readOnly = true,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp),
                        )

                        IconButton(
                            onClick = {
                                //TODO
                            },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CopyAll,
                                contentDescription = "Copy",
                                tint = colorScheme.onBackground,
                                modifier = Modifier.size(30.dp, 30.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Button(
                        onClick = {
                           //TODO
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            "Generate password",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = White,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
                            )
                        )
                    }
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun AboutItemSheetPreview() {
    AboutItemSheet(true, {})
}