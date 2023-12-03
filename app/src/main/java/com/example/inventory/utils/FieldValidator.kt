package com.example.inventory.utils

object FieldValidator {
    fun validateEmail(input: String) = Regex("""^[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?${'$'}""").matches(input)

    fun validatePhoneNumber(input: String) = Regex("""(\+7|8)[- _]*\(?[- _]*(\d{3}[- _]*\)?([- _]*\d){7}|\d\d[- _]*\d\d[- _]*\)?([- _]*\d){6})""").matches(input)

    fun validatePrice(input: String) = Regex("""\d+(\.\d+)*""").matches(input)

    fun validateNumber(input: String) = Regex("""\d+""").matches(input)

    fun validateName(input: String) = Regex("""[\wА-Яа-я\- ]+""").matches(input)
}