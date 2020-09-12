import kotlin.math.min

class DistanceCalculator {
    companion object {

        fun levenshteinDistance(string1: String, string2: String): Int {
            var cost = 0
            val row = string1.length + 1
            val col = string2.length + 1
            val substitutionCosts = Array(row) { IntArray(col) }

            for (i in 0 until row)
                substitutionCosts[i][0] = i

            for (j in 0 until col)
                substitutionCosts[0][j] = j

            for (i in 1 until row) {
                for (j in 1 until col) {
                    cost = when (string1[i - 1] == string2[j - 1]) {
                        true -> 0
                        false -> 1
                    }

                    substitutionCosts[i][j] = min(
                        substitutionCosts[i - 1][j] + 1,
                        min(
                            substitutionCosts[i][j - 1] + 1,
                            substitutionCosts[i - 1][j - 1] + cost
                        )
                    )
                }
            }

            return substitutionCosts[row - 1][col - 1]
        }
    }

}