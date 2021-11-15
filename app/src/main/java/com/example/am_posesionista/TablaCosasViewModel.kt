package com.example.am_posesionista

import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.*

private const val TAG = "debug"
class TablaCosasViewModel: ViewModel() {
    val inventory = mutableListOf<Any>()
    val cat1 = mutableListOf<Cosa>()
    val cat2 = mutableListOf<Cosa>()
    val cat3 = mutableListOf<Cosa>()
    val cat4 = mutableListOf<Cosa>()
    val cat5 = mutableListOf<Cosa>()
    val cat6 = mutableListOf<Cosa>()
    val cat7 = mutableListOf<Cosa>()
    val cat8 = mutableListOf<Cosa>()
    val cat9 = mutableListOf<Cosa>()
    val cat10 = mutableListOf<Cosa>()
    val cat11 = mutableListOf<Cosa>()

    init {
        inventory.add(cat1)
        inventory.add(cat2)
        inventory.add(cat3)
        inventory.add(cat4)
        inventory.add(cat5)
        inventory.add(cat6)
        inventory.add(cat7)
        inventory.add(cat8)
        inventory.add(cat9)
        inventory.add(cat10)
        inventory.add(cat11)
    }

    fun dropCosa(itemIndex: Int) {
        Log.d(TAG, "itemIndex")
        //alt_inventory.removeAt(itemIndex)
        //.removeAt(itemIndex)
    }

    fun dragCosa(from: Int, dest: Int) {
        //val temp = alt_inventory[dest]
        //alt_inventory[dest] = alt_inventory[from]
        //alt_inventory[from] = temp
    }

    fun addCosa(unaCosa: Cosa){
        cat1.add(unaCosa)
    }

    fun orderSection(unaCosa: Cosa){

    }
}