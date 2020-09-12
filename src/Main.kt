import java.io.File
import kotlin.system.measureNanoTime

fun main() {
    val dict = Dictionnary("Dictionnaries/dico.txt")
    val corrector = Corrector(dict)
    println("Correction: " + measureNanoTime { File("Dictionnaries/fautes.txt").forEachLine { corrector.correct(it) } }.toFloat() / 1_000_000_000)
}

