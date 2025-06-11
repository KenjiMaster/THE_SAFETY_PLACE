package com.katespe.laprimera.primeraapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.katespe.laprimera.R
import java.lang.Math.abs

class ViewPagerAdapter(fa:FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3 // Cantidad de pantallas

    override fun createFragment(position: Int): Fragment {
        // Devuelve el fragmento correspondiente a la posición
        when (position) {
            0 -> return IndexFragment()
            1 -> return TwoFragment()
            2 -> return ThirtFragment()
            else -> return ThirtFragment() // Fragmento predeterminado
        }
    }

}
class CustomPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        // Define las animaciones de entrada y salida según la posición de la página.
        val scaleFactor = 0.85f // Escala de la página activa
        val alphaFactor = 0.5f // Opacidad de la página inactiva

        when {
            position < -1 -> { // Página a la izquierda (inactiva)
                page.alpha = alphaFactor
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            }
            position <= 1 -> { // Páginas visibles (activa y la siguiente)
                page.alpha = Math.max(1 - abs(position), alphaFactor)
                page.scaleX = Math.max(1 - abs(position), scaleFactor)
                page.scaleY = Math.max(1 - abs(position), scaleFactor)
            }
            else -> { // Página a la derecha (inactiva)
                page.alpha = alphaFactor
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            }
        }
    }
}

class SmoothSlidePageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.translationX = -position * page.width
        page.alpha = 1 - abs(position)
    }
}