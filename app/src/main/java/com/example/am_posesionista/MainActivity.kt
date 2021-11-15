package com.example.am_posesionista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), TablaCosasFragment.InterfazTablaCosas {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentoActual = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if(fragmentoActual == null) {
            // val fragmento = CosaFragment()
            val fragmento = TablaCosasFragment()
                supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragmento)
                .commit()
        }
    }

    override fun onCosaSeleccionada(unaCosa: Cosa){
        // Replacing fragment
        // val fragmento = CosaFragment()
        val fragmento = CosaFragment.nuevaInstancia(unaCosa)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmento)
            .addToBackStack(null)
            .commit()
    }
}