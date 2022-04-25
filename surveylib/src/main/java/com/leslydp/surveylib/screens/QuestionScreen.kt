package com.leslydp.surveylib.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leslydp.surveylib.components.QuestionTitle
import com.leslydp.surveylib.model.Answer
import com.leslydp.surveylib.model.SQuestion

@OptIn(
    ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class,
    androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun JetSurveyScreen(
    question: SQuestion,
    questionState: MutableState<Boolean>,
    onAnswer: (String) -> Unit
) {
    var sendInfo = remember{mutableStateOf(false)}
    val ans =
        mutableListOf<String>()  //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
    //list.forEach { question ->
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
    ) {

        item {
            Spacer(modifier = Modifier.height(32.dp))
            QuestionTitle(question.questionName)
            Spacer(modifier = Modifier.height(24.dp))

            Log.d("myTag2", question.questionName)
            //QuestionTitle(question.questionName)
            //Log.d("tag", question.options.size.toString())
            when (question) {
                is SQuestion.MultipleChoiceQuestion -> {
                    Log.d("tag",question.options[0])
                    val cantMaxOptions=question.options.size
                    val respuesta = mutableListOf<Byte>()
                    (1..cantMaxOptions).forEach {
                        respuesta.add(0)
                    }
                    if(question.options[0].contains("[!]")) {
                        val blurhash =
                            mutableListOf<String>() //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
                        val url =
                            mutableListOf<String>() //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
                        val options =
                            mutableListOf<String>() //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
                        for (option in question.options) {
                            val optionSplit = option.split("[!]")
                            blurhash.add(optionSplit[1])
                            url.add(optionSplit[0])
                            options.add(optionSplit[2])
                            Log.d("tag",option)
                            Log.d("tag",url.toString())
                        }
                        MultipleChoiceIconQuestionCMP(options,questionState, url, blurhash, { idq, valor ->
                            val compareTo = valor.compareTo(false)
                            respuesta[idq] = compareTo.toByte()
                            Log.d("icon", respuesta.toString())
                            ans.add(respuesta.toString())
                            //onAnswer(ans.toString())
                        }
                        )
                    }

                    else{
                        MultipleChoiceQuestionCMP(question.options, questionState,onAnswerSelected = { idq, valor ->
                            val compareTo = valor.compareTo(false)
                            respuesta[idq] = compareTo.toByte()
                            Log.d("noicon", respuesta.toString())
                            ans.add(respuesta.toString())
                            //onAnswer(ans.toString())

                        })

                    }

                    Log.d("respuesta2",ans.toString())

                }
                is SQuestion.SingleChoiceQuestion -> {
                    val cantMaxOptions=question.options.size
                    val respuesta = mutableListOf<Byte>()
                    (1..cantMaxOptions).forEach {
                        respuesta.add(0)
                    }
                    if(question.options[0].contains("[!]")) {
                        val blurhash =
                            mutableListOf<String>() //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
                        val url =
                            mutableListOf<String>() //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
                        val options =
                            mutableListOf<String>() //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
                        for (option in question.options) {
                            val optionSplit = option.split("[!]")
                            blurhash.add(optionSplit[1])
                            url.add(optionSplit[0])
                            options.add(optionSplit[2])
                            Log.d("tag", option)
                            Log.d("tag", url.toString())
                        }
                        SingleChoiceIconQuestionCMP(
                            options = options,
                            questionState = questionState,
                            url = url,
                            blurhash = blurhash,
                            onAnswerSelected = {id ->
                                if (respuesta.indexOf(1) > -1)
                                    respuesta[respuesta.indexOf(1)] = 0
                                respuesta[id] = 1
                                Log.d("myTag", respuesta.toString())
                                ans.add(respuesta.toString())
                                //onAnswer(ans.toString())
                            }
                        )
                    }
                    else{
                        SingleChoiceQuestionCMP(question.options,questionState, onAnswerSelected = { id ->
                            if (respuesta.indexOf(1) > -1)
                                respuesta[respuesta.indexOf(1)] = 0
                            respuesta[id] = 1
                            Log.d("myTag", respuesta.toString())
                            ans.add(respuesta.toString())
                            Log.d("resta",ans.toString())
                           // onAnswer(ans.toString())
                        })
                    }


                }
                is SQuestion.SliderQuestion -> {
                    var respuesta = 0f
                    SliderQuestionCMP(question.options,questionState, onAnswerSelected = {
                        Log.d("slider", it.toString())
                        respuesta = it
                        ans.add("[!]${respuesta}")
                        //onAnswer(ans.toString())
                    })

                }

                is SQuestion.TextQuestion->{
                    var respuesta=""
                    TextQuestionCMP(onAnswerWritten = {
                        Log.d("text", it)
                        respuesta = it

                        //onAnswer(ans.toString())
                    },modifier = Modifier, questionState)
                    //onAnswer(respuesta)
                    ans.add(respuesta)
                }


            }
        }
    }
    // }
    LaunchedEffect(sendInfo.value) {
        if(sendInfo.value){
            onAnswer((ans.toString()))
            Log.d("Respuesta", ans.toString())
        }else{
            Log.d("Respuesta", "NOT SENDING INFO")
        }

    }


}