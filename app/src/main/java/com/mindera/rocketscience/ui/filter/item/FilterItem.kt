package com.mindera.rocketscience.ui.filter.item

data class FilterItem(
    val asc: Boolean,
    val desc: Boolean,
    val successLaunches: Boolean,
    val failedLaunches: Boolean,
    val dateRange: List<Float>
)