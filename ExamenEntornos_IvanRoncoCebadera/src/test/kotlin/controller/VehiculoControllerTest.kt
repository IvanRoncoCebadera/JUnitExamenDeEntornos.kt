package controller

import enums.TipoVehiculo
import exceptions.VehiculoBadRequestException
import exceptions.VehiculoListEmptyException
import exceptions.VehiculoNotFoundException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import models.Automovil
import models.Camion
import models.Motocicleta
import models.Vehiculo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import repository.CrudRepository
import java.time.LocalDate

internal class VehiculoControllerTest {

    @MockK
    private lateinit var repository: CrudRepository<Vehiculo, String>

    @InjectMockKs
    private lateinit var controller: VehiculoController

    init{
        MockKAnnotations.init(this)
    }

    val idNoExistente = "ABCD-123"

    val misVehiculos = listOf<Vehiculo>(
        Automovil("AAAA-111", "Toyota", TipoVehiculo.GASOLINA, 120, 2008, LocalDate.of(2013, 4, 12), true, 3),
        Motocicleta("BBBB-222", "Citroen", TipoVehiculo.HÍBRIDO, 23, 2004, LocalDate.of(2009, 12, 30), false, 6),
        Camion("CCCC-333", "Peugeot", TipoVehiculo.GASÓLEO, 500, 2012, LocalDate.of(2011, 7, 27), true, 1200),
        Automovil("DDDD-444", "Toyota", TipoVehiculo.ELÉCTRICO, 120, 2008, LocalDate.of(2013, 4, 12), false, 3)
    )

    @Test
    fun listarTodosLosVehiculos() {
        every { repository.listarTodosLosVehiculos() } returns misVehiculos
        val lista = controller.listarTodosLosVehiculos()
        assertAll(
            { assertEquals(misVehiculos.size, lista.size)},
            { assertEquals(misVehiculos[0], lista[0])},
            { assertEquals(misVehiculos[1], lista[1])},
            { assertEquals(misVehiculos[2], lista[2])},
            { assertEquals(misVehiculos[3], lista[3])}
        )
    }

    @Test
    fun listarTodosLosVehiculosQueHanPasadoLaRevision() {
        every { repository.listarTodosLosVehiculosQueHanPasadoLaRevision() } returns listOf(misVehiculos[0], misVehiculos[2])
        val lista = controller.listarTodosLosVehiculosQueHanPasadoLaRevision()
        assertAll(
            { assertEquals(2, lista.size)},
            { assertEquals(misVehiculos[0], lista[0])},
            { assertEquals(misVehiculos[2], lista[1])}
        )
    }

    @Test
    fun listarTodosLosVehiculosQueHanSidoDescartados() {
        every { repository.listarTodosLosVehiculosQueHanSidoDescartados() } returns listOf(misVehiculos[1], misVehiculos[3])
        val lista = controller.listarTodosLosVehiculosQueHanSidoDescartados()
        assertAll(
            { assertEquals(2, lista.size)},
            { assertEquals(misVehiculos[1], lista[0])},
            { assertEquals(misVehiculos[3], lista[1])}
        )
    }

    @Test
    fun informarDeUnVehiuloSegunElId() {
        every { repository.listarTodosLosVehiculos() } returns misVehiculos
        every { repository.informarDeUnVehiuloSegunElId(misVehiculos[0].matricula) } returns misVehiculos[0]
        assertEquals(misVehiculos[0], controller.informarDeUnVehiuloSegunElId(misVehiculos[0].matricula))
    }

    @Test
    fun informarDeUnVehiuloSegunElIdPeroNoSeEncontro() {
        every { repository.listarTodosLosVehiculos() } returns misVehiculos
        every { repository.informarDeUnVehiuloSegunElId(misVehiculos[0].matricula) } returns null
        val message = assertThrows<VehiculoNotFoundException>{ controller.informarDeUnVehiuloSegunElId(misVehiculos[0].matricula) }
        assertEquals("Error del sistema: No se encontró ningún vehículo con el id deseado.", message.message)
    }

