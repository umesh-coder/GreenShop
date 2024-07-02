package com.umesh.greenshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.umesh.greenshop.Model.CategoryModel
import com.umesh.greenshop.Model.ItemsModel
import com.umesh.greenshop.Model.SliderModel

class MainViewModel : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _banner = MutableLiveData<List<SliderModel>>()
    private val _category = MutableLiveData<MutableList<CategoryModel>>()
    private val _recommend = MutableLiveData<MutableList<ItemsModel>>()


    val banner: MutableLiveData<List<SliderModel>> = _banner
    val category: LiveData<MutableList<CategoryModel>> = _category
    val recommend: LiveData<MutableList<ItemsModel>> = _recommend


    fun loadBanner() {
        val bannerRef = firebaseDatabase.getReference("Banner")
        bannerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<SliderModel>()
                for (snapshot in p0.children) {
                    val banner = snapshot.getValue(SliderModel::class.java)
                    banner?.let { list.add(it) }
                }
                _banner.value = list
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun loadCategory() {
        val categoryRef = firebaseDatabase.getReference("Category")
        categoryRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<CategoryModel>()
                for (snapshot in p0.children) {
                    val category = snapshot.getValue(CategoryModel::class.java)
                    category?.let { list.add(it) }
                }
                _category.value = list
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun loadRecommended() {
        val recommendedRef = firebaseDatabase.getReference("Items")
        recommendedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()
                for (snapshot in p0.children) {
                    val Recommended = snapshot.getValue(ItemsModel::class.java)
                    Recommended?.let { lists.add(it) }

                    _recommend.value = lists
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}