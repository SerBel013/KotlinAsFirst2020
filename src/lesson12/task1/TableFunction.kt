@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

import kotlin.math.abs
import kotlin.time.seconds

/**
 * Класс "табличная функция".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса хранит таблицу значений функции (y) от одного аргумента (x).
 * В таблицу можно добавлять и удалять пары (x, y),
 * найти в ней ближайшую пару (x, y) по заданному x,
 * найти (интерполяцией или экстраполяцией) значение y по заданному x.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class TableFunction {

    private val table = mutableListOf<Pair<Double, Double>>()

    /**
     * Количество пар в таблице
     */
    val size: Int get() = table.size


    /**
     * Добавить новую пару.
     * Вернуть true, если пары с заданным x ещё нет,
     * или false, если она уже есть (в этом случае перезаписать значение y)
     */
    fun add(x: Double, y: Double): Boolean {
        val pair = table.find { it.first == x }
        return if (pair != null) {
            table.remove(pair)
            table.add(Pair(x, y))
            false
        } else {
            table.add(Pair(x, y))
            true
        }
    }

    /**
     * Удалить пару с заданным значением x.
     * Вернуть true, если пара была удалена.
     */
    fun remove(x: Double): Boolean {
        val pair = table.find { it.first == x }
        return if (pair != null) {
            table.remove(pair)
            true
        } else false
    }

    /**
     * Вернуть коллекцию из всех пар в таблице
     */
    fun getPairs(): Collection<Pair<Double, Double>> = table

    /**
     * Вернуть пару, ближайшую к заданному x.
     * Если существует две ближайшие пары, вернуть пару с меньшим значением x.
     * Если таблица пуста, бросить IllegalStateException.
     */
    fun findPair(x: Double): Pair<Double, Double>? {
        if (table.isEmpty()) throw IllegalStateException()
        var closestPair = Pair(Double.MAX_VALUE, Double.MAX_VALUE) // ближайшая пара
        for (i in table) {
            if (abs(x - i.first) < abs(x - closestPair.first))
                closestPair = i
            else if (abs(x - i.first) == abs(x - closestPair.first) && i.first < closestPair.first)
                closestPair = i
        }
        return closestPair
    }

    /**
     * Вернуть значение y по заданному x.
     * Если в таблице есть пара с заданным x, взять значение y из неё.
     * Если в таблице есть всего одна пара, взять значение y из неё.
     * Если таблица пуста, бросить IllegalStateException.
     * Если существуют две пары, такие, что x1 < x < x2, использовать интерполяцию.
     * Если их нет, но существуют две пары, такие, что x1 < x2 < x или x > x2 > x1, использовать экстраполяцию.
     */
    fun getValue(x: Double): Double {
        if (size == 0) throw IllegalStateException()
        if (size == 1) return table.first().second
        var rightPair = Pair(Double.POSITIVE_INFINITY, 0.0)
        var rightPair2 = Pair(Double.POSITIVE_INFINITY, 0.0)
        var leftPair = Pair(Double.NEGATIVE_INFINITY, 0.0)
        var leftPair2 = Pair(Double.NEGATIVE_INFINITY, 0.0)
        for ((first, second) in table) {
            if (first == x) return second
            if (first > x && first < rightPair.first) {
                val i = rightPair
                rightPair = Pair(first, second)
                rightPair2 = i
            } else if (first > x && first < rightPair2.first) rightPair2 = Pair(first, second)
            if (first < x && first > leftPair.first) {
                val j = leftPair
                leftPair = Pair(first, second)
                leftPair2 = j
            } else if (first < x && first > leftPair2.first) leftPair2 = Pair(first, second)
        }
        if (rightPair.first != Double.POSITIVE_INFINITY && leftPair.first != Double.NEGATIVE_INFINITY)
            return leftPair.second + (rightPair.second - leftPair.second) * (x - leftPair.first) / (rightPair.first - leftPair.first)
        return if (rightPair.first == Double.POSITIVE_INFINITY)
            leftPair2.second + (leftPair.second - leftPair2.second) * (x - leftPair2.first) / (leftPair.first - leftPair2.first)
        else rightPair.second + (rightPair2.second - rightPair.second) * (x - rightPair.first) / (rightPair2.first - rightPair.first)
    }

    /**
     * Таблицы равны, если в них одинаковое количество пар,
     * и любая пара из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean {
        if (other is TableFunction && other.size == size) {
            for (i in other.getPairs())
                if (i !in table) return false
            return true
        }
        return false
    }
}