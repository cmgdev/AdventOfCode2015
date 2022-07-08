package day14

import kotlin.math.min

fun main() {
    println(distanceTravelled(14, 10, 127, 1) == 14)
    println(distanceTravelled(16, 11, 162, 1) == 16)
    println(distanceTravelled(14, 10, 127, 1000) == 1120)
    println(distanceTravelled(16, 11, 162, 1000) == 1056)

    val vixen = distanceTravelled(8, 8, 53, 2503)
    val blitzen = distanceTravelled(13, 4, 49, 2503)
    val rudolph = distanceTravelled(20, 7, 132, 2503)
    val cupid = distanceTravelled(12, 4, 43, 2503)
    val donner = distanceTravelled(9, 5, 38, 2503)
    val dasher = distanceTravelled(10, 4, 37, 2503)
    val comet = distanceTravelled(3, 37, 76, 2503)
    val prancer = distanceTravelled(9, 12, 97, 2503)
    val dancer = distanceTravelled(37, 1, 36, 2503)

    val speeds = mutableListOf<Int>(dasher, dancer, prancer, vixen, comet, cupid, donner, blitzen, rudolph)
    speeds.sortDescending()
    println(speeds.first() == 2655)
    /*
Vixen can fly    8 km/s for 8 seconds, but then must rest for  53 seconds.
Blitzen can fly 13 km/s for 4 seconds, but then must rest for  49 seconds.
Rudolph can fly 20 km/s for 7 seconds, but then must rest for  132 seconds.
Cupid can fly   12 km/s for 4 seconds, but then must rest for  43 seconds.
Donner can fly   9 km/s for 5 seconds, but then must rest for  38 seconds.
Dasher can fly  10 km/s for 4 seconds, but then must rest for  37 seconds.
Comet can fly    3 km/s for 37 seconds, but then must rest for 76 seconds.
Prancer can fly  9 km/s for 12 seconds, but then must rest for 97 seconds.
Dancer can fly  37 km/s for 1 seconds, but then must rest for  36 seconds.

     */
}

fun distanceTravelled(speed: Int, duration: Int, rest: Int, travelSeconds: Int): Int {
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