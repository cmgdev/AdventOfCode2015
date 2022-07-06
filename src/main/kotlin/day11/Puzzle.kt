package day11

const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"
const val INVALID_CHARS = "iol"
val VALID_CHARS = ALPHABET.toList().minus(INVALID_CHARS).joinToString("")

fun main() {
    println(!isValidPassword("abcdefgh"))
    println(!isValidPassword("abcdefgg"))
    println(!isValidPassword("abcdfffa"))
    println(isValidPassword("abcdffaa"))

    println(nextValidPassword("abcdefgz") == "abcdffaa")
    println(nextValidPassword("ghjaaaaa") == "ghjaabcc")
    println(nextValidPassword("ghijklmn") == "ghjaabcc")

    println(nextValidPassword("cqjxjnds") == "cqjxxyzz")
    println(nextValidPassword("cqjxxyzz") == "cqkaabcc")
}

fun nextValidPassword(password: String): String {
    var nextPassword = password
    do {
        nextPassword = nextPassword(nextPassword)
    } while (!isValidPassword(nextPassword))
    return nextPassword
}

private fun nextPassword(password: String): String {
    var nextPassword = password

    val invCharIdx = password.indexOfAny(INVALID_CHARS.toCharArray())
    if (invCharIdx >= 0) {
        nextPassword = password.substring(0, invCharIdx) + nextChar(password[invCharIdx])
        (invCharIdx + 1 until password.length).forEach { _ -> nextPassword += "a" }
        return nextPassword
    }

    var idx = password.length - 1
    do {
        val currentChar = nextPassword[idx]
        val nextValidChar = nextChar(currentChar)

        nextPassword = nextPassword.replaceRange(idx, idx + 1, nextValidChar.toString())
        idx--
    } while (nextValidChar == 'a' && idx >= 0)
    return nextPassword
}

fun nextChar(nextChar: Char): Char {
    return VALID_CHARS[((VALID_CHARS.indexOf(nextChar) + 1) % VALID_CHARS.length)]
}

fun isValidPassword(password: String): Boolean {
    var validPairs = false
    val pairs = password.windowed(2).mapIndexedNotNull { i, s ->
        if (s[0] == s[1]) Pair(i, s) else null
    }.toMap()

    if (pairs.size >= 2) {
        validPairs = pairs.any { p -> pairs.any { p.key + 2 <= it.key && p.value != it.value } }
    }

    return validPairs && password.none { INVALID_CHARS.contains(it) } &&
            password.windowed(3).any {
                val firstIdx = ALPHABET.indexOf(it[0])
                ALPHABET.indexOf(it[1]) == firstIdx + 1 && ALPHABET.indexOf(it[2]) == firstIdx + 2
            }
}