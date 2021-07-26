package com.isidroid.b21.clean.presentation.contacts

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import java.lang.Exception


object ContactsRepository {
    fun fetchContacts(context: Context): List<Contact>? {
        val result = mutableListOf<Contact>()
        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor ?: throw Exception("No cursor created")

        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                try {
                    val id = cursor.get(ContactsContract.Contacts._ID)
                    val name = cursor.get(ContactsContract.Contacts.DISPLAY_NAME)
                    val photo = cursor.get(ContactsContract.Contacts.PHOTO_URI)
                    val hasPhoneNumber =
                        cursor.get(ContactsContract.Contacts.HAS_PHONE_NUMBER).toIntOrNull() == 1
                    val phone = if (hasPhoneNumber)
                        getData(context, id, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                    else null

                    val email =
                        getData(context, id, ContactsContract.CommonDataKinds.Email.CONTENT_URI)

                    val contact = Contact(
                        id = id,
                        name = name,
                        photo = photo,
                        hasPhoneNumber = hasPhoneNumber,
                        phone = phone,
                        email = email
                    )

                    result.add(contact)
                } catch (t: Throwable) {
                }
            }
        }

        cursor.close()
        return result
    }

    private fun Cursor.get(columnName: String) = getString(getColumnIndex(columnName))
    private fun getData(context: Context, id: String, uri: Uri): String? {
        val phoneCursor = context.contentResolver.query(
            uri,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                    + " = ?", arrayOf(id), null
        )

        val result: String? = if (phoneCursor?.moveToFirst() == true)
            phoneCursor.get(ContactsContract.CommonDataKinds.Phone.DATA)
        else
            null

        phoneCursor?.close()
        return result
    }
}