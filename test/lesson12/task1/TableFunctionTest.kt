package lesson12.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

internal class TableFunctionTest {

    @Test
    @Tag("6")
    fun add() {
        val function = TableFunction()
        function.add(1.0, 2.0)
        function.add(3.0, 4.0)
        assertTrue(function.add(5.0, 6.0))
        assertFalse(function.add(5.0, 7.0))
        assertEquals(3, function.size)
        val newFunction = TableFunction()
        newFunction.add(1.9, 2.9)
        assertTrue(newFunction.add(6.0, 1.1))
        assertFalse(newFunction.add(1.9, 7.7))
        assertEquals(2, newFunction.size)
    }

    @Test
    @Tag("6")
    fun remove() {
        val function = TableFunction()
        function.add(1.0, 2.0)
        function.add(3.0, 4.0)
        assertTrue(function.remove(1.0))
        assertFalse(function.remove(1.0))
        assertEquals(1, function.size)
        val newFunction = TableFunction()
        newFunction.add(1.9, 2.9)
        assertTrue(newFunction.remove(1.9))
        assertFalse(newFunction.remove(1.9))
        assertEquals(0, newFunction.size)
    }

    @Test
    @Tag("6")
    fun getPairs() {
        val function = TableFunction()
        function.add(1.0, 2.0)
        val pairs = function.getPairs()
        assertEquals(1, pairs.size)
        assertEquals(1.0 to 2.0, pairs.single())
        val newFunction = TableFunction()
        newFunction.add(1.9, 2.9)
        val pair = newFunction.getPairs()
        assertEquals(1.9 to 2.9, pair.single())
    }

    @Test
    @Tag("6")
    fun findPair() {
        val function = TableFunction()
        function.add(1.0, 2.0)
        function.add(3.0, 4.0)
        function.add(5.0, 6.0)
        assertEquals(5.0 to 6.0, function.findPair(5.75))
        assertEquals(1.0 to 2.0, function.findPair(1.5))
        val newFunction = TableFunction()
        newFunction.add(1.9, 2.9)
        newFunction.add(10000.0, 3.9)
        assertEquals(10000.0 to 3.9, newFunction.findPair(8976.0))
    }

    @Test
    @Tag("10")
    fun getValue() {
        val function = TableFunction()
        val newFunction = TableFunction()
        try {
            function.getValue(0.0)
        } catch (ex: IllegalArgumentException) {
            // pass
        } catch (ex: NotImplementedError) {
            throw ex
        } catch (ex: IllegalStateException) {
        }
        function.add(1.0, 2.0)
        assertEquals(2.0, function.getValue(1.5))
        function.add(3.0, 4.0)
        function.add(5.0, 6.0)
        assertEquals(5.0, function.getValue(4.0), 1e-10)
        assertEquals(0.0, function.getValue(-1.0), 1e-10)
        newFunction.add(1.0, 2.0)
        newFunction.add(2.0, 2.0)
        assertEquals(2.0, newFunction.getValue(1.0), 1e-10)
        assertEquals(2.0, newFunction.getValue(0.0), 1e-10)
    }

    @Test
    @Tag("6")
    fun equals() {
        val f1 = TableFunction()
        f1.add(1.0, 2.0)
        f1.add(3.0, 4.0)
        val f2 = TableFunction()
        f2.add(3.0, 4.0)
        f2.add(1.0, 2.0)
        assertEquals(f1, f2)
        val q1 = TableFunction()
        q1.add(11.1, 11.1)
        val q2 = TableFunction()
        q2.add(11.1, 11.1)
        assertEquals(q1, q2)
    }
}