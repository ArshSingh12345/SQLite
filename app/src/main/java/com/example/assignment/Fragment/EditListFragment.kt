package com.example.assignment.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.Adapter.AdapterForListItems
import com.example.assignment.Dataclass.ItemData
import com.example.assignment.Helperclass.DatabaseHelperClass
import com.example.assignment.R
import com.example.assignment.databinding.FragmentEditListBinding

class EditListFragment : Fragment() {
    private lateinit var mBinding: FragmentEditListBinding
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance(itemData: ItemData, recyclerView: RecyclerView): EditListFragment {
            val fragment = EditListFragment()
            val args = Bundle()
            args.putParcelable("itemData", itemData)
            fragment.arguments = args
            fragment.recyclerView = recyclerView
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentEditListBinding.inflate(inflater, container, false)
        val itemData = requireArguments().getParcelable<ItemData>("itemData")

        if (itemData != null) {
            mBinding.editTextItemName.setText(itemData.itemName)
            mBinding.editTextItemQty.setText(itemData.itemQty.toString())
            mBinding.editTextItemPrice.setText(itemData.itemPrice.toString())

            mBinding.buttonSave.setOnClickListener {
                val updatedName = mBinding.editTextItemName.text.toString()
                val updatedQty = mBinding.editTextItemQty.text.toString().toInt()
                val updatedPrice = mBinding.editTextItemPrice.text.toString().toDouble()


                itemData.itemName = updatedName
                itemData.itemQty = updatedQty
                itemData.itemPrice = updatedPrice

                // Update the item in the database
                val dbHelper = DatabaseHelperClass(requireContext())
                val rowsAffected = dbHelper.updateItem(itemData)

                if (rowsAffected > 0) {
                    val adapter = recyclerView.adapter as? AdapterForListItems
                    //onDataUpdateListener.onDataUpdated()
                    val fragmentManager = requireFragmentManager()
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, RecyclerViewFragment())
                        .commit()
                    adapter?.notifyDataSetChanged()
                } else {

                }
            }
        }
        return mBinding.root
    }
}
