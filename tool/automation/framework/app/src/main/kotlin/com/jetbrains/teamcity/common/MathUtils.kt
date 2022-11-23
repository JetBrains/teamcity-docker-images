package com.jetbrains.teamcity.common

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
    }
}
