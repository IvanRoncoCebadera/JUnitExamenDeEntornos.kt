package controller

import enums.TipoVehiculo
import exceptions.VehiculoBadRequestException
import exceptions.VehiculoListEmptyException
import exceptions.VehiculoNotFoundException
import models.Automovil
import models.Camion
import models.Motocicleta
import models.Vehiculo
import repository.CrudRepository
import java.time.LocalDate

class VehiculoController(val repository: CrudRepository<Vehiculo, String>) {

    /**
     * función que consigue un listado completo de vehículos llamando al repositorio
     * @return una lista inmutabel de todos los vehículos
     */
    fun listarTodosLosVehiculos(): List<Vehiculo>{
        hayVehiculosEnLaLista()
        return repository.listarTodosLosVehiculos()
    }

    /**
     * función que lista a todos aquellos vehículos que haya aprobado la revisión
     * @return una lista inmutable de los vehiculos que hayan aprobado la revisión
     */
    fun listarTodosLosVehiculosQueHanPasadoLaRevision(): List<Vehiculo>{
        hayVehiculosAprobadosONo(true)
        return repository.listarTodosLosVehiculosQueHanPasadoLaRevision()
    }

    /**
     * función que lista a todos aquellos vehículos que no haya aprobado la revisión
     * @return una lista inmutable de los vehiculos que no hayan aprobado la revisión
     */
    fun listarTodosLosVehiculosQueHanSidoDescartados(): List<Vehiculo>{
        hayVehiculosAprobadosONo(false)
        return repository.listarTodosLosVehiculosQueHanSidoDescartados()
    }

    /**
     * función que devuelve un vehículo en especifico según su id
     * @param id es el identificador del vehículo con el que se buscará
     * @throws VehiculoNotFoundException, en caso de que no se encuentre ningún vehículo
     * @return el vehiculo encontrado, en caso de que se encuentre el vehículo con el id especificado
     */
    fun informarDeUnVehiuloSegunElId(id: String): Vehiculo{
        hayVehiculosEnLaLista()
        esLaIdValida(id)
        return repository.informarDeUnVehiuloSegunElId(id)?: throw VehiculoNotFoundException("Error del sistema: No se encontró ningún vehículo con el id deseado.")
    }

    /**
     * función que salvará un nuevo vehículo, o actualiza uno ya existente
     * @param entity es el vehículo que queremos añadir o actualizar
     * @return el vehículo recien salvado, o la versión más actual del vehículo actualizado
     */
    fun actualizar(entity: Vehiculo): Vehiculo{
        esElVehiculoValido(entity)
        return repository.actualizar(entity)
    }

    /**
     * función que elimina al vehículo especificado, a través de su id, de la lista de vehículos
     * @param id es el identificador del vehículo a buscar
     * @throws VehiculoNotFoundException, en caso de que no se encuetre ningún vehículo
     * @return el vehiculo borrado, en caso de que se encuentre el vehículo con el id especificado
     */
    fun revocar(id: String): Vehiculo{
        hayVehiculosEnLaLista()
        esLaIdValida(id)
        return repository.revocar(id)?: throw VehiculoNotFoundException("Error del sistema: No se encontró ningún vehículo con el id deseado.")
    }

    /**
     * función que lista todos los vehículos que haya de un tipo especificado
     * @param tipo es el tipo de vehículo del que quermos la lista
     * @return una lista inmutable de vehículos del tipo especificado
     */
    fun listarVehiculosSegunElTipo(tipo: TipoVehiculo): List<Vehiculo>{
        hayVehiculosDelTipoEspecifico(tipo)
        return repository.listarVehiculosSegunElTipo(tipo)
    }

