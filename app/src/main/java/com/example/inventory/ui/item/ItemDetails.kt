package com.example.inventory.ui.item

data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    val supplierName: String = "",
    val supplierEmail: String = "",
    val supplierPhone: String = "",
) {
    val isEntryValid: Boolean
        get() {
            return isNameValid && isPriceValid && isQuantityValid // and isSupplierNameValid and isSupplierEmailValid and isSupplierPhoneValid
        }

    val isNameValid: Boolean
        get() {
            return Regex("""[\wА-Яа-я\-]+""").matches(name)
        }

    val isPriceValid: Boolean
        get() {
            return Regex("""\d+(\.\d+)*""").matches(price)
        }

    val isQuantityValid: Boolean
        get() {
            return Regex("""\d+""").matches(quantity)
        }

    val isSupplierNameValid: Boolean
        get() {
            return Regex("""[\wА-Яа-я\-]+""").matches(supplierName)
        }

    val isSupplierEmailValid: Boolean
        get() {
            return Regex("""^[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?${'$'}""").matches(supplierEmail)
        }

    val isSupplierPhoneValid: Boolean
        get() {
            return Regex("""(\+7|8)[- _]*\(?[- _]*(\d{3}[- _]*\)?([- _]*\d){7}|\d\d[- _]*\d\d[- _]*\)?([- _]*\d){6})""").matches(supplierPhone)
        }
}