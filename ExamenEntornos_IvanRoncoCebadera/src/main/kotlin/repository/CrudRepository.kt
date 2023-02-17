package repository

import enums.TipoVehiculo

interface CrudRepository<T, ID> {
    fun listarTodosLosVehiculos(): List<T>
    fun listarTodosLosVehiculosQueHanPasadoLaRevision(): List<T>
    fun listarTodosLosVehiculosQueHanSidoDescartados(): List<T>
    fun informarDeUnVehiuloSegunElId(id: ID): T?
    fun actualizar(entity: T): T
    fun revocar(id: ID): T?
    fun listarVehiculosSegunElTipo(tipo: TipoVehiculo): List<T>
}