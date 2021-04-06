package com.ceylonsolutions.postmate

import com.ceylonsolutions.postmate.util.Helper
import junit.framework.TestCase.assertTrue
import org.junit.Test

class UnitTest {

    @Test
    fun toTitleCaseEveryWord_Success() {
        var actual : String? = Helper.toTitleCaseEveryWord("Post mate app test")
        val expected : String = "Post Mate App Test"
        println("$expected = $actual")
        assertTrue(expected == actual)
    }

    @Test
    fun generateMD5Hash_Success() {
        var actual : String? = Helper.generateMD5Hash("rajitha@gmail.com")
        val expected : String = "38dabc425a8ac9b328043432e9135268"
        println("$expected = $actual")
        assertTrue(expected == actual)
    }


    @Test
    fun capitalizeFirstLetter_Success() {
        var actual : String? = Helper.capitalizeFirstLetter("post mate app test")
        val expected : String = "Post mate app test"
        assertTrue(expected == actual)
    }

    @Test
    fun capitalizeFirstLetter_Error() {
        var actual : String? = Helper.capitalizeFirstLetter(null)
        val expected : String = "Post mate app test"
        println("$expected = $actual")
        assertTrue(expected == actual)
    }
}