package day18

const val ON = '#'
const val OFF = '.'

fun main() {
    println(runIterations(EXAMPLE.lines(), 4).sumOf { line -> line.count { it == ON } } == 4)
    println(runIterations(INPUT.lines(), 100).sumOf { line -> line.count { it == ON } } == 1061)

    println(runIterations(EXAMPLE.lines(), 5, true).sumOf { line -> line.count { it == ON } } == 17)
    println(runIterations(INPUT.lines(), 100, true).sumOf { line -> line.count { it == ON } } == 1006)
}

fun runIterations(input: List<String>, iterations: Int, part2: Boolean = false): List<CharArray> {
    var map = input.map { it.trim() }.filterNot { it.isBlank() }.map { it.toCharArray() }.toMutableList()
    val xRange = IntRange(0, map.size - 1)
    val yRange = IntRange(0, map[0].size - 1)

    if (part2) {
        map[xRange.first][yRange.first] = ON
        map[xRange.first][yRange.last] = ON
        map[xRange.last][yRange.first] = ON
        map[xRange.last][yRange.last] = ON

    }

    (0 until iterations).forEach { _ ->
        val nextMap = mutableListOf<CharArray>()
        xRange.forEach { x ->
            val row = CharArray(xRange.last + 1)
            yRange.forEach { y ->
                val neighborsOn = getNeighbors(Pair(x, y), xRange, yRange).count { map[it.first][it.second] == ON }


                if (part2 && (x == xRange.first || x == xRange.last) && (y == yRange.first || y == yRange.last)) {
                    row[y] = ON
                } else {
                    val thisPoint = map[x][y]
                    if (thisPoint == ON) {
                        row[y] = if (neighborsOn == 2 || neighborsOn == 3) ON else OFF
                    } else if (thisPoint == OFF) {
                        row[y] = if (neighborsOn == 3) ON else OFF
                    }
                }
            }
            nextMap.add(row)
        }
        map = nextMap
    }
    return map
}

fun getNeighbors(point: Pair<Int, Int>, xRange: IntRange, yRange: IntRange): List<Pair<Int, Int>> {
    val neighbors = mutableListOf<Pair<Int, Int>>()
    (-1..1).forEach { deltaX ->
        (-1..1).forEach { deltaY ->
            val newX = point.first + deltaX
            val newY = point.second + deltaY
            if (!(deltaX == 0 && deltaY == 0)
                && newX in xRange
                && newY in yRange
            ) {
                neighbors.add(Pair(newX, newY))
            }
        }
    }
    return neighbors
}

const val EXAMPLE = """
.#.#.#
...##.
#....#
..#...
#.#..#
####..
"""

