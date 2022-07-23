package day20

import util.getFactors

fun main() {
    var firstHouse = solvePart1(776000)
    println("${firstHouse == 776160}")

    firstHouse = solvePart2(786000)
    println("${firstHouse == 786240}")
}

private const val MIN_PRESENTS = 33100000

private fun solvePart2(startAtHouse: Int): Int {
    var presentsAtHouse = 0
    var currentHouse = startAtHouse
    while (presentsAtHouse <= MIN_PRESENTS) {
        currentHouse++
        presentsAtHouse = getPresentsAtHousePart2(currentHouse)
    }
    return currentHouse
}

private fun solvePart1(startAtHouse: Int): Int {
    var presentsAtHouse = 0
    var currentHouse = startAtHouse
    while (presentsAtHouse <= MIN_PRESENTS) {
        currentHouse++
        presentsAtHouse = getPresentsAtHouse(currentHouse)
    }
    return currentHouse
}

fun getPresentsAtHouse(houseNum: Int): Int {
    return getFactors(houseNum).sumOf { it * 10 }
}

fun getPresentsAtHousePart2(houseNum: Int): Int {
    return getFactors(houseNum).filter { houseNum < it * 50 }.sumOf { it * 11 }
}

