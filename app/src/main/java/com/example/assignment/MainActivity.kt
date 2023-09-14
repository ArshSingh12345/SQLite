package com.example.assignment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.assignment.Dataclass.ItemData
import com.example.assignment.Fragment.RecyclerViewFragment
import com.example.assignment.Helperclass.DatabaseHelperClass
import com.example.assignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState == null) {
            replaceFragment(RecyclerViewFragment())
        }
        mBinding.buttonSave.setOnClickListener {
            saveItemToDatabase()
        }
    }

    private fun saveItemToDatabase() {
        val itemName = mBinding.editTextItemName.text.toString()
        val itemPriceStr = mBinding.editTextItemPrice.text.toString()
        val itemQtyStr = mBinding.editTextItemQty.text.toString()
        if (itemName.isNotBlank() && itemPriceStr.isNotBlank()) {
            val itemPrice = itemPriceStr.toDouble()
            val itemQty = itemQtyStr.toInt()
            val item = ItemData(
                0,
                itemName,
                itemQty,
                itemPrice,
                0
            )
            val dbHelper = DatabaseHelperClass(this)
            val insertedId = dbHelper.insertItem(item)
            val fragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container) as? RecyclerViewFragment
            fragment?.loadItemsFromDatabase()
            dbHelper.close()
            if (insertedId != -1L) {
                Toast.makeText(this, "Item inserted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Item insertion failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Plz enter Your Details", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to replace fragments
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
