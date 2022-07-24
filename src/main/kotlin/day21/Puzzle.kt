package day21

import util.combinations

val weapons = listOf(
    Item("Dagger", 8, 4, 0),
    Item("Shortsword", 10, 5, 0),
    Item("Warhammer", 25, 6, 0),
    Item("Longsword", 40, 7, 0),
    Item("Greataxe", 74, 8, 0)
)

val armors = listOf(
    Item("Leather", 13, 0, 1),
    Item("Chainmail", 31, 0, 2),
    Item("Splintmail", 53, 0, 3),
    Item("Bandedmail", 75, 0, 4),
    Item("Platemail", 102, 0, 5)
)

val rings = listOf(
    Item("Damage +1", 25, 1, 0),
    Item("Damage +2", 50, 2, 0),
    Item("Damage +3", 100, 3, 0),
    Item("Defense +1", 20, 0, 1),
    Item("Defense +2", 40, 0, 2),
    Item("Defense +3", 80, 0, 3)
)

fun main() {
    // example
    println(
        fight(8, listOf(Item("Shortsword", 10, 5, 0), Item("Platemail", 102, 0, 5)), 12, 2, 7)
    )

    // get all the kits, sorted by cost
    val allKits = buildKits()
//    println(allKits.size)
//    allKits.forEach { kit -> println("${kit.sumOf { it.cost }} -> $kit") }

    val bossPoints = 109
    val bossDamage = 8
    val bossArmor = 2
    val playerStartingPoints = 100

    // least amount to win
    for (kit in allKits) {
        val playerWins = fight(playerStartingPoints, kit, bossPoints, bossArmor, bossDamage)
        if (playerWins) {
            val cost = kit.sumOf { it.cost }
            println(cost == 111)
//            println("$cost -> $kit")
            break
        }
    }

    // most amount to lose
    for (kit in allKits.asReversed()) {
        val playerWins = fight(playerStartingPoints, kit, bossPoints, bossArmor, bossDamage)
        if (!playerWins) {
            val cost = kit.sumOf { it.cost }
            println(cost == 188)
//            println("$cost -> $kit")
            break
        }
    }
}

fun fight(
    playerStartingPoints: Int,
    playerKit: List<Item>,
    bossStartingPoints: Int,
    bossArmor: Int,
    bossDamage: Int
): Boolean {
    var playerPoints = playerStartingPoints
    val playerArmor = playerKit.sumOf { it.armor }
    val playerDamage = playerKit.sumOf { it.damage }
    val playerDamageDealt = (playerDamage - bossArmor).coerceAtLeast(1)

    var bossPoints = bossStartingPoints
    val bossDamageDealt = (bossDamage - playerArmor).coerceAtLeast(1)

    while (true) {
        bossPoints -= playerDamageDealt
//        println("The player deals $playerDamageDealt damage; the boss goes down to $bossPoints hit points.")
        if (bossPoints <= 0) return true

        playerPoints -= bossDamageDealt
//        println("The boss deals $bossDamageDealt damage; the player goes down to $playerPoints hit points.")
        if (playerPoints <= 0) return false
    }
}

fun buildKits(): List<List<Item>> {
    val allKits = mutableListOf<List<Item>>()

    // build each of the legal combinations
    // there's surely a better way than this that I'm not aware of
    // weapons = 1
    // armors = 0 .. 1
    // rings = 0 .. 2

    // 1 weapon, 0 armors, 0 rings
    allKits.addAll(weapons.map { listOf(it) }.toList())

    // 1 weapon, 1 armor, 0 rings
    weapons.forEach { weapon ->
        allKits.addAll(armors.map { armor ->
            listOf(weapon, armor)
        }.toList())
    }

    // 1 weapon, 0 armor, 1 rings
    weapons.forEach { weapon ->
        allKits.addAll(rings.map { ring ->
            listOf(weapon, ring)
        }.toList())
    }

    // 1 weapon, 0 armor, 2 rings
    weapons.forEach { weapon ->
        allKits.addAll(combinations(rings, 2).map { twoRings ->
            val w = mutableListOf(weapon)
            w.addAll(twoRings)
            w
        }.toList())
    }

    // 1 weapon, 1 armor, 1 ring
    weapons.forEach { weapon ->
        armors.forEach { armor ->
            allKits.addAll(rings.map { ring ->
                listOf(weapon, armor, ring)
            })
        }
    }

    // 1 weapon, 1 armor, 2 rings
    weapons.forEach { weapon ->
        armors.forEach { armor ->
            allKits.addAll(combinations(rings, 2).map { twoRings ->
                val w = mutableListOf(weapon, armor)
                w.addAll(twoRings)
                w
            }.toList())
        }
    }


    allKits.sortBy { kit -> kit.sumOf { it.cost } }
    return allKits
}

data class Item(val name: String, val cost: Int, val damage: Int, val armor: Int)

