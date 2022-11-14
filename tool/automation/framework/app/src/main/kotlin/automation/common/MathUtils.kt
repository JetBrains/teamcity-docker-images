package automation.common

class MathUtils {
    companion object {
        /**
         * Calculates modulo percentage increase from initial to final value.
         * @param initial - initial value
         * @param final - final value
         * @return percentage increase
         */
        fun getPercentageIncrease(initial: Int, final: Int): Float {
            return Math.abs(((100f*(final - initial)) / initial))
        }
    }
}