    @Test
    fun actualizar() {
        val miVehiculo = Automovil("AAAA-111", "Peugeot", TipoVehiculo.ELÉCTRICO, 170, 2018, LocalDate.of(2013, 4, 12), true, 6)
        val miVehiculo2 = Automovil("AAAA-111", "Peugeot", TipoVehiculo.ELÉCTRICO, 170, 2018, LocalDate.now(), true, 6)
        every { repository.actualizar(miVehiculo) } returns miVehiculo2
        every { repository.listarTodosLosVehiculos() } returns listOf(miVehiculo2, misVehiculos[1], misVehiculos[2], misVehiculos[3])
        assertAll(
            { assertEquals(4, controller.listarTodosLosVehiculos().size)},
            { assertEquals(miVehiculo2, controller.actualizar(miVehiculo)) }
        )
    }

    @Test
    fun crear(){
        val miVehiculo = Automovil("AAAA-111", "Peugeot", TipoVehiculo.ELÉCTRICO, 170, 2018, LocalDate.of(2013, 4, 12), true, 6)
        every { repository.actualizar(miVehiculo) } returns miVehiculo
        every { repository.listarTodosLosVehiculos() } returns listOf(misVehiculos[0], misVehiculos[1], misVehiculos[2], misVehiculos[3], miVehiculo)

        assertAll(
            { assertEquals(5, controller.listarTodosLosVehiculos().size)},
            { assertEquals(miVehiculo, controller.actualizar(miVehiculo)) }
        )
    }

    @Test
    fun revocar() {
        every { repository.listarTodosLosVehiculos() } returns misVehiculos
        every { repository.revocar(misVehiculos[0].matricula) } returns misVehiculos[0]
        assertEquals(misVehiculos[0], controller.revocar(misVehiculos[0].matricula))
    }

    @Test
    fun revocarPeroNoSeEncontro(){
        every { repository.listarTodosLosVehiculos() } returns misVehiculos
        every { repository.revocar(idNoExistente) } returns null
        val message = assertThrows<VehiculoNotFoundException>{ controller.revocar(idNoExistente) }
        assertEquals("Error del sistema: No se encontró ningún vehículo con el id deseado.", message.message)
    }

    @Test
    fun listarVehiculosSegunElTipo() {
        every { repository.listarVehiculosSegunElTipo(TipoVehiculo.GASOLINA) } returns listOf(misVehiculos[0])
        val lista = controller.listarVehiculosSegunElTipo(TipoVehiculo.GASOLINA)
        assertAll(
            { assertEquals(1, lista.size)},
            { assertEquals(misVehiculos[0], lista[0])}
        )
    }

    @Test
    fun elEsVehiculoValido() {
        assertTrue(controller.esElVehiculoValido(misVehiculos[0]))
    }

    @Test
    fun elNoEsVehiculoValido() {
        val message1 = assertThrows<VehiculoBadRequestException> {
            controller.esElVehiculoValido(Automovil("AAAA-111", "", TipoVehiculo.GASOLINA, 120, 2008, LocalDate.of(2013, 4, 12), true, 3))
        }
        val message2 = assertThrows<VehiculoBadRequestException> {
            controller.esElVehiculoValido(Automovil("AAAA-111", "Toyota", TipoVehiculo.GASOLINA, -120, 2008, LocalDate.of(2013, 4, 12), true, 3))
        }
        val message3 = assertThrows<VehiculoBadRequestException> {
            controller.esElVehiculoValido(Automovil("AAAA-111", "Toyota", TipoVehiculo.GASOLINA, 120, 2024, LocalDate.of(2013, 4, 12), true, 3))
        }
        val message4 = assertThrows<VehiculoBadRequestException> {
            controller.esElVehiculoValido(Automovil("AAAA-111", "Toyota", TipoVehiculo.GASOLINA, 120, 2008, LocalDate.of(2024, 4, 12), true, 3))
        }
        val message5 = assertThrows<VehiculoBadRequestException> {
            controller.esElVehiculoValido(Automovil("AAAA-111", "Toyota", TipoVehiculo.GASOLINA, 120, 2008, LocalDate.of(2013, 4, 12), true, 2))
        }
        val message6 = assertThrows<VehiculoBadRequestException> {
            controller.esElVehiculoValido(Motocicleta("AAAA-111", "Toyota", TipoVehiculo.GASOLINA, 120, 2008, LocalDate.of(2013, 4, 12), true, 0))
        }
        val message7 = assertThrows<VehiculoBadRequestException> {
            controller.esElVehiculoValido(Camion("AAAA-111", "Toyota", TipoVehiculo.GASOLINA, 120, 2008, LocalDate.of(2013, 4, 12), true, 0))
        }
        assertAll(
            {assertEquals("Error del sistema: El modelo del coche no puede estar vacio.", message1.message)},
            {assertEquals("Error del sistema: Los kilometros del coche debén ser como mínimo, 0.", message2.message)},
            {assertEquals("Error del sistema: El año de matriculación no puede ser mayor al actual, que es: ${LocalDate.now().year}", message3.message)},
            {assertEquals("Error del sistema: La fecha de la última revisión no ha podido ser la actual, que es: ${LocalDate.now()}", message4.message)},
            {assertEquals("Error del sistema: El número de plazas debe ser como mínimo 3.", message5.message)},
            {assertEquals("Error del sistema: La cilindrada debe ser como mínimo 1.", message6.message)},
            {assertEquals("Error del sistema: La capacidad máxima debe ser como mínimo 1.", message7.message)}
        )
    }

