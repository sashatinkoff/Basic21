package com.isidroid.b21.clean.presentation.contacts

data class Contact(
    val id: String,
    val name: String,
    val email: String? = null,
    val photoUri: String? = null,
    val photo: String? = null,
    val phone: String? = null,
    val hasPhoneNumber: Boolean
) {
    override fun toString(): String {
        return "Contact(id='$id', name='$name', email=$email, phone=$phone)"
    }
}