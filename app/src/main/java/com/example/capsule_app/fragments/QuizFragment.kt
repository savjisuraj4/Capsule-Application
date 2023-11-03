package com.example.capsule_app.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.capsule_app.R
import com.example.capsule_app.model.Question
import com.google.android.material.card.MaterialCardView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var currentQuestion: Int = 1

    private var questionsList = ArrayList<Question>()
    private var selectedOptionPosition: Int = 0
    private var score: Int = 0

    private lateinit var questionNumber: TextView
    private lateinit var question: TextView
    private lateinit var option1Text: TextView
    private lateinit var option2Text: TextView
    private lateinit var option3Text: TextView
    private lateinit var option4Text: TextView
    private lateinit var option1CardView: MaterialCardView
    private lateinit var option2CardView: MaterialCardView
    private lateinit var option3CardView: MaterialCardView
    private lateinit var option4CardView: MaterialCardView
    private lateinit var checkAnswerCardView: MaterialCardView
    private lateinit var nextQuestionCardView: MaterialCardView
    private lateinit var checkScoreCardView: MaterialCardView
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
        addQuestions()
        hooks(view)
        setQuestion()
        sharedPreferences=requireActivity().getSharedPreferences("Score",Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()
        editor.clear().apply()
        option1CardView.setOnClickListener {
            selectedOptionPosition = 1
            selectedOptionStrokeColor(option1CardView)
        }
        option2CardView.setOnClickListener {
            selectedOptionPosition = 2
            selectedOptionStrokeColor(option2CardView)
        }
        option3CardView.setOnClickListener {
            selectedOptionPosition = 3
            selectedOptionStrokeColor(option3CardView)
        }
        option4CardView.setOnClickListener {
            selectedOptionPosition = 4
            selectedOptionStrokeColor(option4CardView)
        }
        nextQuestionCardView.setOnClickListener {
            removeColour()
            currentQuestion++
            setQuestion()
        }
        checkAnswerCardView.setOnClickListener {
            checkAnswer()
        }
        checkScoreCardView.setOnClickListener {
            val quizResult = QuizResult()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()

            editor.putInt("scoreValue", score)
            editor.apply()
            fragmentTransaction?.replace(R.id.fragmentContainerView, quizResult)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        return view
    }

    private fun selectedOptionStrokeColor(selectedOption: MaterialCardView) {
        removeColour()
        selectedOption.setCardBackgroundColor(Color.parseColor("#363A43"))
        selectedOption.strokeColor = Color.parseColor("#323232")
        selectedOption.strokeWidth = 5
    }

    private fun removeColour() {
        val optionList = arrayListOf(
            option1CardView,
            option2CardView,
            option3CardView,
            option4CardView
        )
        for (cardView in optionList) {
            cardView.setCardBackgroundColor(Color.parseColor("#FFE5B4"))
            cardView.strokeColor = Color.parseColor("#FFFFFF")
            cardView.strokeWidth = 0
        }

    }

    private fun checkAnswer() {

        val currQuestion = questionsList[currentQuestion - 1]
        val correctAnswer = currQuestion.correctAnswer
        if (selectedOptionPosition == 0) {
            Toast.makeText(requireContext(), "Please Select Any option!", Toast.LENGTH_LONG).show()
        } else if (selectedOptionPosition == correctAnswer) {
            score++

            Toast.makeText(requireContext(), "Correct Answer!", Toast.LENGTH_LONG).show()
            editor.putInt("scoreValue", score)
            editor.apply()

            if (currentQuestion  == questionsList.size) {
                checkAnswerCardView.visibility = View.GONE
                checkScoreCardView.visibility = View.VISIBLE
                nextQuestionCardView.isClickable=false
                nextQuestionCardView.isFocusable=false
            }
        } else {
            Toast.makeText(requireContext(), "Please Try Again!", Toast.LENGTH_LONG).show()
        }
        wrongAnswerStrokeColor(selectedOptionPosition)
        correctAnswerStokeColor(correctAnswer)
    }

    private fun setQuestion() {
        if (currentQuestion - 1 == questionsList.size) {
            checkAnswerCardView.visibility = View.GONE
            checkScoreCardView.visibility = View.VISIBLE
            nextQuestionCardView.isClickable=false
            nextQuestionCardView.isFocusable=false
        } else {
            val currQuestion = questionsList[currentQuestion - 1]
            questionNumber.text = "$currentQuestion"
            question.text = currQuestion.question
            option1Text.text = currQuestion.options[0]
            option2Text.text = currQuestion.options[1]
            option3Text.text = currQuestion.options[2]
            option4Text.text = currQuestion.options[3]

        }
    }

    private fun correctAnswerStokeColor(correctAnswer: Int) {
        if (correctAnswer == 1) {
            option1CardView.strokeColor = Color.parseColor("#ff669900")
            option1CardView.strokeWidth = 8
        }
        if (correctAnswer == 2) {
            option2CardView.strokeColor = Color.parseColor("#ff669900")
            option2CardView.strokeWidth = 8
        }
        if (correctAnswer == 3) {
            option3CardView.strokeColor = Color.parseColor("#ff669900")
            option3CardView.strokeWidth = 8
        }
        if (correctAnswer == 4) {
            option3CardView.strokeColor = Color.parseColor("#ff669900")
            option3CardView.strokeWidth = 8
        }
    }

    private fun wrongAnswerStrokeColor(selectedOptionPosition: Int) {
        if (selectedOptionPosition == 1) {
            option1CardView.strokeColor = Color.parseColor("#ffcc0000")
            option1CardView.strokeWidth = 8
        }
        if (selectedOptionPosition == 2) {
            option2CardView.strokeColor = Color.parseColor("#ffcc0000")
            option2CardView.strokeWidth = 8
        }
        if (selectedOptionPosition == 3) {
            option3CardView.strokeColor = Color.parseColor("#ffcc0000")
            option3CardView.strokeWidth = 8
        }
        if (selectedOptionPosition == 4) {
            option3CardView.strokeColor = Color.parseColor("#ffcc0000")
            option3CardView.strokeWidth = 8
        }
    }


    private fun addQuestions() {
        val bloodQuestions: ArrayList<Question> = arrayListOf(
            Question(
                "What is the primary function of red blood cells (RBCs)?",
                listOf(
                    "1. Oxygen transport",
                    "2. Nutrient absorption",
                    "3. Immune response",
                    "4. Hormone production"
                ),
                1
            ),
            Question(
                "Which blood type is considered the universal donor?",
                listOf("1. A", "2. B", "3. AB", "4. O"),
                4
            ),
            Question(
                "What is the liquid component of blood called?",
                listOf("1. Plasma", "2. Serum", "3. Hemoglobin", "4. Platelets"),
                1
            ),
            Question(
                "What is the average lifespan of a red blood cell?",
                listOf("1. 120 days", "2. 30 days", "3. 365 days", "4. 7 days"),
                1
            ),
            Question(
                "Which component of blood is responsible for clotting and preventing excessive bleeding?",
                listOf("1. Platelets", "2. White blood cells", "3. Red blood cells", "4. Plasma"),
                1
            ),
            Question(
                "What is the protein in red blood cells that binds to oxygen and carries it throughout the body?",
                listOf("1. Hemoglobin", "2. Insulin", "3. Melatonin", "4. Albumin"),
                1
            ),
            Question(
                "What is the most abundant type of white blood cell?",
                listOf("1. Neutrophils", "2. Lymphocytes", "3. Monocytes", "4. Eosinophils"),
                1
            ),
            Question(
                "Which blood component is responsible for the body's immune response?",
                listOf("1. White blood cells", "2. Plasma", "3. Platelets", "4. Red blood cells"),
                1
            ),
            Question(
                "Which of the following blood cells are involved in allergic reactions and parasitic infections?",
                listOf("1. Eosinophils", "2. Basophils", "3. Neutrophils", "4. Monocytes"),
                1
            ),
            Question(
                "What is the condition in which there is an abnormally low number of red blood cells or a deficiency of hemoglobin in the blood?",
                listOf("1. Anemia", "2. Leukemia", "3. Hemophilia", "4. Thrombocytopenia"),
                1
            )
        )
        questionsList = bloodQuestions
    }

    private fun hooks(view: View) {
        questionNumber = view.findViewById(R.id.questionNumber)
        question = view.findViewById(R.id.question)
        option1Text = view.findViewById(R.id.option1Text)
        option2Text = view.findViewById(R.id.option2Text)
        option3Text = view.findViewById(R.id.option3Text)
        option4Text = view.findViewById(R.id.option4Text)


        option1CardView = view.findViewById(R.id.option1CardView)
        option2CardView = view.findViewById(R.id.option2CardView)
        option3CardView = view.findViewById(R.id.option3CardView)
        option4CardView = view.findViewById(R.id.option4CardView)
        checkAnswerCardView = view.findViewById(R.id.checkAnswerCardView)
        nextQuestionCardView = view.findViewById(R.id.nextQuestionCardView)
        checkScoreCardView = view.findViewById(R.id.checkScoreCardView)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuizFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}