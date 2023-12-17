package com.example.inventory.data

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.File

class SecuredFileRepository(private val applicationContext: Context) : FileRepository {
    private val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun saveItemToFile(item: Item, targetFile: Uri) {
        val cachedFile = File(applicationContext.cacheDir, targetFile.lastPathSegment!!.split("/").last())
        val encryptedCachedFile = EncryptedFile.Builder(
            cachedFile,
            applicationContext,
            masterKeys,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val encryptedCacheOutput = encryptedCachedFile.openFileOutput()
        Json.encodeToStream(item, encryptedCacheOutput)

        withContext(Dispatchers.IO) {
            encryptedCacheOutput.close()
        }

        val encryptedCacheInput = cachedFile.inputStream()
        val targetOutput = applicationContext.contentResolver.openOutputStream(targetFile)

        val copyResult = encryptedCacheInput.copyTo(targetOutput!!)
        Log.i("InventoryFile", "File was copied ($copyResult bytes)")

        withContext(Dispatchers.IO) {
            targetOutput.close()
            encryptedCacheInput.close()
            cachedFile.delete()
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getItemFromFile(targetFile: Uri) : Item{
        val cachedFile = File(applicationContext.cacheDir, targetFile.lastPathSegment!!.split("/").last())
        val targetInput = applicationContext.contentResolver.openInputStream(targetFile)

        val cacheOutput = cachedFile.outputStream()

        targetInput!!.copyTo(cacheOutput)

        withContext(Dispatchers.IO) {
            targetInput.close()
            cacheOutput.close()
        }

        val encryptedCachedFile = EncryptedFile.Builder(
            cachedFile,
            applicationContext,
            masterKeys,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val encryptedCacheInput = encryptedCachedFile.openFileInput()
        val item = Json.decodeFromStream<Item>(encryptedCacheInput)

        withContext(Dispatchers.IO) {
            encryptedCacheInput.close()
            cachedFile.delete()
        }

        return item.copy(id = 0, creationType = CreationType.FILE)
    }
}