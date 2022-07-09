package util

/*
https://www.rosettacode.org/wiki/Permutations#Kotlin
 */
fun <T> permute(input: List<T>): List<List<T>> {
    if (input.size == 1) return listOf(input)
    val perms = mutableListOf<List<T>>()
    val toInsert = input[0]
    for (perm in permute(input.drop(1))) {
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, toInsert)
            perms.add(newPerm)
        }
    }
    return perms
}

//https://www.reddit.com/r/Kotlin/comments/isg16h/what_is_the_fastest_way_combination_in_kotlin/g5fvsw3/?utm_source=reddit&utm_medium=web2x&context=3
fun <T> combinations(input: List<T>, length: Int): Sequence<List<T>> =
    sequence {
        val n = input.size
        if (length > n) return@sequence
        val indices = IntArray(length) { it }
        while (true) {
            yield(indices.map { input[it] })
            var i = length
            do {
                i--
                if (i == -1) return@sequence
            } while (indices[i] == i + n - length)
            indices[i]++
            for (j in i + 1 until length) indices[j] = indices[j - 1] + 1
        }
    }