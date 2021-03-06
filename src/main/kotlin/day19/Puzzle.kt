package day19

fun main() {
    val exampleReplacements = EXAMPLE.lines().map { it.trim() }.filterNot { it.isEmpty() }
    println(getMolecules("HOH", exampleReplacements).size == 4)
    println(getMolecules("HOHOHO", exampleReplacements).size == 7)

    val replacements = INPUT.lines().map { it.trim() }.filterNot { it.isEmpty() }
    val input =
        "CRnSiRnCaPTiMgYCaPTiRnFArSiThFArCaSiThSiThPBCaCaSiRnSiRnTiTiMgArPBCaPMgYPTiRnFArFArCaSiRnBPMgArPRnCaPTiRnFArCaSiThCaCaFArPBCaCaPTiTiRnFArCaSiRnSiAlYSiThRnFArArCaSiRnBFArCaCaSiRnSiThCaCaCaFYCaPTiBCaSiThCaSiThPMgArSiRnCaPBFYCaCaFArCaCaCaCaSiThCaSiRnPRnFArPBSiThPRnFArSiRnMgArCaFYFArCaSiRnSiAlArTiTiTiTiTiTiTiRnPMgArPTiTiTiBSiRnSiAlArTiTiRnPMgArCaFYBPBPTiRnSiRnMgArSiThCaFArCaSiThFArPRnFArCaSiRnTiBSiThSiRnSiAlYCaFArPRnFArSiThCaFArCaCaSiThCaCaCaSiRnPRnCaFArFYPMgArCaPBCaPBSiRnFYPBCaFArCaSiAl"
    println(getMolecules(input, replacements).size == 518)

    println(findE(input, replacements) == 200)
}

fun getMolecules(input: String, replacements: List<String>): Set<String> {
    val formulas = replacements.map { it.split(" ") }.map { Pair(it[0], it[2]) }.toList()

    val molecules = mutableSetOf<String>()
    formulas.forEach { formula ->
        Regex(formula.first).findAll(input).forEach { matches ->
            molecules.addAll(matches.groups.take(1).map {
                input.replaceRange(it?.range!!, formula.second)
            })
        }
    }
    return molecules
}

fun findE(input: String, replacements: List<String>): Int {
    val formulas = replacements.map { it.split(" ") }.map { Pair(it[0], it[2]) }.toList()

    var toE = input
    var count = 0
    while (toE != "e") {
        formulas.forEach {
            if (toE.contains(it.second)) {
                val startIndex = toE.lastIndexOf(it.second)
                toE = toE.replaceRange(startIndex, startIndex + it.second.length, it.first)
                count++
            }
        }
    }
    return count
}

const val EXAMPLE = """
e => H
e => O
H => HO
H => OH
O => HH
"""

const val INPUT = """
Al => ThF
Al => ThRnFAr
B => BCa
B => TiB
B => TiRnFAr
Ca => CaCa
Ca => PB
Ca => PRnFAr
Ca => SiRnFYFAr
Ca => SiRnMgAr
Ca => SiTh
F => CaF
F => PMg
F => SiAl
H => CRnAlAr
H => CRnFYFYFAr
H => CRnFYMgAr
H => CRnMgYFAr
H => HCa
H => NRnFYFAr
H => NRnMgAr
H => NTh
H => OB
H => ORnFAr
Mg => BF
Mg => TiMg
N => CRnFAr
N => HSi
O => CRnFYFAr
O => CRnMgAr
O => HP
O => NRnFAr
O => OTi
P => CaP
P => PTi
P => SiRnFAr
Si => CaSi
Th => ThCa
Ti => BP
Ti => TiTi
e => HF
e => NAl
e => OMg
"""