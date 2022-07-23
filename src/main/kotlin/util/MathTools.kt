package util

fun getFactors(num: Int): List<Int> {
    val factors = (1..num / 2).filter { num % it == 0 }.toMutableList()
    factors.add(num)
    return factors
}

fun gcd(nums: Collection<Int>): Int {
    if (nums.isEmpty()) return 0
    if (nums.size == 1) return nums.first()

    var aa = nums.first()
    nums.drop(1).forEach {
        aa = gcd(aa, it)
    }
    return aa
}

fun gcd(a: Int, b: Int): Int {
    var aa = a
    var bb = b
    while (aa != bb) {
        if (aa > bb) {
            aa -= bb
        } else {
            bb -= aa
        }
    }
    return aa
}