const val INPUT = """
#..####.##..#...#..#...#...###.#.#.#..#....#.##..#...##...#..#.....##..#####....#.##..##....##.#....
.#..#..#..#.###...##..#.##.....#...#..##....#####.##............####.#..######..#.#.##.#...#..#...##
#.....##.##.##.#..##.#..###...#.#.#..##..###.####.####.#.####.#...##.#..###.........#.###...#....###
#.###..#######..##..#.....##.#.#.###.#.##..#.##..##.##.#.##...###.#...#.#####.#.##..#.#####..#.#####
#.##.##.###.##..###.#.##.##...##.#.#..##..###.########.#.####..####...#####...#..#...##....##.##.##.
..#.#.#.#..#.#.###....###...#...#.##..####.###.....#.####.###.###.#......#.#.###..#..#.#....#.#####.
...#.###.#....#.###...#.#.#...#...#.#####....#....#...#####..#..#.#..######..#.##.#.##.#..###.#...##
.###...#...#.#..#.#.####.#...#.....##...###.#....#..##.###....#.##....###..#.#####...###.#.##.####..
#.#....##.#.....#####.#.##..#######.#.####..###.##.#####.##.#...###...#.#...###..#...#.#.###.###.###
...##.##.....##..#.##...#.#...#...#.#####.#...#.#.#.#####.##.#...#.#..##.##..#...#....####..###.###.
#..#....######...#...###.#....#####....#.#.#....#....#.#######.#####..#....#....#.##..#.##.###..#...
#####.#.######.#.#####.#..##..##..####..#....#...#######....##..##.#..###..###.###..###...#...######
#...##..##...###....##..##.##..#.#.#.#....##.#.......###..###..###...###..##.##.##.#.#.#..#.#..#..#.
..###....##.###..#.#..########...###...##..#######....##..###..#####.##.#....###..##.##.##.#...##.#.
###..#.#..#.#.##.##...##.....#..###.#..##.##.#....##.#.######..##..#.#.##.###...#..####...#.#..#.###
.######....#..##..#.####.##..#.#..#.#..#....#..##.#..#.#...####..#....#.####.#.###.#...####.#...#.#.
#.######.##..###.###..#..###.#...#..#...#...###.##....#.#......#...#.##.#.###..#.#####.#.#..###..#.#
...#..#...####..###.########.....###.###.#..##.##....######..#..#.....#.##.##.#..##..#..##...#..#..#
#..#..##..#.#.########.##.#.####..#.#####.#.###.##....###..##..#.#.###..#.##..##.##.####...######.##
.######.###....#...##...#..#....##..#.#...###.######.##...#....##.##.#.#.##..#...###.###.#....#..##.
####.#.##..##.##.###...#.###.##..##....###..####.##..#.#.##..###.#..##...####...#..####.#.#..##...#.
.#.#..#.....##...#..#...#.#...#.#.##..#....#..#......#####.#######....#.#..#..###..##.#.########..##
.##.#..#..##..#..####.#...####...#...#..##.#..###.#..######..#.#...###.##...#..#####..##.#..##.#.##.
.###..##.##.##....###.###..#.#...##.#.#...#.#######.####..#..###.#######.#...#.#...#.##...#..####..#
##.########..#..#....#.###..##.##.#.##.#..#......####..##.##.#..####..#####..#.....#####.###..#.#.#.
.#..####..##.#.#..#####.##..#..#.#....#.#####.#####...######........##.##..##.#.#.###..#.#.#.#..##.#
.##..##..#.######..###....#.#.###.#........#..###..#.########.....#.##...#.....#..#...##...#..#.###.
##.##.#..####....####.#######.....#.#.#...#.######.#.....####.####...###..####.##.##....###..#..#...
#.#..####...#......#...###...##....##.#######..#.###.#...###.##.##...####..#.####..#......##..#####.
.#.#...##...#....#.####.##.....#....#.#.#######..###.#.....#.....####...##...#.#.##.####..##.###.#.#
####.#.#.####...#...####.#.....#.#######.#.......####......###..###.#...######..#.##.#.##..#..##..##
..##.###..#..####..####.......######.##..#.....##.##...##.##......#.###..###...#.##.#####.#.######.#
.###..####.###..#..#.......#.##...##...##.######.....#..####.#......#.#...#...#...###...#.#.##..####
.####....##.##.#.....##.###.####.#.......#.......#.#..#.#.#.....###.#.#####.#..#.#.#####.#####.###.#
.##.#.###.#####..#..#....###.#.#.#..#..###..##..####..##.###....#..####.####.#..###.#..######.######
####.#.....##..###....#.....#.##.#.##..##..########.#####..###.####....##.....######.#.#.##.......#.
#.#.##.....#.....##.###.#..#.##.##....#..##....##.#.###.##.#..#..##.##.###.#..##.###...##..###.#####
#.###.#.#.#.#.#.#.#...#..#.###..####.##...#..####.###....#..#..##.#....####..##.##....#.#.##.##....#
...######....#..####...#.#..#.#.#..#.##.#.#.......#..#......##..#...#..#..##...##.#...#.#.#...##.##.
.#####..#...####....#..###..##....#####..###.#.#...###..###.###..##...#......#...#...#.#.#...#.##..#
......#####.#...#.#.#.##..#.###..##..#.#...###..###....##..#####..#######.#..#.###....###...##.#..#.
..##.########.##..#....##.#...##.##.#.#..#.##..#.#.#.##....#.#.#.#.##....##....#....#####.##..#.##.#
####...#....##.#.###......##.##.#..##...#..#####..#.#....##..#####...#.#.##...#.####.####..##.######
.##.###.##.#...#.#....###.#######...##...##..#..##.###.#.####..#..###......#.#.##.#.#....#..##...#..
.#.###.#.###.###.#.##.#..#......####.##...#..##.#..####.....#...#.###.##.##.#..#.##..#.###......#..#
...##.####......#.#.#..###..#....###....#.##.#####..#..#..#...#.#.###...#.#.#.##....###.####..###.#.
##..#.#.#.#....####...#.##.###..####....#..#####.######..#.##.##..#####.#.....#.#...##.#.##.##.#.#..
#..##.#.#.#.###.#.#.###...#.#...##..#..#.#.#.##..###...#..##.#..#.#.#..#.....#.######.#.###..###.#..
....#.#.##.###.##...#.##.#....#..##.#..##...#...#.##.####...##..####.#.........#..##..#...#...##.#..
.##.......##...###.##.#.##.###.##.#..#..#..####...#...#....#####...###..##..#..#..##...#....#..#####
..####..#...#...#..###....##.#.#####..#..#.....#......#...#.......##....####...##....##.##.#.#####.#
##.#.#.#..##..##..#.####.##.##.###.#...###.#....#.....#.###...#######..###.####.###.####.##...##.#..
..#.#...##.#....#..#..##.####.....#.#.#...#..#..###.#..###.#####.#.#####.#.#.#.#.###.##.###..#....##
.###.#...#....###..#...####....####..#.##..#..##.###..#.#.#.#..#...###.#.#...#......#...#.##.##.#...
..####.####.##.#.##....#...##....#..#....#..###..#...#..###.#####.....#####..##.#.#.#.#.#.##.####...
...##.#.##.####..##.###..#.#.#.#.#.#.#..###...#.##..#.####.##...#.#.##......###..#...###....#.#.###.
##...##..#.#.##..#.#.#....#.####.......#.#.#######.#..#....#.###.#...###.##....###.#.#..#.#.##.####.
...##.......######.....##....#...#..#.##.###.#..#.##.###.#.###.#.#.#...#.#...##.##.##..#.##########.
###..#....#.#.....#....###.#...##.......##.#.#..#.#...########......###..##.#..#..####.##..####...#.
......##.###.#.###.....#..#...#.#......##....#....#........#..#...##.##.....#...##.##.........##....
.##.##.#.#...#....######..##....##..##.#.#.##.#.##..##...#..###......##......#.#....#.#.#.......###.
.......#.##..##.#...#.##..#..#####.#..#.######.........###.#####.####.#...##...........##...##..####
#......#.#..#...#...##..#.#.###.##.##.#.#..#.###.##.#..###..#.###..#...###.##..###..#...#..###...#..
####.##..#####..####.#...#..#..###..##.#.#...#...#...#.##.####.##.###....###...#.#.#..####.######.##
.....#..####...#.#.#.####..####..##.###......#.....########.#...#.#..#..#...#.###..##.#####..###.###
.#######.#.##..###.#...###.#####............##.###...#.##.#.##..##.#.#..#.######..######..#..#..####
...##..#.####...#..#.#.##.#....#.####..#..###.###..#.#...#....##.##.#......##..##..#.#.#.###..#..#..
........#...#.##.#.#..#....####....#.##...###..####...###.#.#..######..###..##.#####.###.###.#.#...#
##......##.#..###.####.##.#.###.#.......#.##..####..#.###.##..##..##...##...#.###...#.#..#..#.#####.
##..#.#.....##.####.#..##.#.##.#.#...#...#.#...####.#.#.##...##....##.###..###.####.#...#.###..#####
.#####.####.####.####.#.##.##......###....###.####...###...#...#..#.##.#.#####.###..##.#..###...##..
.#...#..##...##...#....#.#.#..##..#.##..#.###.#.###..###.#.#.###.#....#######.####.##..#..#...####..
..##.##..#.##..#.#.###..#.##.########...####.#.###.##..#..###.###...##..##.#..#.######.##.#....###.#
##.#####.###.##.#.##.##.##.###..##..##..#.#.#.#.####..#......#.#.#.#.#.#.##...#####.####...#.#...#.#
.#..###..##.#####.#.##.#..##...##..##...#####.#.####..#...##.....######.#.#...##.#..#######.###.###.
#.#..##.#.#####.#.#.....###.###.#..##.#####....#.###.##.##.#.#..##..#.#....#######.###.#.#.....#.###
....###...#.###.####....###.....##....#####.##.###.###.##.##.##.#..###..######...####.#.#..####..#..
###.....#..####..#.####..#..#...##.##..##.######.####.....#...##....#..#.##.#####..###.##.#.####...#
.##.##.#...#..####...##.##.###...#...#..#.#.#####.....####...#.#.#..#.####...####.#...###.#......###
###.##....#.#.#...#.###....####..##...##.##.##.#..#...####..#..#..##...#####.####.####...##.#..###.#
..####.....##..###.#.#.###.########..#...#.##..#.#.#.......#.##.#..#...####.##.#..#.######..#.#...#.
#.#.##.#.#.##.#....##......##......#######.#..#.##...##..#.#.###...#.#..#..###...#..###.....##.....#
..#.##.#.##.#.##..##.....#.#..#.#..#...##..#..#.#....###.#####....####.####..#####.##.###...#..###.#
#....#.###..#..########.###..#.#.#.##...##.#..##.###..#..#..#.#.##..###...###.#.##..#.##.#..#.#.####
#.......#######......#...#...##.##...###.#....##.#..#....####.#.##.###...#.#####...##.###........##.
.##.####.....###.##......####.###.########..#.####..#.##.#.####.....#...#.##....#######.##..#......#
#.#.##.##....##..##.#.###..#.##.#..#..#.#..##.....###..###.##.##.####.##.#.#.##...####..#.#..##.#.#.
...##.#.#.#...###.#.......#.#.....#.#...##....##.##.##.####...#.#..#..#..#.#.##.#..#.#.#....###..#.#
....#.#.###.#####.##..###..##..#...#.##.#......##.####.#..####.#.##..####.#.#...##..#####..##.#.#...
..###.#.##..#....#..#.#.....##.#####..##....#.#...#.##..##.#.#..#...##.##..##..##....#...#..#..#..##
##.#.##.#...#.###.##.##.##.##..##.##...#..##.#..#######.#..#...#.#.##..#....##.#..####.###........#.
.##.#..#.....#####..##.#.#.#.#..###.#######.###.###....##....#.#.#.###....###.#..#.#....#.#..###...#
...###.#.#.###..#...#..###.######..##.#.#..#...####.#####.##..#..###...#..#..#..###..##.#.#...#.###.
#......#.#..#..##.##.#.##.#.###.#.##.#.#..#....#.##..#..##..##.#.#.#....##.###.###.####.#.#####...##
...#.##..#.######.......#.#.###.....#####....##.#.#.###........#.#.###.#.#########.##.##.#..##..#...
##..###..###....####.##.##..##.###....####..##...####.####..####..###.####..##.#...###.#####.##.##.#
###...##.#.#.#####..#..#####...##.#...#.#.###.#..##..###.##.#.#.....####.##.#..##.###.#...##.##...##
...#.#.##.##..##....#..#.#####.##.###..#.#.#........####.###.##....##....####..#.#....#.#.#.###..#.#
..#.#.#.#.###...#....##..######.##....#.#.##..###..#.#.###..#.##..#.#.###......#..#..#.####..#...##.
.....####.#.....###.#.##.#..##.#..###.#####.#..##...###.#..###..#..##....###.#..##.#..#.##.#..#...##
"""