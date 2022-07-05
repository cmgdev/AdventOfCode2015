package day09

import util.permute
import kotlin.math.min

fun main() {
    println(findShortestRoute(EXAMPLE.lines()) == 605)

    println(findShortestRoute(INPUT.lines()) == 117)
}

fun findShortestRoute(lines: List<String>): Int {
    val names = mutableSetOf<String>()
    val edges = mutableListOf<Edge>()

    lines.map { it.trim() }.filter { it.isNotBlank() }
        .forEach {
            val parts = it.split(" ")
            names.add(parts[0])
            names.add(parts[2])
            edges.add(Edge(parts[0], parts[2], parts[4].toInt()))
            edges.add(Edge(parts[2], parts[0], parts[4].toInt()))
        }

    val permutations = permute(names.toList())

    var shortest = Int.MAX_VALUE
    permutations.forEach { p ->
        val dist = p.windowed(2).sumOf { pair ->
            edges.filter { edge -> edge.nameA == pair[0] && edge.nameB == pair[1] }.map { edge -> edge.weight }.first()
        }

        shortest = min(shortest, dist)
    }

    return shortest
}

data class Edge(val nameA: String, val nameB: String, val weight: Int)


const val EXAMPLE = """
London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141
"""

const val INPUT = """
Faerun to Tristram = 65
Faerun to Tambi = 129
Faerun to Norrath = 144
Faerun to Snowdin = 71
Faerun to Straylight = 137
Faerun to AlphaCentauri = 3
Faerun to Arbre = 149
Tristram to Tambi = 63
Tristram to Norrath = 4
Tristram to Snowdin = 105
Tristram to Straylight = 125
Tristram to AlphaCentauri = 55
Tristram to Arbre = 14
Tambi to Norrath = 68
Tambi to Snowdin = 52
Tambi to Straylight = 65
Tambi to AlphaCentauri = 22
Tambi to Arbre = 143
Norrath to Snowdin = 8
Norrath to Straylight = 23
Norrath to AlphaCentauri = 136
Norrath to Arbre = 115
Snowdin to Straylight = 101
Snowdin to AlphaCentauri = 84
Snowdin to Arbre = 96
Straylight to AlphaCentauri = 107
Straylight to Arbre = 14
AlphaCentauri to Arbre = 46    
"""