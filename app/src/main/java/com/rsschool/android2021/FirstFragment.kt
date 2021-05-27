package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minVal: EditText? = null
    private var maxVal: EditText? = null
    private var sender:Sender? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is Sender){
            sender = context
        }
        else
            throw RuntimeException(context.toString() + "must implement Sender")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minVal = view.findViewById(R.id.min_value)
        maxVal = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = getString(R.string.result,result)

        generateButton?.setOnClickListener {
            try {
                val min = minVal?.text.toString().toInt()
                val max = maxVal?.text.toString().toInt()
                if(checkValues(min,max))
                    sender?.sendValues(min,max)
            }catch (e:NumberFormatException){
                Toast.makeText(context,"Please, enter min and max values", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun checkValues(min: Int,max: Int):Boolean{
        return when {
            min == max -> {
                Toast.makeText(context, "Min and max values can't be the same", Toast.LENGTH_SHORT)
                    .show()
                false
            }
            max < min -> {
                Toast.makeText(context, "Min value can't be larger than max", Toast.LENGTH_SHORT)
                    .show()
                false
            }
            else -> true
        }
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