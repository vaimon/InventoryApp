package com.example.inventory.ui.item

import com.example.inventory.utils.FieldValidator

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
            return isNameValid && isPriceValid && isQuantityValid && isSupplierNameValid && isSupplierEmailValid && isSupplierPhoneValid
        }

    val isNameValid: Boolean
        get() {
            return FieldValidator.validateName(name)
        }

    val isPriceValid: Boolean
        get() {
            return FieldValidator.validatePrice(price)
        }

    val isQuantityValid: Boolean
        get() {
            return FieldValidator.validateNumber(quantity)
        }

    val isSupplierNameValid: Boolean
        get() {
            return FieldValidator.validateName(supplierName)
        }

    val isSupplierEmailValid: Boolean
        get() {
            return FieldValidator.validateEmail(supplierEmail)
        }

    val isSupplierPhoneValid: Boolean
        get() {
            return FieldValidator.validatePhoneNumber(supplierPhone)
        }
}