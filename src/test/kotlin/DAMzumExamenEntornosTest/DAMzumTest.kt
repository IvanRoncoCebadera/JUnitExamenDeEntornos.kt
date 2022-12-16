package DAMzumExamenEntornosTest

import modelsExamenEntornos.DAMzum
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.lang.IllegalArgumentException

class DAMzumTest {

    val DAMzum =  DAMzum("0000000000000000", "2022-12-13", 1)

    @Test
    fun esValidoCardNumberTest(){
        val cardNumber1 = "0000000000000000"
        val cardNumber2 = "9999999999999999"
        assertAll(
            { assertTrue(DAMzum.esValidoCardNumber(cardNumber1)) },
            { assertTrue(DAMzum.esValidoCardNumber(cardNumber2)) }
        )
    }

    @Test
    fun noEsValidoCardNumberTest(){
        val cardNumber1 = "00000"
        val mensaje1: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidoCardNumber(cardNumber1) }
        val cardNumber2 = "999999999999999999999"
        val mensaje2: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidoCardNumber(cardNumber2) }
        val cardNumber3 = null
        val mensaje3: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidoCardNumber(cardNumber3) }
        val cardNumber4 = "hola"
        val mensaje4: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidoCardNumber(cardNumber4) }
        val cardNumber5 = "-1111111111111111"
        val mensaje5: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidoCardNumber(cardNumber5) }
        assertAll(
            { assertEquals("El cardNumber no puede ser ni menor ni mayor de 16 dígitos, vuelve a probar:", mensaje1.message) },
            { assertEquals("El cardNumber no puede ser ni menor ni mayor de 16 dígitos, vuelve a probar:", mensaje2.message) },
            { assertEquals("El cardNumber no puede ser nulo, vuelve a probar:", mensaje3.message) },
            { assertEquals("El cardNumber introducido no es un número, vuelve a probar:", mensaje4.message) },
            { assertEquals("El cardNumber no puede ser negativo, vuelve a probar", mensaje5.message) }
        )
    }

    @Test
    fun esValidaFechaTest(){
        //Me acabo de percatar que día 00 y mes 00 no tiene sentido y que por ende como lo tengo en el papel del examen está mal
        val fecha1 = "2020-01-01"
        val fecha2 = "2025-12-31"
        assertAll(
            { assertTrue(DAMzum.esValidaFecha(fecha1)) },
            { assertTrue(DAMzum.esValidaFecha(fecha2)) }
        )
    }

    @Test
    fun noEsValidaFechaTest(){
        //Me acabo de percatar que sería mejor testear con día 00 y mes 00, en vez de -01 y -01 como lo tengo en el papel del examen
        val fecha1 = "2019-00-00"
        val mensaje1: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidaFecha(fecha1) }
        val fecha2 = "2026-13-32"
        val mensaje2: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidaFecha(fecha2) }
        val fecha3 = null
        val mensaje3: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidaFecha(fecha3) }
        val fecha4 = "hola"
        val mensaje4: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidaFecha(fecha4) }
        assertAll(
            { assertEquals("La fecha introducida no puede tener un año menor que 2020, ni un mes menor que 01, ni un día menor que 01, vuelve a probar:", mensaje1.message) },
            { assertEquals("La fecha introducida no puede tener un año mayor que 2025, ni un mes mayor que 12, ni un día mayor que 31, vuelve a probar:", mensaje2.message) },
            { assertEquals("La fecha no puede ser nula, vuelve a probar:", mensaje3.message) },
            { assertEquals("La fecha introducida no cumple con el patrón esperado, vuelve a probar:", mensaje4.message) }
            )
    }

    @Test
    fun esValidCSVTest(){
        //Considero que hay dos maneras de introducir un CSV para un nuevo usuario, o bien lo calculas a traves del cardNumber, o bien lo introduces tu a mano, por eso realizo estos test de que es aceptable o no a la hora de introducirlo a mano
        val CSV1 = "0"
        val CSV2 = "499"
        assertAll(
            { assertTrue(DAMzum.esValidoCSV(CSV1)) },
            { assertTrue(DAMzum.esValidoCSV(CSV2)) }
        )
    }

    @Test
    fun noEsValidoCSVTest(){
        //Considero que hay dos maneras de introducir un CSV para un nuevo usuario, o bien lo calculas a traves del cardNumber, o bien lo introduces tu a mano, por eso realizo estos test de que es aceptable o no a la hora de introducirlo a mano
        val CSV1 = "-1"
        val mensaje1: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidoCSV(CSV1) }
        val CSV2 = "500"
        val mensaje2: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidoCSV(CSV2) }
        val CSV3 = null
        val mensaje3: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidoCSV(CSV3) }
        val CSV4 = "hola"
        val mensaje4: IllegalArgumentException = Assertions.assertThrows(IllegalArgumentException::class.java) { DAMzum.esValidoCSV(CSV4) }
        assertAll(
            { assertEquals("El CSV no puede ser ni menor que 0 ni mayor que 499, vuelve a probar:", mensaje1.message) },
            { assertEquals("El CSV no puede ser ni menor que 0 ni mayor que 499, vuelve a probar:", mensaje2.message) },
            { assertEquals("El CSV no puede ser nulo, vuelve a probar:", mensaje3.message) },
            { assertEquals("El CSV introducida no es un número, vuelve a probar:", mensaje4.message) }
        )
    }

    @Test
    fun calcularCSVTest(){
        //Aquí testeo la función que serviría para calcular automáticamente el CSV con respecto al numberCard del usuario
        val cardNumber1 = "0000000000000000"
        val cardNumber2 = "0000000000000500"
        assertAll(
            { assertEquals(0, DAMzum.calcularCSV(cardNumber1)) },
            { assertEquals(1, DAMzum.calcularCSV(cardNumber2)) }
        )
    }
}