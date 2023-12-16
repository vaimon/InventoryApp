package com.example.inventory.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.util.Log
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.io.File
import java.util.UUID

class SecuredFileRepository(private val applicationContext: Context) : FileRepository {
    private val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)



//    @OptIn(ExperimentalSerializationApi::class)
//    override suspend fun saveItemToFile(item: Item, targetDirectory: Uri?) {
//        val file = File(targetDirectory?.path!!, NEW_FILENAME)
//        Log.d("DEBUG","Path: ${file.path}")
//        withContext(Dispatchers.IO) {
//            val result = file.createNewFile()
//            Log.d("DEBUG","Result: $result}")
//        }
//        Log.d("DEBUG","Path: ${file.path}")
//        val encryptedFile = EncryptedFile.Builder(
//            file,
//            applicationContext,
//            masterKeys,
//            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
//        ).build()
//
//        Json.encodeToStream(item, encryptedFile.openFileOutput())
//    }

    override suspend fun saveItemToFile(item: Item, targetDirectory: Uri?) {


//        Json.encodeToStream(item, encryptedFile.openFileOutput())
    }


        companion object{
            private const val WRITE_REQUEST_CODE = 1
    }
}