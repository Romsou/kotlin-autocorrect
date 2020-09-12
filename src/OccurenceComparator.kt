class OccurenceComparator : Comparator<WordAndDistance> {
    override fun compare(o1: WordAndDistance?, o2: WordAndDistance?): Int {
        return when {
            o1 == o2 -> 0
            o1?.count!! > o2?.count!! -> return -1
            o1.count == o2.count -> return 0
            o1.count < o2.count -> return 1
            else -> throw IllegalArgumentException()
        }
    }
}