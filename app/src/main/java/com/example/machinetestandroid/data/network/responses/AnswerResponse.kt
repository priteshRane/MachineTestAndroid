package com.example.machinetestandroid.data.network.responses

data class AnswerResponse (
        val has_more: Boolean?,
        val backoff: Int?,
        val quota_max: Int?,
        val quota_remaining: Int?,
        val items: List<Item>
)