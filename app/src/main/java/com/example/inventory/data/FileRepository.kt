package com.example.inventory.data

import android.net.Uri

interface FileRepository {
    suspend fun saveItemToFile(item: Item, targetFile: Uri)
    suspend fun getItemFromFile(targetFile: Uri): Item
}