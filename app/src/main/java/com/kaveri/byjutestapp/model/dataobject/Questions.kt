package com.kaveri.byjutestapp.model.dataobject

data class Questions (
    var id: String? = null,
    var qno: Int? = null,
    var text: String? = null,
    var mcOptions: ArrayList<String> = arrayListOf(),
    var type: String? = null,
    var marks: Int? = null) {
}