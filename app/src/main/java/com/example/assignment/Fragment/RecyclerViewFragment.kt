package com.example.assignment.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.Adapter.AdapterForListItems
import com.example.assignment.Dataclass.ItemData
import com.example.assignment.Helperclass.DatabaseHelperClass
import com.example.assignment.R
import com.example.assignment.databinding.FragmentRecyclerViewBinding

class RecyclerViewFragment : Fragment(){
    lateinit var mBinding: FragmentRecyclerViewBinding
    private lateinit var adapter: AdapterForListItems
    lateinit var list: ArrayList<ItemData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        list = ArrayList()
        adapter = AdapterForListItems(list,requireFragmentManager(), mBinding.recyclerView)
        mBinding.recyclerView.adapter = adapter
        loadItemsFromDatabase()
        return mBinding.root
    }


    // Function to load data from the database
    fun loadItemsFromDatabase() {
        // You need to implement the database query to retrieve items here
        // For example, you can use DatabaseHelperClass to fetch the items
        val dbHelper = DatabaseHelperClass(requireContext())
        val items = dbHelper.getAllItems()
        dbHelper.close()

        // Clear the current list and add the retrieved items
        list.clear()
        list.addAll(items)

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged()
    }

//    override fun onDataUpdated() {
//        val adapter = mBinding.recyclerView.adapter as? AdapterForListItems
//        adapter?.notifyDataSetChanged()
//    }
}