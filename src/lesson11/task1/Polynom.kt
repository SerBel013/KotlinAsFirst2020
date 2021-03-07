@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
fun check(list: List<Double>) = if (list.isEmpty()) listOf(0.0) else list
class Polynom(vararg coeffs: Double) {
    val coeffsList = check(coeffs.toList().dropWhile { it == 0.0 })

    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = coeffsList.reversed()[i]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double = coeffsList.fold(0.0) { sum, coeff -> sum * x + coeff }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int = coeffsList.size - 1

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom {
        val length = if (other.coeffsList.size > coeffsList.size) other.coeffsList.size else coeffsList.size
        val list = MutableList(length) { 0.0 }
        for (i in coeffsList.indices) list[list.size - i - 1] = coeffsList[coeffsList.size - i - 1]
        for (i in other.coeffsList.indices) list[list.size - i - 1] += other.coeffsList[other.coeffsList.size - i - 1]
        return Polynom(*list.toDoubleArray())
    }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom = Polynom(*coeffsList.map { -it }.toDoubleArray())

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom = this.plus(other.unaryMinus())

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        val list = MutableList(other.coeffsList.size + coeffsList.size) { 0.0 }
        for (i in coeffsList.indices)
            for (j in other.coeffsList.indices)
                list[list.size - i - j - 1] += coeffsList[coeffsList.size - i - 1] * other.coeffsList[other.coeffsList.size - j - 1]
        return Polynom(*list.toDoubleArray())
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     *
     */
    private fun division(other: Polynom): Pair<Polynom, Polynom> {
        var rem = Polynom(*coeffsList.toDoubleArray())
        var x = Polynom(0.0)
        while (rem.degree() >= other.degree() && rem != Polynom(0.0)) {
            val list = MutableList(rem.coeffsList.size - other.coeffsList.size + 1) { 0.0 }
            list[0] = rem.coeff(rem.degree()) / other.coeff(other.degree())
            val listPolynom = Polynom(*list.toDoubleArray())
            x = x.plus(listPolynom)
            rem = rem.minus(listPolynom.times(other))
        }
        return Pair(x, rem)
    }

    operator fun div(other: Polynom): Polynom = division(other).first

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom = division(other).second

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = other is Polynom && coeffsList == other.coeffsList

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int = coeffsList.hashCode()
}
