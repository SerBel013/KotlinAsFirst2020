package ru.spbstu.kfirst.task4

import org.junit.Test

import org.junit.Assert.*

class ListTest {
    @Test
    fun factorize() {
        assertEquals(listOf(2), factorize(2))
        assertEquals(listOf(3, 5, 5), factorize(75))
        assertEquals(listOf(2, 3, 3, 19), factorize(342))
    }

    @Test
    fun factorizeToString() {
        assertEquals("2", factorizeToString(2))
        assertEquals("3*5*5", factorizeToString(75))
        assertEquals("2*3*3*19", factorizeToString(342))
    }

    @Test
    fun convert() {
        assertEquals(listOf(1), convert(1, 2))
        assertEquals(listOf(1, 2, 1, 0), convert(100, 4))
        assertEquals(listOf(1, 3, 12), convert(250, 14))
        assertEquals(listOf(2, 14, 12), convert(1000, 19))
    }

    @Test
    fun convertToString() {
        assertEquals("1", convertToString(1, 2))
        assertEquals("1210", convertToString(100, 4))
        assertEquals("13c", convertToString(250, 14))
        assertEquals("2ec", convertToString(1000, 19))
        assertEquals("z", convertToString(35, 36))
    }
}