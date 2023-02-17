package repository

import enums.TipoVehiculo
import exceptions.VehiculoBadRequestException
import models.Automovil
import models.Camion
import models.Motocicleta
import models.Vehiculo
import java.time.LocalDate

class VehiculoRepository: CrudRepository<Vehiculo, String> {
    val vehiculos = mutableListOf<Vehiculo>()

    /**
     * función que lista todos los vehículos
     * @return una lista inmutable de vehiculos
     */
    override fun listarTodosLosVehiculos(): List<Vehiculo> {
        return vehiculos.toList()
    }

    /**
     * función que lista todos aquellos vehículos cuya última revisión fuera válida
     * @return una lista inmutable de vehículos que pasarón la última revisión
     */
    override fun listarTodosLosVehiculosQueHanPasadoLaRevision(): List<Vehiculo> {
        val lista = mutableListOf<Vehiculo>()
        for(vehiculo in vehiculos){
            if(vehiculo.fueLaUltimaRevisionApta){
                lista.add(vehiculo)
            }
        }
        return lista.toList()
    }

    /**
     * función que lista todos aquellos vehículos cuya última revisión no fue válida
     * @return una lista inmutable de vehículos que no pasarón la última revisión
     */
    override fun listarTodosLosVehiculosQueHanSidoDescartados(): List<Vehiculo> {
        val lista = mutableListOf<Vehiculo>()
        for(vehiculo in vehiculos){
            if(!vehiculo.fueLaUltimaRevisionApta){
                lista.add(vehiculo)
            }
        }
        return lista.toList()
    }

    /**
     * función que lista todos los vehículos según el tipo de vehículos se pida
     * @param tipo es el tipo de vehículos que listaremos
     * @return una lista inmutable de vehículos, de los que se cojen solo los de el tipo especificado
     */
    override fun listarVehiculosSegunElTipo(tipo: TipoVehiculo): List<Vehiculo> {
        val lista = mutableListOf<Vehiculo>()
        when(tipo){
            TipoVehiculo.GASOLINA ->{
                for(vehiculo in vehiculos){
                    if(vehiculo.tipoDeVehiculo == TipoVehiculo.GASOLINA){
                        lista.add(vehiculo)
                    }
                }
            }
            TipoVehiculo.GASÓLEO ->{
                for(vehiculo in vehiculos){
                    if(vehiculo.tipoDeVehiculo == TipoVehiculo.GASÓLEO){
                        lista.add(vehiculo)
                    }
                }
            }
            TipoVehiculo.HÍBRIDO ->{
                for(vehiculo in vehiculos){
                    if(vehiculo.tipoDeVehiculo == TipoVehiculo.HÍBRIDO){
                        lista.add(vehiculo)
                    }
                }
            }
            TipoVehiculo.ELÉCTRICO ->{
                for(vehiculo in vehiculos){
                    if(vehiculo.tipoDeVehiculo == TipoVehiculo.ELÉCTRICO){
                        lista.add(vehiculo)
                    }
                }
            }
        }
        return lista.toList()
    }

    /**
     * función que busca un vehículo según un id, y en caso de encontrar un vehículo, lo elimina
     * @param id es el identificador del vehículo con el que buscaremos el vehiculo
     * @return el vehiculo eliminado, o un null si no se puedo eliminar ningún vehículo
     */
    override fun revocar(id: String): Vehiculo? {
        val vehiculo = informarDeUnVehiuloSegunElId(id)
        if(vehiculo != null){
            vehiculos.remove(vehiculo)
        }
        return vehiculo
    }

    /**
     * función que salvará un nuevo vehículo, o actualiza uno ya existente
     * @param entity es el vehículo que queremos añadir o actualizar
     * @return el vehículo recien salvado, o la versión más actual del vehículo actualizado
     */
    override fun actualizar(entity: Vehiculo): Vehiculo {
        var vehiculo = informarDeUnVehiuloSegunElId(entity.matricula)

        if(vehiculo != null){
            when(entity){
                is Automovil -> {
                    vehiculo = Automovil(vehiculo.matricula, vehiculo.modelo, vehiculo.tipoDeVehiculo, vehiculo.kilometros, vehiculo.añoMatriculacion, LocalDate.now(), true, (vehiculo as Automovil).numeroPlazas)
                }
                is Motocicleta -> {
                    vehiculo = Motocicleta(vehiculo.matricula, vehiculo.modelo, vehiculo.tipoDeVehiculo, vehiculo.kilometros, vehiculo.añoMatriculacion, LocalDate.now(), true, (vehiculo as Motocicleta).cilindrada)
                }
                is Camion -> {
                    vehiculo = Camion(vehiculo.matricula, vehiculo.modelo, vehiculo.tipoDeVehiculo, vehiculo.kilometros, vehiculo.añoMatriculacion, LocalDate.now(), true, (vehiculo as Camion).capacidadMaxima)
                }
            }
            vehiculos.remove(vehiculo)
            vehiculos.add(entity)
        }else{
            vehiculos.add(entity)
        }
        return entity
    }

    /**
     * función que busca un vehículo según un id
     * @param id es el identificador del vehículo con el que buscaremos el vehiculo
     * @return o bien un vehículo que tenga el id buscado, o bien un null si no se encuetra ningún vehículo con ese id
     */
    override fun informarDeUnVehiuloSegunElId(id: String): Vehiculo? {
        for(vehiculo in vehiculos){
            if(vehiculo.matricula == id) return vehiculo
        }
        return null
    }
}