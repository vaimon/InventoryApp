package com.example.inventory.data

import android.content.Context
import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.commonsware.cwac.saferoom.SQLCipherUtils
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [Item::class], version = 4, exportSchema = true)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context, cipherKey: String): InventoryDatabase {
            val databaseFile = context.getDatabasePath("item_database")
            SQLiteDatabase.loadLibs(context)

            when(SQLCipherUtils.getDatabaseState(databaseFile)){
                SQLCipherUtils.State.UNENCRYPTED -> {
                    SQLCipherUtils.encrypt(context, databaseFile, cipherKey.toByteArray())
                    Log.i("InventoryDB", "DB was encrypted successfully")
                }
                SQLCipherUtils.State.ENCRYPTED -> {
                    Log.i("InventoryDB", "DB has already been encrypted")
                }
                else -> {
                    Log.w("InventoryDB", "DB does not exist!")
                }
            }

            val supportFactory = SupportFactory(SQLiteDatabase.getBytes(cipherKey.toCharArray()))
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()      // :(
                    .openHelperFactory(supportFactory)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}