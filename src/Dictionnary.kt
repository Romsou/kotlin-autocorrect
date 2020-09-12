import java.io.File

class Dictionnary(filepath: String?) {
    val words = HashSet<String>()


    init {
        File(filepath).forEachLine { words.add(it) }
    }

    fun contains(word: String): Boolean {
        return words.contains(word)
    }
}