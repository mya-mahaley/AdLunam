package com.example.adlunam.ui.trivia

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.adlunam.databinding.FragmentTriviaBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.adlunam.MainViewModel
import com.example.adlunam.R
import com.example.adlunam.ui.profile.ProfileViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.roundToInt
import kotlin.random.Random

class TriviaFragment : Fragment() {
    private val correctColor = Color.parseColor("#FF006A36")
    private val incorrectColor = Color.parseColor("#FF6A0000")
    private val cardColor = Color.parseColor("#2E0E9D")
    private var explanationVisible = false
    private val mainViewModel: MainViewModel by activityViewModels()

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

        binding.resetScore.setOnClickListener {
            mainViewModel.resetScore()
        }

        mainViewModel.observeTriviaScore().observe(viewLifecycleOwner){
            val correct = it.first
            val total = it.second
            if (correct == 0.0 && total == 0.0){
                binding.curScore.text = "0%"
            } else {
                val curScore = (correct / total * 100).roundToInt()
                binding.curScore.text = "$curScore%"
            }
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
        explanationVisible = false
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

        enableAnswers(correct, explanation, question)


    }

    private fun enableAnswers(correct: Int, explanation: String, question: String) {
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake_animation)
        val spin = AnimationUtils.loadAnimation(context, R.anim.rotate_animation)
        binding.answer0.setOnClickListener {
            binding.question.text = explanation
            when (correct) {
                0 -> {
                    mainViewModel.correctAnswer()
                    binding.questionCard.setCardBackgroundColor(correctColor)
                    binding.questionCard.startAnimation(spin)
                }
                else -> {
                    mainViewModel.incorrectAnswer()
                    binding.questionCard.setCardBackgroundColor(incorrectColor)
                    binding.questionCard.startAnimation(shake);
                }
            }
            showAnswer(correct, explanation, question)
            disableAnswers()
        }

        binding.answer1.setOnClickListener {
            binding.question.text = explanation
            when (correct) {
                1 -> {
                    mainViewModel.correctAnswer()
                    binding.questionCard.setCardBackgroundColor(correctColor)
                    binding.questionCard.startAnimation(spin)
                }
                else -> {
                    mainViewModel.incorrectAnswer()
                    binding.questionCard.setCardBackgroundColor(incorrectColor)
                    binding.questionCard.startAnimation(shake);
                }
            }

            showAnswer(correct, explanation, question)
            disableAnswers()
        }

        binding.answer2.setOnClickListener {
            binding.question.text = explanation
            when (correct) {
                2 -> {
                    mainViewModel.correctAnswer()
                    binding.questionCard.setCardBackgroundColor(correctColor)
                    binding.questionCard.startAnimation(spin)
                }
                else -> {
                    mainViewModel.incorrectAnswer()
                    binding.questionCard.setCardBackgroundColor(incorrectColor)
                    binding.questionCard.startAnimation(shake);
                }
            }
            showAnswer(correct,explanation, question)
            disableAnswers()
        }
    }

    private fun showAnswer(correct: Int, explanation: String, question: String) {
        explanationVisible = true
        binding.questionCard.setOnClickListener{
            if(explanationVisible){
                explanationVisible = false
                binding.question.text = question
            } else {
                explanationVisible = true
                binding.question.text = explanation
            }
        }
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