package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.model.room.Count

object ScrapCountFormatter {
    fun convert(count: Int): String {
        return when {
            count < Count.THOUSAND.value -> count.toString()
            count < Count.TEN_THOUSAND.value -> formatNumber(
                count / Count.THOUSAND.value.toDouble(),
                "천"
            )

            else -> formatNumber(count / Count.TEN_THOUSAND.value.toDouble(), "만")
        }
    }

    private fun formatNumber(number: Double, unit: String): String {
        val formatted = if (number % 1 == 0.0) {
            "%.0f $unit"
        } else {
            "%.1f $unit"
        }
        return String.format(formatted, number)
    }
}