    /**
     * función que válida todos los datos posibles del vehículo que se le pase
     * @param entity es el vehículo que queremos validar
     * @throws VehiculoBadRequestException si los datos del vehículo no son válidos
     * @return true si los datos del vehículo si son válidos
     */
    fun esElVehiculoValido(entity: Vehiculo): Boolean{
        //Aprobecho que tengo creada la función de validar el id
        esLaIdValida(entity.matricula)
        //Primero válido el modelo
        require(entity.modelo.isNotEmpty()){
            throw VehiculoBadRequestException("Error del sistema: El modelo del coche no puede estar vacio.")
        }
        //Después válido los kilómetros
        require(entity.kilometros >= 0){
            throw VehiculoBadRequestException("Error del sistema: Los kilometros del coche debén ser como mínimo, 0.")
        }
        //Después válido el año de matriculación
        require(entity.añoMatriculacion <= LocalDate.now().year){
            throw VehiculoBadRequestException("Error del sistema: El año de matriculación no puede ser mayor al actual, que es: ${LocalDate.now().year}")
        }
        //Después válido la fecha de la última revisión
        require(entity.fechaUltimaRevision.compareTo(LocalDate.now()) <= 0){
            throw VehiculoBadRequestException("Error del sistema: La fecha de la última revisión no ha podido ser la actual, que es: ${LocalDate.now()}")
        }
        //Por último, toca comprobar los datos que tienen cada hijo de Vehiculo especificamente
        when(entity){
            is Automovil -> {
                require(entity.numeroPlazas > 2){
                    throw VehiculoBadRequestException("Error del sistema: El número de plazas debe ser como mínimo 3.")
                }
            }
            is Motocicleta -> {
                require(entity.cilindrada > 0){
                    throw VehiculoBadRequestException("Error del sistema: La cilindrada debe ser como mínimo 1.")
                }
            }
            is Camion -> {
                require(entity.capacidadMaxima > 0){
                    throw VehiculoBadRequestException("Error del sistema: La capacidad máxima debe ser como mínimo 1.")
                }
            }
        }
        return true
    }

    /**
     * función que sirve para comprobar si el id introducido es válido, o no
     * @param id es el identificador que queremos validar
     * @throws VehiculoBadRequestException en el caso de que no sea válido
     * @return true en el caso de que sea válido
     */
    fun esLaIdValida(id: String): Boolean{
        require(id.isNotEmpty()){
            throw VehiculoBadRequestException("Error del sistema: El id no puede estar vacia.")
        }
        //Lo siento si no es el regex correcto de una matriucula, no recuerdo si primero iba la letra y despues el número o al reves
        val regex = Regex("[A-Z]{4}-[0-9]{3}")
        require(id.matches(regex)){
            throw VehiculoBadRequestException("""Error del sistema: El id debe tener el patrón adecuado, por ejemplo: "AAAA-000"""")
        }
        return true
    }

    /**
     * función que sirve para comprobar si algún vehículo o no en la lista de vehículos
     * @throws VehiculoNotFoundException si no hay vehiculos
     * @return true si hay algún vehículo
     */
    fun hayVehiculosEnLaLista(): Boolean{
        require(repository.listarTodosLosVehiculos().isNotEmpty()){
            throw VehiculoListEmptyException("Error del sistema: No hay vehículos.")
        }
        return true
    }

    /**
     * función que sirve par comprobar si hay algún vehículo del tipo que se especifique
     * @param tipo es el tipo de vehiculo que se quiere conseguir
     * @throws VehiculoNotFoundException en caso de que no haya
     * @return true en caso de que haya
     */
    fun hayVehiculosDelTipoEspecifico(tipo: TipoVehiculo): Boolean{
        require(repository.listarVehiculosSegunElTipo(tipo).isNotEmpty()){
            throw VehiculoListEmptyException("Error del sitema: No hay ningún vehículo del tipo especificado.")
        }
        return true
    }

    /**
     * función que comprueba si hay o no coches que hayan pasado la revisión o no
     * @param condicion es el booleano que usaremos para determinar si buscamos los vehículos que hayan o no pasado la revisión
     * @throws VehiculoListEmptyException en caso de que no haya vehículos
     * @return true en caso de que haya vehículos
     */
    fun hayVehiculosAprobadosONo(condicion: Boolean): Boolean{
        if(condicion) {
            require(repository.listarTodosLosVehiculosQueHanPasadoLaRevision().isNotEmpty()){
                throw VehiculoListEmptyException("Error del sistema: No hay vehículos que hayan pasado la revisión.")
            }
        }else {
            require(repository.listarTodosLosVehiculosQueHanSidoDescartados().isNotEmpty()){
                throw VehiculoListEmptyException("Error del sistema: No hay vehículos que no hayan pasado la revisión.")
            }
        }
        return true
    }
}