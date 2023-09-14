package com.example.assignment.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.Dataclass.ItemData
import com.example.assignment.Fragment.EditListFragment
import com.example.assignment.Helperclass.DatabaseHelperClass
import com.example.assignment.R
import com.example.assignment.databinding.ItemDesignBinding

class AdapterForListItems(
    private val list: ArrayList<ItemData>,
    private val fragmentManager: FragmentManager,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<AdapterForListItems.ViewHolder>() {
    class ViewHolder(val mBinding: ItemDesignBinding) : RecyclerView.ViewHolder(mBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.mBinding.textItemName.text = data.itemName
        holder.mBinding.textOrderNo.text = data.id.toString()
        holder.mBinding.textItemPrice.text = data.itemPrice.toString()
        holder.mBinding.textItemQty.text = data.itemQty.toString()

        holder.itemView.setOnClickListener {
            val fragment = EditListFragment.newInstance(data, recyclerView)
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        holder.mBinding.delete.setOnClickListener {
            val itemId = data.id
            val dbHelper = DatabaseHelperClass(holder.itemView.context)
            val deletedRows = dbHelper.deleteItem(itemId)
            dbHelper.close()

            if (deletedRows > 0) {
                list.removeAt(position)
                notifyItemRemoved(position)
            } else {
                Toast.makeText(
                    holder.itemView.context,
                    "Item deletion failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
