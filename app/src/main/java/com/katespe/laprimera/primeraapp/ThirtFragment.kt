package com.katespe.laprimera.primeraapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.katespe.laprimera.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirtFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirtFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_thirt, container, false)

        val btnEmocionesUno = view.findViewById<AppCompatButton>(R.id.uno)
        val btnCustom = view.findViewById<AppCompatButton>(R.id.tres)
        val btnEmocionesDos = view.findViewById<AppCompatButton>(R.id.dos)

        btnEmocionesUno.setOnClickListener{
            val intent = Intent(requireContext(), RecicleActivity::class.java)
            startActivity(intent)
        }
        btnEmocionesDos.setOnClickListener{
            val intent = Intent(requireContext(), NoseActivity::class.java)
            startActivity(intent)
        }
        btnCustom.setOnClickListener{
            val intent = Intent(requireContext(), MixerActivity::class.java)
            startActivity(intent)
        }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThirtFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirtFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}