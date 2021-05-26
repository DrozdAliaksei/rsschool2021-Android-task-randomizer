package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minVal: EditText? = null
    private var maxVal: EditText? = null
    private lateinit var sender: Sender

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minVal = view.findViewById(R.id.min_value)
        maxVal = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        // TODO: val min = ...
        var min = minVal?.text.toString().toInt()
        // TODO: val max = ...
        var max = maxVal?.text.toString().toInt()

        generateButton?.setOnClickListener {
            // TODO: send min and max to the SecondFragment
            //activity?.sendValues(min,max) as MainActivity
            sender.sendValues(min,max)
        }
    }

    public fun setSender(x: Sender){
        sender = x
    }

    interface Sender {
        fun sendValues(min: Int,max: Int)
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            args.also { fragment.arguments = it }
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}