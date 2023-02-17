package exceptions

sealed class VehiculoException(message: String): RuntimeException(message)
class VehiculoNotFoundException(message: String): VehiculoException(message)
class VehiculoBadRequestException(message: String): VehiculoException(message)
class VehiculoListEmptyException(message: String): VehiculoException(message)