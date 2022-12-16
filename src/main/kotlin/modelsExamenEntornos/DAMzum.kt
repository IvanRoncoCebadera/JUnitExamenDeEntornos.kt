package modelsExamenEntornos

data class DAMzum(val cardNumber: String, val fecha: String, val CSV: Int) {

    /**
     * función que sirve para comprobar si el CardNumber es válido o no
     * @param cardNumber es el CardNumber que queremos comprobar
     * @throws IllegalArgumentException como diversos mensajes de error que dependen de en que este mal el CardNumber
     * @return true en el caso de que el CardNumber sea válido
     */
    fun esValidoCardNumber(cardNumber: String?): Boolean{
        require(cardNumber != null){"El cardNumber no puede ser nulo, vuelve a probar:"}
        require(cardNumber.first() != '-'){"El cardNumber no puede ser negativo, vuelve a probar"}
        val regex = Regex("[0-9]+")
        require(cardNumber.matches(regex)){"El cardNumber introducido no es un número, vuelve a probar:"}
        require(cardNumber.length == 16){"El cardNumber no puede ser ni menor ni mayor de 16 dígitos, vuelve a probar:"}
        return true
    }

    /**
     * función que sirve para comprobar si la fecha es válida o no
     * @param fecha es la fecha que queremos comprobar
     * @throws IllegalArgumentException como diversos mensajes de error que dependen de en que este mal la fecha
     * @return true en el caso de que la fecha sea válida
     */
    fun esValidaFecha(fecha: String?): Boolean{
        require(fecha != null){"La fecha no puede ser nula, vuelve a probar:"}
        val regex = Regex("[0-9]{4}-[0-9]{2}-[0-9]{2}")
        require(fecha.matches(regex)){"La fecha introducida no cumple con el patrón esperado, vuelve a probar:"}
        val año = fecha.split('-')[0].toInt()
        val mes = fecha.split('-')[1].toInt()
        val dia = fecha.split('-')[2].toInt()
        require(año >= 2020 && mes >= 1 && dia >= 1){"La fecha introducida no puede tener un año menor que 2020, ni un mes menor que 01, ni un día menor que 01, vuelve a probar:"}
        require(año <= 2025 && mes <= 12 && dia <= 31){"La fecha introducida no puede tener un año mayor que 2025, ni un mes mayor que 12, ni un día mayor que 31, vuelve a probar:"}
        return true
    }

    /**
     * función que sirve para comprobar si el CSV es válido o no
     * @param CSV es la CSV que queremos comprobar
     * @throws IllegalArgumentException como diversos mensajes de error que dependen de en que este mal la fecha
     * @return true en el caso de que el CSV sea válido
     */
    fun esValidoCSV(CSV: String?): Boolean{
        require(CSV != null){"El CSV no puede ser nulo, vuelve a probar:"}
        val regex = Regex("-?[0-9]+")
        require(CSV.matches(regex)){"El CSV introducida no es un número, vuelve a probar:"}
        require(CSV.toInt() >= 0 && CSV.toInt() <= 499){"El CSV no puede ser ni menor que 0 ni mayor que 499, vuelve a probar:"}
        return true
    }

    /**
     * función que sirve para calcular el CSV a través del CardNumber
     * @param cardNumber es el CardNumber con el que calcularemos el CSV
     * @return el CSV calculado a través del CardNumber
     */
    fun calcularCSV(cardNumber: String): Int{
        var resultado = 0
        val parte1 = ("${cardNumber[0]}${cardNumber[1]}${cardNumber[2]}${cardNumber[3]}").toInt()
        val parte2 = ("${cardNumber[4]}${cardNumber[5]}${cardNumber[6]}${cardNumber[7]}").toInt()
        val parte3 = ("${cardNumber[8]}${cardNumber[9]}${cardNumber[10]}${cardNumber[11]}").toInt()
        val parte4 = ("${cardNumber[12]}${cardNumber[13]}${cardNumber[14]}${cardNumber[15]}").toInt()
        resultado = (parte1 + parte2 + parte3 + parte4) % 499
        return resultado
    }
}

