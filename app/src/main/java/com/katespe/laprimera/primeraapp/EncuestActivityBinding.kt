package com.katespe.laprimera.primeraapp

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.katespe.laprimera.R
import com.katespe.laprimera.databinding.ActivityEncuestBinding  // Asegúrate de importar tu clase de vinculación generada

class EncuestActivityBinding private constructor(
    private val rootView: View
) : ViewBinding {

    // Declara las vistas que deseas vincular aquí
    val fabAddTask: FloatingActionButton
    val emotionsRecyclerView: RecyclerView

    init {
        fabAddTask = rootView.findViewById(R.id.fabAddTask)
        emotionsRecyclerView = rootView.findViewById(R.id.emotions)
    }

    override fun getRoot() = rootView

    companion object {
        fun inflate(inflater: LayoutInflater): EncuestActivityBinding {
            val rootView = ActivityEncuestBinding.inflate(inflater).root
            return EncuestActivityBinding(rootView)
        }
    }
}




