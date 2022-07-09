package day15

import kotlin.math.max

fun main() {
    val butterscotch = Ingredient(-1, -2, 6, 3, 8)
    val cinnamon = Ingredient(2, 3, -2, -1, 3)

    println(maxOf2(butterscotch, cinnamon) == 62842880)
    println(maxOf2(butterscotch, cinnamon, 500) == 57600000)

    val sugar = Ingredient(3, 0, 0, -3, 2)
    val sprinkles = Ingredient(-3, 3, 0, 0, 9)
    val candy = Ingredient(-1, 0, 4, 0, 1)
    val chocolate = Ingredient(0, 0, -2, 2, 8)
    println(maxOf4(sugar, sprinkles, candy, chocolate) == 222870)
    println(maxOf4(sugar, sprinkles, candy, chocolate, 500) == 117936)
}

private fun maxOf4(
    a: Ingredient, b: Ingredient, c: Ingredient, d: Ingredient, constrainCalories: Int = 0
): Int {
    var maxScore = 0
    (0..100).forEach { i ->
        (0..100 - i).forEach { j ->
            (0..100 - i - j).forEach { k ->
                val l = 100 - i - j - k
                val score = Recipe(listOf(Pair(a, i), Pair(b, j), Pair(c, k), Pair(d, l))).getScore(constrainCalories)
                maxScore = max(maxScore, score)
            }
        }
    }
    return maxScore
}

private fun maxOf2(a: Ingredient, b: Ingredient, constrainCalories: Int = 0): Int {
    var maxScore = 0
    (0..100).forEach { i ->
        val j = 100 - i
        val score = Recipe(listOf(Pair(a, i), Pair(b, j))).getScore(constrainCalories)
        maxScore = max(maxScore, score)
    }
    return maxScore
}

data class Recipe(val ingredients: List<Pair<Ingredient, Int>>) {
    fun getScore(constrainCalories: Int = 0): Int {
        if (constrainCalories > 0 && ingredients.sumOf { it.first.calories * it.second } != constrainCalories) {
            return 0
        }
        val capacity = ingredients.sumOf { it.first.capacity * it.second }.coerceAtLeast(0)
        val durability = ingredients.sumOf { it.first.durability * it.second }.coerceAtLeast(0)
        val flavor = ingredients.sumOf { it.first.flavor * it.second }.coerceAtLeast(0)
        val texture = ingredients.sumOf { it.first.texture * it.second }.coerceAtLeast(0)
        return capacity * durability * flavor * texture
    }
}

data class Ingredient(
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
)

/*
Butterscotch: capacity -1, durability -2, flavor  6, texture  3, calories 8 --> 44
Cinnamon:     capacity  2, durability  3, flavor -2, texture -1, calories 3 --> 56
 */

/*
Sugar:     capacity  3, durability 0, flavor  0, texture -3, calories 2
Sprinkles: capacity -3, durability 3, flavor  0, texture  0, calories 9
Candy:     capacity -1, durability 0, flavor  4, texture  0, calories 1
Chocolate: capacity  0, durability 0, flavor -2, texture  2, calories 8
 */