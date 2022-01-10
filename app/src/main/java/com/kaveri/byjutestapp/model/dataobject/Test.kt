package com.kaveri.byjutestapp.model.dataobject

data class Test (
    var assessmentId: String? = null,
    var assessmentName: String? = null,
    var subject: String? = null,
    var duration: Int? = null,
    var questions: ArrayList<Questions> = arrayListOf(),
    var totalMarks: Int? = null
)