package day14

import kotlin.math.min

fun main() {
    val travelSecondsExample = 1000
    val travelSeconds = 2503

    val cometExample = ReindeerStats("comet", 14, 10, 127)
    val dancerExample = ReindeerStats("dancer", 16, 11, 162)
    println(cometExample.distanceTravelledIn(1) == 14)
    println(dancerExample.distanceTravelledIn(1) == 16)
    println(cometExample.distanceTravelledIn(travelSecondsExample) == 1120)
    println(dancerExample.distanceTravelledIn(travelSecondsExample) == 1056)

    val vixen = ReindeerStats("vixen", 8, 8, 53)
    val blitzen = ReindeerStats("blitzen", 13, 4, 49)
    val rudolph = ReindeerStats("rudolph", 20, 7, 132)
    val cupid = ReindeerStats("cupid", 12, 4, 43)
    val donner = ReindeerStats("donner", 9, 5, 38)
    val dasher = ReindeerStats("dasher", 10, 4, 37)
    val comet = ReindeerStats("comet", 3, 37, 76)
    val prancer = ReindeerStats("prancer", 9, 12, 97)
    val dancer = ReindeerStats("dancer", 37, 1, 36)

    val speeds = mutableListOf(
        vixen.distanceTravelledIn(travelSeconds),
        blitzen.distanceTravelledIn(travelSeconds),
        rudolph.distanceTravelledIn(travelSeconds),
        cupid.distanceTravelledIn(travelSeconds),
        donner.distanceTravelledIn(travelSeconds),
        dasher.distanceTravelledIn(travelSeconds),
        comet.distanceTravelledIn(travelSeconds),
        prancer.distanceTravelledIn(travelSeconds),
        dancer.distanceTravelledIn(travelSeconds)
    )
    speeds.sortDescending()
    println(speeds.first() == 2655)

    println("\npart 2")
    val exampleRunningDistances = listOf(
        cometExample.runningDistanceTravelled(travelSecondsExample),
        dancerExample.runningDistanceTravelled(travelSecondsExample)
    )
    println(assignPoints(exampleRunningDistances).max() == 689)

    val runningDistances = listOf(
        vixen.runningDistanceTravelled(travelSeconds),
        blitzen.runningDistanceTravelled(travelSeconds),
        rudolph.runningDistanceTravelled(travelSeconds),
        cupid.runningDistanceTravelled(travelSeconds),
        donner.runningDistanceTravelled(travelSeconds),
        dasher.runningDistanceTravelled(travelSeconds),
        comet.runningDistanceTravelled(travelSeconds),
        prancer.runningDistanceTravelled(travelSeconds),
        dancer.runningDistanceTravelled(travelSeconds)
    )
    println(assignPoints(runningDistances).max() == 1059)
}

fun assignPoints(runningDistances: List<List<Int>>): List<Int> {
    val points = MutableList(runningDistances.size) { 0 }
    val iterations = runningDistances.minOf { it.size }

    (0 until iterations).forEach { i ->
        val thisIteration = runningDistances.map { it[i] }.toList()
        val max = thisIteration.max()
        val leadingReindeerIdx = thisIteration.mapIndexed { idx, it -> if (it == max) idx else -1 }.filter { it >= 0 }
        leadingReindeerIdx.forEach { points[it] = points[it] + 1 }
    }

    return points
}

data class ReindeerStats(val name: String, val speed: Int, val duration: Int, val rest: Int) {
    fun distanceTravelledIn(travelSeconds: Int): Int {
        var distance = 0
        var remainingSeconds = travelSeconds
        val cycleTime = duration + rest

        while (remainingSeconds > 0) {
            if (remainingSeconds <= cycleTime) {
                distance += speed * min(remainingSeconds, duration)
                remainingSeconds -= cycleTime
            } else {
                val times = remainingSeconds / cycleTime
                distance += speed * times * duration
                remainingSeconds -= times * cycleTime
            }
        }
        return distance
    }

    fun runningDistanceTravelled(travelSeconds: Int): List<Int> {
        return (1..travelSeconds).map { this.distanceTravelledIn(it) }.toList()
    }
}