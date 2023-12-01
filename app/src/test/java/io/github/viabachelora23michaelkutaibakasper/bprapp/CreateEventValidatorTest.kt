package io.github.viabachelora23michaelkutaibakasper.bprapp

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.validators.isInvalidAddress
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.validators.isInvalidCategory
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.validators.isInvalidKeywords
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.validators.isInvalidStartAndEndDate
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.validators.isInvalidTitle
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CreateEventValidatorTest {
    //create unit test for isValidTitle function
    //val  viewModel = CreateEventViewModel()
    @Test
    fun isInvalidTitle_expectedValue_returnsTrue() {
        assertEquals(false, isInvalidTitle("run event"))
    }

    @Test
    fun isInvalidTitle_emptyString_returnsFalse() {
        assertEquals(true, isInvalidTitle(""))
    }

    @Test
    fun isInvalidAddress_expectedValue_returnsTrue() {
        assertEquals(false, isInvalidAddress("Hospitalsgade 86, 8701 Horsens"))
    }

    @Test
    fun isInvalidAddress_emptyString_returnsFalse() {
        assertEquals(true, isInvalidAddress(""))
    }

    @Test
    fun isInvalidCategory_expectedValue_returnsTrue() {
        assertEquals(false, isInvalidCategory("Music"))
    }

    @Test
    fun isInvalidCategory_defaultValue_returnsFalse() {
        assertEquals(true, isInvalidCategory("Choose Category"))
    }

    @Test
    fun isInvalidCategory_emptyString_returnsFalse() {
        assertEquals(true, isInvalidCategory(""))
    }

    @Test
    fun isInvalidKeywords_expectedValueMin_returnsTrue() {
        assertEquals(false, isInvalidKeywords(listOf("Music", "Dance", "Party")))
    }

    @Test
    fun isInvalidKeywords_ExpectedValueMax_returnsFalse() {
        assertEquals(false, isInvalidKeywords(listOf("Music", "Dance", "Party", "Food", "Drinks")))
    }

    @Test
    fun isInvalidKeywords_emptyString_returnsFalse() {
        assertEquals(true, isInvalidKeywords(listOf()))
    }

    @Test
    fun isInvalidKeywords_twoItems_returnsFalse2() {
        assertEquals(true, isInvalidKeywords(listOf("Music", "Dance")))
    }

    @Test
    fun isInvalidKeywords_sixItems_returnsFalse3() {
        assertEquals(
            true,
            isInvalidKeywords(listOf("Music", "Dance", "Party", "Food", "Drinks", "Games"))
        )
    }

    @Test
    fun isInvalidStartAndEndDate_expectedValue_returnsFalse() {
        assertEquals(
            false,
            isInvalidStartAndEndDate(LocalDateTime.now(), LocalDateTime.now().plusHours(2))
        )
    }

    @Test
    fun isInvalidStartAndEndDate_sameValue_returnsTrue() {
        assertEquals(true, isInvalidStartAndEndDate(LocalDateTime.now(), LocalDateTime.now()))
    }

    @Test
    fun isInvalidStartAndEndDate_endDateBeforeStartDate_returnsTrue() {
        assertEquals(
            true,
            isInvalidStartAndEndDate(LocalDateTime.now(), LocalDateTime.now().minusHours(2))
        )
    }
}