    @Test
    fun laIdEsValida(){
        assertTrue(controller.esLaIdValida("AAAA-111"))
    }

    @Test
    fun laIdNoEsValida() {
        val message1 = assertThrows<VehiculoBadRequestException> { controller.esLaIdValida("") }
        val message2 = assertThrows<VehiculoBadRequestException> { controller.esLaIdValida("cekoeejeonve") }
        assertAll(
            {assertEquals("Error del sistema: El id no puede estar vacia.", message1.message)},
            {assertEquals("""Error del sistema: El id debe tener el patrón adecuado, por ejemplo: "AAAA-000"""", message2.message)}
        )
    }

    @Test
    fun hayVehiculosEnLaLista() {
        every { repository.listarTodosLosVehiculos() } returns misVehiculos
        assertTrue(controller.hayVehiculosEnLaLista())
    }

    @Test
    fun noHayVehiculosEnLaLista() {
        every { repository.listarTodosLosVehiculos() } returns listOf()
        val message = assertThrows<VehiculoListEmptyException> { controller.hayVehiculosEnLaLista() }
        assertEquals("Error del sistema: No hay vehículos.", message.message)
    }

    @Test
    fun hayVehiculosDelTipoEspecifico() {
        every { repository.listarVehiculosSegunElTipo(TipoVehiculo.GASOLINA) } returns listOf(misVehiculos[0])
        assertTrue(controller.hayVehiculosDelTipoEspecifico(TipoVehiculo.GASOLINA))
    }

    @Test
    fun noHayVehiculosDelTipoEspecifico() {
        every { repository.listarVehiculosSegunElTipo(TipoVehiculo.GASOLINA) } returns listOf()
        val message = assertThrows<VehiculoListEmptyException> { controller.hayVehiculosDelTipoEspecifico(TipoVehiculo.GASOLINA) }
        assertEquals("Error del sitema: No hay ningún vehículo del tipo especificado.", message.message)
    }

    @Test
    fun hayVehiculosAprobados() {
        every { repository.listarTodosLosVehiculosQueHanPasadoLaRevision() } returns listOf(misVehiculos[0], misVehiculos[2])
        assertTrue(controller.hayVehiculosAprobadosONo(true))
    }

    @Test
    fun noHayVehiculosAprobados(){
        every { repository.listarTodosLosVehiculosQueHanPasadoLaRevision() } returns listOf()
        val message = assertThrows<VehiculoListEmptyException> { controller.hayVehiculosAprobadosONo(true) }
        assertEquals("Error del sistema: No hay vehículos que hayan pasado la revisión.", message.message)
    }

    @Test
    fun hayVehiculosNoAprobados() {
        every { repository.listarTodosLosVehiculosQueHanSidoDescartados() } returns listOf(misVehiculos[1], misVehiculos[3])
        assertTrue(controller.hayVehiculosAprobadosONo(false))
    }

    @Test
    fun noHayVehiculosNoAprobados(){
        every { repository.listarTodosLosVehiculosQueHanSidoDescartados() } returns listOf()
        val message = assertThrows<VehiculoListEmptyException> { controller.hayVehiculosAprobadosONo(false) }
        assertEquals("Error del sistema: No hay vehículos que no hayan pasado la revisión.", message.message)
    }
}