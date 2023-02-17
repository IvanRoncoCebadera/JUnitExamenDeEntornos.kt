package models

import enums.TipoVehiculo
import java.time.LocalDate

sealed class Vehiculo(val matricula: String,
                      val modelo: String,
                      val tipoDeVehiculo: TipoVehiculo,
                      val kilometros: Int,
                      val añoMatriculacion: Int,
                      val fechaUltimaRevision: LocalDate,
                      val fueLaUltimaRevisionApta: Boolean
)

class Automovil(matricula: String,
                  modelo: String,
                  tipoDeVehiculo: TipoVehiculo,
                  kilometros: Int,
                  añoMatriculacion: Int,
                  fechaUltimaRevision: LocalDate,
                  fueLaUltimaRevisionApta: Boolean,
                  val numeroPlazas: Int
): Vehiculo(matricula, modelo, tipoDeVehiculo, kilometros, añoMatriculacion, fechaUltimaRevision, fueLaUltimaRevisionApta){
    override fun toString(): String {
        return "Automovil(matricula='$matricula', modelo='$modelo', tipoDeVehiculo=$tipoDeVehiculo, kilometros=$kilometros, añoMatriculacion=$añoMatriculacion, fechaUltimaRevision=$fechaUltimaRevision, fueLaUltimaRevisionApta=$fueLaUltimaRevisionApta, numeroDePlazas=$numeroPlazas)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Automovil

        if (matricula != other.matricula) return false
        if (modelo != other.modelo) return false
        if (tipoDeVehiculo != other.tipoDeVehiculo) return false
        if (kilometros != other.kilometros) return false
        if (añoMatriculacion != other.añoMatriculacion) return false
        if (fechaUltimaRevision != other.fechaUltimaRevision) return false
        if (fueLaUltimaRevisionApta != other.fueLaUltimaRevisionApta) return false
        if (numeroPlazas != other.numeroPlazas) return false

        return true
    }

    override fun hashCode(): Int {
        var result = matricula.hashCode()
        result = 31 * result + modelo.hashCode()
        result = 31 * result + tipoDeVehiculo.hashCode()
        result = 31 * result + kilometros
        result = 31 * result + añoMatriculacion
        result = 31 * result + fechaUltimaRevision.hashCode()
        result = 31 * result + fueLaUltimaRevisionApta.hashCode()
        result = 31 * result + numeroPlazas.hashCode()
        return result
    }
}

class Motocicleta(matricula: String,
                  modelo: String,
                  tipoDeVehiculo: TipoVehiculo,
                  kilometros: Int,
                  añoMatriculacion: Int,
                  fechaUltimaRevision: LocalDate,
                  fueLaUltimaRevisionApta: Boolean,
                  val cilindrada: Int
): Vehiculo(matricula, modelo, tipoDeVehiculo, kilometros, añoMatriculacion, fechaUltimaRevision, fueLaUltimaRevisionApta){
    override fun toString(): String {
        return "Motocicleta(matricula='$matricula', modelo='$modelo', tipoDeVehiculo=$tipoDeVehiculo, kilometros=$kilometros, añoMatriculacion=$añoMatriculacion, fechaUltimaRevision=$fechaUltimaRevision, fueLaUltimaRevisionApta=$fueLaUltimaRevisionApta, cilindrada=$cilindrada)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Motocicleta

        if (matricula != other.matricula) return false
        if (modelo != other.modelo) return false
        if (tipoDeVehiculo != other.tipoDeVehiculo) return false
        if (kilometros != other.kilometros) return false
        if (añoMatriculacion != other.añoMatriculacion) return false
        if (fechaUltimaRevision != other.fechaUltimaRevision) return false
        if (fueLaUltimaRevisionApta != other.fueLaUltimaRevisionApta) return false
        if (cilindrada != other.cilindrada) return false

        return true
    }

    override fun hashCode(): Int {
        var result = matricula.hashCode()
        result = 31 * result + modelo.hashCode()
        result = 31 * result + tipoDeVehiculo.hashCode()
        result = 31 * result + kilometros
        result = 31 * result + añoMatriculacion
        result = 31 * result + fechaUltimaRevision.hashCode()
        result = 31 * result + fueLaUltimaRevisionApta.hashCode()
        result = 31 * result + cilindrada.hashCode()
        return result
    }
}
class Camion(matricula: String,
                  modelo: String,
                  tipoDeVehiculo: TipoVehiculo,
                  kilometros: Int,
                  añoMatriculacion: Int,
                  fechaUltimaRevision: LocalDate,
                  fueLaUltimaRevisionApta: Boolean,
                  val capacidadMaxima: Int
): Vehiculo(matricula, modelo, tipoDeVehiculo, kilometros, añoMatriculacion, fechaUltimaRevision, fueLaUltimaRevisionApta){
    override fun toString(): String {
        return "Automovil(matricula='$matricula', modelo='$modelo', tipoDeVehiculo=$tipoDeVehiculo, kilometros=$kilometros, añoMatriculacion=$añoMatriculacion, fechaUltimaRevision=$fechaUltimaRevision, fueLaUltimaRevisionApta=$fueLaUltimaRevisionApta, capacidadMaxima=$capacidadMaxima)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Camion

        if (matricula != other.matricula) return false
        if (modelo != other.modelo) return false
        if (tipoDeVehiculo != other.tipoDeVehiculo) return false
        if (kilometros != other.kilometros) return false
        if (añoMatriculacion != other.añoMatriculacion) return false
        if (fechaUltimaRevision != other.fechaUltimaRevision) return false
        if (fueLaUltimaRevisionApta != other.fueLaUltimaRevisionApta) return false
        if (capacidadMaxima != other.capacidadMaxima) return false

        return true
    }

    override fun hashCode(): Int {
        var result = matricula.hashCode()
        result = 31 * result + modelo.hashCode()
        result = 31 * result + tipoDeVehiculo.hashCode()
        result = 31 * result + kilometros
        result = 31 * result + añoMatriculacion
        result = 31 * result + fechaUltimaRevision.hashCode()
        result = 31 * result + fueLaUltimaRevisionApta.hashCode()
        result = 31 * result + capacidadMaxima.hashCode()
        return result
    }
}
