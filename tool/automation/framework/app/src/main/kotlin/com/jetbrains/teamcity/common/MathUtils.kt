package com.jetbrains.teamcity.common

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.abs

/**
 * Utilities for mathematical operations.
 */
class MathUtils {
    companion object {
        /**
         * Calculates modulo percentage increase from initial to final value.
         * @param initial - initial value
         * @param final - final value
         * @return percentage increase
         */
        fun getPercentageIncrease(initial: Long, final: Long): Float {
            return abs(((100f*(final - initial)) / initial))
        }

        /**
         * Rounds decimal number down to 2 digits after floating point.
         * @param number original number
         * @return rounded number
         */
        fun roundOffDecimal(number: Float): Double? {
            val digitalFormat = DecimalFormat("#.##")
            digitalFormat.roundingMode = RoundingMode.FLOOR
            return digitalFormat.format(number).toDouble()
        }
    }
}
