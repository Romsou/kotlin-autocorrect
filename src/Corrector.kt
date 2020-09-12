import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Corrector(var dictionnary: Dictionnary) {

    var trigramToStrings = HashMap<String, LinkedList<String>?>()

    init {
        for (word in dictionnary.words) {
            for (trigram in getTrigrams(word)) {
                if (!trigramToStrings.containsKey(trigram))
                    trigramToStrings.put(trigram, LinkedList())
                trigramToStrings.get(trigram)?.add(word)
            }
        }
    }

    fun correct(word: String): List<Any>? {
        if (dictionnary.contains(word))
            return null

        val wordAndCount = HashMap<String, Int>()
        for (trigram in getTrigrams(word))
            countWordsOccurences(trigram, wordAndCount)

        val occurenceMaxHeap = sortByOccurence(wordAndCount)
        val distanceMinHeap = sortByDistance(occurenceMaxHeap, word)
        val closestWords = RetrieveClosestWords(distanceMinHeap)

        return closestWords
    }

    private fun RetrieveClosestWords(distanceMinHeap: PriorityQueue<WordAndDistance>): LinkedList<String> {
        val closestWords = LinkedList<String>()
        for (i in 0..4)
            closestWords.add(distanceMinHeap.poll().word)
        return closestWords
    }

    private fun sortByDistance(
        occurenceMaxHeap: PriorityQueue<WordAndDistance>,
        word: String
    ): PriorityQueue<WordAndDistance> {
        val distanceMinHeap = PriorityQueue(5, DistanceComparator())
        for (i in 0..99) {
            if (occurenceMaxHeap.peek() == null)
                break

            var word2 = occurenceMaxHeap.poll().word
            var distance = DistanceCalculator.levenshteinDistance(word, word2)
            var wordAndDistance = WordAndDistance(word2, distance)

            distanceMinHeap.add(wordAndDistance)
        }
        return distanceMinHeap
    }

    private fun countWordsOccurences(trigram: String, wordAndCount: HashMap<String, Int>) {
        if (!trigramToStrings.contains(trigram))
            return

        for (word in trigramToStrings[trigram]!!) {
            if (!wordAndCount.contains(word))
                wordAndCount.put(word, 1)
            else
                wordAndCount.replace(word, wordAndCount.get(word)!! + 1)
        }
    }

    private fun sortByOccurence(wordAndCount: HashMap<String, Int>): PriorityQueue<WordAndDistance> {
        val occurencesMaxHeap = PriorityQueue(100, OccurenceComparator())

        for ((key, value) in wordAndCount) {
            if (wordAndCount[key] != null) {
                val wordAndDistance = WordAndDistance(key, value)
                occurencesMaxHeap.add(wordAndDistance)
            }
        }

        return occurencesMaxHeap
    }

    private fun getTrigrams(word: String): HashSet<String> {
        val wordExt = "<$word>"
        val trigrams = HashSet<String>()

        for (i in 0..(wordExt.length - 3))
            trigrams.add(wordExt.substring(i, i + 3))

        return trigrams
    }


}