package day04

import java.security.MessageDigest

val md5: MessageDigest = MessageDigest.getInstance("MD5")

fun main() {
    println(findHashStartingWith(EXAMPLE_1, "00000") == 609043)
    println(findHashStartingWith(EXAMPLE_2, "00000") == 1048970)
    println(findHashStartingWith(INPUT, "00000") == 254575)

    println(findHashStartingWith(INPUT, "000000") == 1038736)
}

fun findHashStartingWith(secretKey: String, target: String): Int? {
    return (1..Int.MAX_VALUE).find { i ->
        md5.digest("$secretKey$i".toByteArray())
            .take(3)
            .joinToString("") { "%02x".format(it) }
//            .also { if (i % 100000 == 0) println("$i -> $it") }
            .startsWith(target)
    }
}

const val EXAMPLE_1 = "abcdef"
const val EXAMPLE_2 = "pqrstuv"
const val INPUT = "bgvyzdsv"