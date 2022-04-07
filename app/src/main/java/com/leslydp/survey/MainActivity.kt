package com.leslydp.survey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.leslydp.survey.ui.theme.SurveyTheme
import com.leslydp.surveylib.JetsurveyScreen
import com.leslydp.surveylib.SurveyQuestionsScreen
import com.leslydp.surveylib.model.SQuestion
import com.leslydp.surveylib.model.SurveyState


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SurveyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var ans: List<String>
                    var options = listOf<String>("https://blurha.sh/assets/images/img1.jpg[!]LEHV6nWB2yk8pyo0adR*.7kCMdnj[!]option1","https://blurha.sh/assets/images/img1.jpg[!]LEHV6nWB2yk8pyo0adR*.7kCMdnj[!]option2","https://blurha.sh/assets/images/img1.jpg[!]LEHV6nWB2yk8pyo0adR*.7kCMdnj[!]option3")
                    val preguntas= listOf<SQuestion>(
                        SQuestion.SingleChoiceQuestion("hola", options )
                    )
                    val state: SurveyState.Questions by remenber{mutableStateOf()}
                    SurveyQuestionsScreen(preguntas,{},state) { answer ->
                        ans = answer.ans
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SurveyTheme {
        Greeting("Android")
    }
}