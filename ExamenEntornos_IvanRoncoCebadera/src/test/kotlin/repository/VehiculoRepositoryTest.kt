package repository

import enums.TipoVehiculo
import models.Automovil
import models.Camion
import models.Motocicleta
import models.Vehiculo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate

internal class VehiculoRepositoryTest {

    val repository = VehiculoRepository()

    val idNoExistente = "ABCD-123"

    val misVehiculos = listOf<Vehiculo>(
        Automovil("AAAA-111", "Toyota", TipoVehiculo.GASOLINA, 120, 2008, LocalDate.of(2013, 4, 12), true, 3),
        Motocicleta("BBBB-222", "Citroen", TipoVehiculo.HÍBRIDO, 23, 2004, LocalDate.of(2009, 12, 30), false, 6),
        Camion("CCCC-333", "Peugeot", TipoVehiculo.GASÓLEO, 500, 2012, LocalDate.of(2011, 7, 27), true, 1200),
        Automovil("DDDD-444", "Toyota", TipoVehiculo.ELÉCTRICO, 120, 2008, LocalDate.of(2013, 4, 12), false, 3)
    )

    @BeforeEach
    fun setup(){
        val lista = repository.listarTodosLosVehiculos()
        for(i in lista){
            repository.revocar(i.matricula)
        }
        for(i in misVehiculos){
            repository.actualizar(i)
        }
    }

    @Test
    fun listarTodosLosVehiculos() {
        val lista = repository.listarTodosLosVehiculos()
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
        val lista = repository.listarTodosLosVehiculosQueHanPasadoLaRevision()
        assertAll(
            { assertEquals(2, lista.size)},
            { assertEquals(misVehiculos[0], lista[0])},
            { assertEquals(misVehiculos[2], lista[1])}
        )
    }

    @Test
    fun listarTodosLosVehiculosQueHanSidoDescartados() {
        val lista = repository.listarTodosLosVehiculosQueHanSidoDescartados()
        assertAll(
            { assertEquals(2, lista.size)},
            { assertEquals(misVehiculos[1], lista[0])},
            { assertEquals(misVehiculos[3], lista[1])}
        )
    }

    @Test
    fun listarVehiculosSegunElTipo() {
        var lista = repository.listarVehiculosSegunElTipo(TipoVehiculo.GASOLINA)
        assertAll(
            { assertEquals(1, lista.size)},
            { assertEquals(misVehiculos[0], lista[0])}
        )
        lista = repository.listarVehiculosSegunElTipo(TipoVehiculo.HÍBRIDO)
        assertAll(
            { assertEquals(1, lista.size)},
            { assertEquals(misVehiculos[1], lista[0])}
        )
        lista = repository.listarVehiculosSegunElTipo(TipoVehiculo.GASÓLEO)
        assertAll(
            { assertEquals(1, lista.size)},
            { assertEquals(misVehiculos[2], lista[0])}
        )
        lista = repository.listarVehiculosSegunElTipo(TipoVehiculo.ELÉCTRICO)
        assertAll(
            { assertEquals(1, lista.size)},
            { assertEquals(misVehiculos[3], lista[0])}
        )
    }

    @Test
    fun revocar() {
        val vehiculo = repository.revocar(misVehiculos[0].matricula)
        val lista = repository.listarTodosLosVehiculos()
        assertAll(
            { assertEquals(3, lista.size)},
            { assertEquals(misVehiculos[1], lista[0])},
            { assertEquals(misVehiculos[2], lista[1])},
            { assertEquals(misVehiculos[3], lista[2])},
            //Comparo el resultado de llamar a la funcion revocar, con el resultado esperado
            { assertEquals(misVehiculos[0], vehiculo)},
            {assertNull(repository.revocar(idNoExistente))}
        )
    }

    @Test
    fun actualizar() {
        val miVehiculo = Motocicleta("EEEE-555", "Citroen", TipoVehiculo.HÍBRIDO, 253, 2014, LocalDate.of(2019, 2, 20), false, 6)
        val vehiculo = repository.actualizar(miVehiculo)
        val lista = repository.listarTodosLosVehiculos()
        assertAll(
            { assertEquals(misVehiculos[0], lista[0])},
            { assertEquals(misVehiculos[1], lista[1])},
            { assertEquals(misVehiculos[2], lista[2])},
            { assertEquals(misVehiculos[3], lista[3])},
            { assertEquals(miVehiculo, lista[4])},
            //Comparo el resultado de llamar a la funcion revocar, con el resultado esperado
            { assertEquals(miVehiculo, vehiculo)}
        )
    }

    @Test
    fun informarDeUnVehiuloSegunElId() {
        assertAll(
            { assertEquals(misVehiculos[0], repository.informarDeUnVehiuloSegunElId(misVehiculos[0].matricula))},
            { assertNull(repository.informarDeUnVehiuloSegunElId(idNoExistente))}
        )
    }
}