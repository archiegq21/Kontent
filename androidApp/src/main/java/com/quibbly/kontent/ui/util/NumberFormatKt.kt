package com.quibbly.kontent.ui.util

import java.text.NumberFormat
import java.util.*

fun Double.formatToAmount(
    currencyCode: String,
): String {
    val numberFormat = NumberFormat.getInstance().apply {
        if (currencyCode.isNotEmpty()) {
            currency = Currency.getInstance(currencyCode)
        }
    }
    return "${numberFormat.format(this)} $currencyCode"
}