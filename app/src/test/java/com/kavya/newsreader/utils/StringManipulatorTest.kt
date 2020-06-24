package com.kavya.newsreader.utils

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by Kavya P S on 18/06/20.
 */
internal class DateStringManipulatorTest {

    @Test
    fun emptyStringCase() {
        assertEquals("", DateStringManipulator.dateTimezoneRemover(""))
    }

    @Test
    fun validStringCase() {
        assertEquals(
            "2020-06-18",
            DateStringManipulator.dateTimezoneRemover("2020-06-18T13:48:00Z")
        )
    }

    @Test
    fun whiteSpacesCase() {
        assertEquals(
            "2020-06-18",
            DateStringManipulator.dateTimezoneRemover("     2020-06-18T13:48:00Z    ")
        )
        assertEquals(
            "2020-06-18",
            DateStringManipulator.dateTimezoneRemover("     2020-06-18T13:48:00Z")
        )
        assertEquals(
            "2020-06-18",
            DateStringManipulator.dateTimezoneRemover("2020-06-18T13:48:00Z   ")
        )
    }


    @Test
    fun invalidDateStringCase() {
        assertEquals("", DateStringManipulator.dateTimezoneRemover("2020-   06-18T13:48:00Z    "))
        assertEquals("", DateStringManipulator.dateTimezoneRemover("2020-06    -18T13:48:00Z"))
        assertEquals("", DateStringManipulator.dateTimezoneRemover("abcDtT"))
    }

}