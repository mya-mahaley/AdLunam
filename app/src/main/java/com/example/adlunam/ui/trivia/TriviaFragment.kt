package com.example.adlunam.ui.trivia

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.adlunam.databinding.FragmentTriviaBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adlunam.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.roundToInt
import kotlin.random.Random

class TriviaFragment : Fragment() {
    private val correctColor = Color.parseColor("#FF006A36")
    private val incorrectColor = Color.parseColor("#FF6A0000")
    private val cardColor = Color.parseColor("#2E0E9D")
    private var curCorrect = 0.0
    private var curTotal = 0.0


    private var _binding: FragmentTriviaBinding? = null
    private val questionList : MutableList<TriviaQuestion> = ArrayList()
    //private val seed = 42 // 100L
    private var random = Random(System.currentTimeMillis())
    private var answerClicked = false
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTriviaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loadQuestions()
        pickQuestion()

        binding.newQuestion.setOnClickListener {
            pickQuestion()
        }

        return root
    }

    private fun loadQuestions() {
        answerClicked = false

        var fileScanner = Scanner(TriviaQuestions)
        fileScanner.nextLine()
        while(fileScanner.hasNextLine()){
            var lineScanner = Scanner(fileScanner.nextLine())
            lineScanner.useDelimiter("!")

            val question = lineScanner.next()
            val explanation = lineScanner.next()

            val answer0 = lineScanner.next()
            val answer1 = lineScanner.next()
            val answer2 = lineScanner.next()


            val answers = mutableListOf<Pair<String, Boolean>>()
            answers.add(Pair(answer0, true))
            answers.add(Pair(answer1, false))
            answers.add(Pair(answer2, false))

            questionList.add(TriviaQuestion(question, explanation, answers))
        }
    }

    private fun pickQuestion() {
        val questionObject = questionList[random.nextInt(0, questionList.size)]
        val question = questionObject.question
        val explanation = questionObject.explanation
        val answers = questionObject.answers
        answers.shuffle()

        binding.question.text = question
        binding.questionCard.setCardBackgroundColor(cardColor)
        binding.answer0.setStrokeColorResource(R.color.white)
        binding.answer1.setStrokeColorResource(R.color.white)
        binding.answer2.setStrokeColorResource(R.color.white)


        binding.answer0.text = answers[0].first
        binding.answer1.text = answers[1].first
        binding.answer2.text = answers[2].first

        var correct = 0
        if (answers[1].second) {
            correct = 1
        }
        if (answers[2].second) {
            correct = 2
        }

        enableAnswers(correct, explanation)


    }

    private fun enableAnswers(correct: Int, explanation: String) {
        binding.answer0.setOnClickListener {
            curTotal++
            binding.question.text = explanation
            when (correct) {
                0 -> {
                    curCorrect++
                    binding.questionCard.setCardBackgroundColor(correctColor)
                }
                else -> binding.questionCard.setCardBackgroundColor(incorrectColor)
            }
            val curScore = (curCorrect/curTotal * 100).roundToInt()
            binding.curScore.text = "$curScore%"
            showAnswer(correct)
            disableAnswers()
        }

        binding.answer1.setOnClickListener {
            curTotal++
            binding.question.text = explanation
            when (correct) {
                1 -> {
                    curCorrect++
                    binding.questionCard.setCardBackgroundColor(correctColor)
                }
                else -> binding.questionCard.setCardBackgroundColor(incorrectColor)
            }
            val curScore = (curCorrect/curTotal * 100).roundToInt()
            binding.curScore.text = "$curScore%"
            showAnswer(correct)
            disableAnswers()
        }

        binding.answer2.setOnClickListener {
            curTotal++
            binding.question.text = explanation
            when (correct) {
                2 -> {
                    curCorrect++
                    binding.questionCard.setCardBackgroundColor(correctColor)
                }
                else -> binding.questionCard.setCardBackgroundColor(incorrectColor)
            }
            val curScore = (curCorrect/curTotal * 100).roundToInt()
            binding.curScore.text = "$curScore%"
            showAnswer(correct)
            disableAnswers()
        }
    }

    private fun showAnswer(correct: Int) {
        when (correct) {
            0 -> {
                binding.answer0.setStrokeColorResource(R.color.green)
                binding.answer1.setStrokeColorResource(R.color.red)
                binding.answer2.setStrokeColorResource(R.color.red)
            }
            1 -> {
                binding.answer1.setStrokeColorResource(R.color.green)
                binding.answer0.setStrokeColorResource(R.color.red)
                binding.answer2.setStrokeColorResource(R.color.red)
            }
            else -> {
                binding.answer2.setStrokeColorResource(R.color.green)
                binding.answer1.setStrokeColorResource(R.color.red)
                binding.answer0.setStrokeColorResource(R.color.red)
            }
        }
    }


    private fun disableAnswers() {
        binding.answer0.setOnClickListener {}
        binding.answer1.setOnClickListener {}
        binding.answer2.setOnClickListener {}
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}