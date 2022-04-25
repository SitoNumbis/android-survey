package com.leslydp.surveylib.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun SurveyBottomBar(
    questionSize: Int,
    questionIndex: Int,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onDonePressed: () -> Unit,
    questionState: MutableState<Boolean>
) {
    Surface(
        elevation = 7.dp,
        modifier = Modifier.fillMaxWidth() // .border(1.dp, MaterialTheme.colors.primary)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            if (questionIndex != 0) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onPreviousPressed
                ) {
                    Text(text = "PREVIOUS")
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            if (questionIndex == questionSize-1) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onDonePressed,
                    enabled = questionState.value
                ) {
                    Text(text = "DONE")
                    Log.d("estadoBotton",questionState.value.toString())
                }
            } else {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onNextPressed,
                    enabled = questionState.value
                ) {
                    Text(text = "NEXT")
                }
            }
        }
    }
}