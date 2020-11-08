package com.example.machinetestandroid.data.network.responses

data class Item(
        var answer_id: Int = 0,
        var question_id: Int?,
        var content_license: String?,
        var is_accepted: Boolean?,
        var score: Int?,
        var last_activity_date: Int?,
        var creation_date: Int?,
        var owner: Owner?
)