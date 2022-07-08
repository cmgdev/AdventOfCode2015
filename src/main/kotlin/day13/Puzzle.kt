package day13

import util.permute
import kotlin.math.max

fun main() {
    println(findHappiness(EXAMPLE) == 330)
    println(findHappiness(INPUT) == 733)
}

fun findHappiness(input: String): Int {
    val names = mutableSetOf<String>()
    val relationships = mutableMapOf<Pair<String, String>, Int>()

    input.lines().filterNot { it.trim().isBlank() }
        .forEach {
            val parts = it.split(" ")
            names.add(parts[0])
            relationships[Pair(parts[0], parts[2])] = parts[1].toInt()
        }

    val possibleSeats = permute(names.toList())
    var maxHappiness = 0
    possibleSeats.forEach { seats ->
        val thisHappiness = seats.windowed(2, partialWindows = true).sumOf {
            val nameA = it.first()
            val nameB = if (it.size == 2) it.last() else seats.first()
            val happinessA = relationships[Pair(nameA, nameB)] ?: 0
            val happinessB = relationships[Pair(nameB, nameA)] ?: 0
            happinessA + happinessB
        }
        maxHappiness = max(maxHappiness, thisHappiness)
    }

    return maxHappiness
}

const val EXAMPLE = """
Alice 54 Bob
Alice -79 Carol
Alice -2 David
Bob 83 Alice
Bob -7 Carol
Bob -63 David
Carol -62 Alice
Carol 60 Bob
Carol 55 David
David 46 Alice
David -7 Bob
David 41 Carol
"""

const val INPUT = """
Alice 2 Bob
Alice 26 Carol
Alice -82 David
Alice -75 Eric
Alice 42 Frank
Alice 38 George
Alice 39 Mallory
Bob 40 Alice
Bob -61 Carol
Bob -15 David
Bob 63 Eric
Bob 41 Frank
Bob 30 George
Bob 87 Mallory
Carol -35 Alice
Carol -99 Bob
Carol -51 David
Carol 95 Eric
Carol 90 Frank
Carol -16 George
Carol 94 Mallory
David 36 Alice
David -18 Bob
David -65 Carol
David -18 Eric
David -22 Frank
David 2 George
David 42 Mallory
Eric -65 Alice
Eric 24 Bob
Eric 100 Carol
Eric 51 David
Eric 21 Frank
Eric 55 George
Eric -44 Mallory
Frank -48 Alice
Frank 91 Bob
Frank 8 Carol
Frank -66 David
Frank 97 Eric
Frank -9 George
Frank -92 Mallory
George -44 Alice
George -25 Bob
George 17 Carol
George 92 David
George -92 Eric
George 18 Frank
George 97 Mallory
Mallory 92 Alice
Mallory -96 Bob
Mallory -51 Carol
Mallory -81 David
Mallory 31 Eric
Mallory -73 Frank
Mallory -89 George
"""