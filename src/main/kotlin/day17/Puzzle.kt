package day17

import util.combinations

fun main() {
    val exampleCombos = combosToTarget(getContainers(EXAMPLE), 25)
    println(exampleCombos.size == 4)

    val combos = combosToTarget(getContainers(INPUT), 150)
    println(combos.size == 4372)
}

fun getContainers(input: String): List<Container> {
    return input.split(",").mapIndexed { idx, it ->
        Container(idx, it.toInt())
    }.toList()
}

fun combosToTarget(containers: List<Container>, target: Int): List<List<Container>> {
    val combos = mutableListOf<List<Container>>()
    (2 until containers.size).forEach { count ->
        combos.addAll(combinations(containers, count).filter { trial ->
            trial.sumOf { it.capacity } == target
        })
    }
    return combos
}

data class Container(val id: Int, val capacity: Int)

const val EXAMPLE = "20,15,10,5,5"
const val INPUT = "11,30,47,31,32,36,3,1,5,3,32,36,15,11,46,26,28,1,19,3"