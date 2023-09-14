package com.example.assignment.Helperclass

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.assignment.Dataclass.ItemData

class DatabaseHelperClass(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "items.db"
        const val DATABASE_VERSION = 1
        const val TABLE_ITEMS = "items"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_QTY = "quantity"
        const val COLUMN_PRICE = "price"
        const val COLUMN_ORDER_NO = "order_no"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        db!!.execSQL(
            "CREATE TABLE $TABLE_ITEMS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_NAME TEXT," +
                    "$COLUMN_QTY INTEGER," +
                    "$COLUMN_PRICE REAL," +
                    "$COLUMN_ORDER_NO INTEGER " +
                    ")"
        )
    }

    // for inserting
    fun insertItem(item: ItemData): Long {

        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, item.itemName)
        values.put(COLUMN_QTY, item.itemQty)
        values.put(COLUMN_PRICE, item.itemPrice)
        values.put(COLUMN_ORDER_NO, item.orderId)
        return db.insert(TABLE_ITEMS, null, values)
    }

    //for read
    @SuppressLint("Range")
    fun getAllItems(): ArrayList<ItemData> {
        val itemList = ArrayList<ItemData>() // Correct initialization
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ITEMS", null)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val qty = cursor.getInt(cursor.getColumnIndex(COLUMN_QTY))
            val price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
            val orderNo = cursor.getInt(cursor.getColumnIndex(COLUMN_ORDER_NO))
            val item = ItemData(id, name, qty, price, orderNo)
            itemList.add(item)
        }
        cursor.close()
        return itemList
    }

    //For deleting
    fun deleteItem(itemId: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_ITEMS, "$COLUMN_ID=?", arrayOf(itemId.toString()))
    }
    //for updating
    fun updateItem(item: ItemData): Int {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, item.itemName)
        values.put(COLUMN_QTY, item.itemQty)
        values.put(COLUMN_PRICE, item.itemPrice)
        values.put(COLUMN_ORDER_NO, item.orderId)

        return db.update(
            TABLE_ITEMS,
            values,
            "$COLUMN_ID=?",
            arrayOf(item.id.toString()) // Assuming 'id' is the primary key of the record to update
        )
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}
