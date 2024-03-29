import java.io.File
import javax.swing.text.StyledEditorKit.BoldAction

fun main() {
    part1()
    part2()
}

fun part2() {
    val file = File("src/main/Day_2/input.txt")
    val gameList: ArrayList<Game> = ArrayList()
    var result = 0

    file.forEachLine eachLine@{ line -> gameList.add(Game(line)) }
    gameList.forEach { game ->
        result += game.score
    }
    println("Part2: $result")
}

private fun part1() {
    val file = File("src/main/Day_2/input.txt")
    val gameList: ArrayList<Game> = ArrayList()
    val isPossibleGame = Game(12, 14, 13)
    var result = 0

    file.forEachLine eachLine@{ line -> gameList.add(Game(line)) }
    gameList.forEach { game ->
        if (game.isGamePossible(isPossibleGame)) {
            result += game.id
        }
    }
    println("Part1: $result")
}


class Game(line: String) {

    var id: Int = -1
    val sets: ArrayList<Subset> = ArrayList()
    var highestRed = 0
    var highestBlue = 0
    var highestGreen = 0
    val score
        get() = highestRed * highestBlue * highestGreen



    constructor(red: Int = 0, blue: Int = 0, green: Int = 0) : this("") {
        sets.add(Subset(red, blue, green))
        if (red > highestRed) highestRed = red
        if (blue > highestBlue) highestBlue = blue
        if (green > highestGreen) highestGreen = green
    }

    init {
        if (line.isNotEmpty()) {
            val list = line.split(";", ":")
            for (i in 1..<list.size) {
                sets.add(Subset(list[i]))
                if (sets[i-1].red > highestRed) highestRed = sets[i-1].red
                if (sets[i-1].blue > highestBlue) highestBlue = sets[i-1].blue
                if (sets[i-1].green > highestGreen) highestGreen = sets[i-1].green
            }
            id = list[0].split(" ")[1].toInt()
        }
    }

    /**
     * @return Returns true if other game is possible with sets of this game.
     */
    fun isGamePossible(other: Game): Boolean {
        sets.forEach { subset ->
            if (!subset.isSetPossible(other.sets[0])) {
                return false
            }
        }
        return true
    }

    override fun toString(): String {
        return "Game(id=$id, sets=$sets)"
    }


}


class Subset(set: String) {
    var red: Int = 0
    var blue: Int = 0
    var green: Int = 0

    constructor(red: Int = 0, blue: Int = 0, green: Int = 0) : this("") {
        this.red = red
        this.blue = blue
        this.green = green
    }

    init {
        if (set.isNotEmpty()) {
            val tokens = set.split(",")
            tokens.forEach {
                val list = it.split(" ")
                when (list[2]) {
                    "red" -> red = list[1].toInt()
                    "blue" -> blue = list[1].toInt()
                    "green" -> green = list[1].toInt()
                }
            }
        }
    }

    fun isSetPossible(other: Subset): Boolean {
        return (red <= other.red) and (blue <= other.blue) and (green <= other.green)
    }

    override fun toString(): String {
        return "Subset(red=$red, blue=$blue, green=$green)"
    }
}