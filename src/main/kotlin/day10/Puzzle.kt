package day10

fun main() {
    println(transformByRegex("211") == "1221")
    println(transformByRegex("1", 5) == "312211")

    val transformed = transformByRegex("1321131112", 40)
    println(transformed.length == 492982)
    println(transformByRegex(transformed, 10).length == 6989950)
}

fun transformByRegex(input: String, times: Int = 1): String {
    val regex = Regex("(\\d)\\1*")

    var s = input
    (1..times).forEach { _ ->
        s = regex.findAll(s).map {
            val group = it.groupValues[0]
            group.length.toString() + it.groupValues[0][0]
        }.joinToString("")
    }
    return s
}

fun transform(input: String, times: Int = 1): String {
    fun transform(input: String): String {
        var output = ""
        var lastChar = ' '
        var count = 0

        input.forEach {
            if (it != lastChar) {
                if (count > 0) {
                    output += count.toString() + lastChar
                }
                lastChar = it
                count = 1
            } else {
                count++
            }
        }
        output += count.toString() + lastChar
        return output
    }

    var transformed = input
    (1..times).forEach { _ ->
        transformed = transform(transformed)
    }
    return transformed
}