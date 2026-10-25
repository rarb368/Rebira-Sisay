package com.example.data

import com.example.model.Question
import com.example.model.UnitSummary

class QuestionRepository {

    private val customQuestions = mutableListOf<Question>()

    private val baseQuestions: List<Question> by lazy {
        val list = mutableListOf<Question>()
        list.addAll(ChemistryQuestions2017Part1.getQuestions())
        list.addAll(ChemistryQuestions2017Part2.getQuestions())
        list.addAll(BiologyQuestions2017Part1.getQuestions())
        list.addAll(BiologyQuestions2017Part2.getQuestions())
        list.addAll(EnglishQuestions2017Part1.getQuestions())
        list.addAll(EnglishQuestions2017Part2.getQuestions())
        list.addAll(PhysicsQuestions2017Part1.getQuestions())
        list.addAll(PhysicsQuestions2017Part2.getQuestions())
        list.addAll(AptitudeQuestions2017Part1.getQuestions())
        list.addAll(AptitudeQuestions2017Part2.getQuestions())
        list.addAll(MathsQuestions.getNaturalMathsQuestions())
        list.addAll(MathsQuestions.getSocialMathsQuestions())
        list.addAll(DiagramQuestions2017.getDiagramQuestions())
        list
    }

    fun getAllQuestions(): List<Question> {
        val result = mutableListOf<Question>()
        result.addAll(baseQuestions)
        result.addAll(customQuestions)
        return result
    }

    fun getQuestionsBySubject(subject: String): List<Question> {
        return getAllQuestions().filter { it.subject.equals(subject, ignoreCase = true) }
    }

    fun getQuestionsForFullExam(subject: String, year: String): List<Question> {
        return getAllQuestions()
            .filter { it.subject.equals(subject, ignoreCase = true) && (year == "All" || it.year == year) }
            .sortedBy { it.questionNumber }
    }

    fun getQuestionsForUnit(subject: String, grade: Int, unitTitle: String, year: String? = null): List<Question> {
        return getAllQuestions().filter {
            it.subject.equals(subject, ignoreCase = true) &&
                    it.grade == grade &&
                    it.unitTitle.equals(unitTitle, ignoreCase = true) &&
                    (year == null || year == "All" || it.year == year)
        }.sortedBy { it.questionNumber }
    }

    fun getUnitsForSubject(subject: String, gradeFilter: Int? = null, yearFilter: String? = null): List<UnitSummary> {
        val filtered = getAllQuestions().filter {
            it.subject.equals(subject, ignoreCase = true) &&
                    (gradeFilter == null || it.grade == gradeFilter) &&
                    (yearFilter == null || yearFilter == "All" || it.year == yearFilter)
        }

        val grouped = filtered.groupBy { "${it.grade}_${it.unitNumber}_${it.unitTitle}" }
        return grouped.map { (_, qList) ->
            val first = qList.first()
            UnitSummary(
                grade = first.grade,
                unitNumber = first.unitNumber,
                unitTitle = first.unitTitle,
                totalQuestions = qList.size,
                attemptedQuestions = 0,
                correctQuestions = 0
            )
        }.sortedWith(compareBy({ it.grade }, { it.unitNumber }))
    }

    fun getAvailableYears(subject: String): List<String> {
        val years = getAllQuestions()
            .filter { it.subject.equals(subject, ignoreCase = true) }
            .map { it.year }
            .distinct()
            .sortedDescending()
        return if (years.isNotEmpty()) listOf("All") + years else listOf("All", "2017", "2016", "2015", "2014/15")
    }

    fun addQuestion(question: Question) {
        customQuestions.add(question.copy(isCustom = true))
    }

    fun updateQuestion(question: Question) {
        val index = customQuestions.indexOfFirst { it.id == question.id }
        if (index != -1) {
            customQuestions[index] = question
        } else {
            customQuestions.add(question.copy(isCustom = true))
        }
    }

    fun deleteQuestion(questionId: String) {
        customQuestions.removeAll { it.id == questionId }
    }
}
