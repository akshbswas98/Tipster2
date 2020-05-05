package com.example.tipster2

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
private const val TAG="MainActivity"
private const val INITIAL_TIP  = 5
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBarTip.progress = INITIAL_TIP
        tipPercent.text = "$INITIAL_TIP%"
        updateTipDescription(INITIAL_TIP)

        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            Log.i(TAG, "onProgressChanged $progress")
            tipPercent.text = "$progress%"
                updateTipDescription(progress)

                computeTipAndTotal()


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}


        })
        etBase.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            Log.i(TAG,    "afterTextChanged $s")
                computeTipAndTotal()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun updateTipDescription(tipPercent: Int) {
val tipDescription : String ;
        when(tipPercent){
            in 0..9 -> tipDescription ="Poor"
            in 10..16 -> tipDescription = "Okay"
            in 17..24  -> tipDescription = "Amazing"
            else -> tipDescription = "Superb"

        }
        tvTipDescription.text = tipDescription
        val color = ArgbEvaluator().evaluate(  tipPercent.toFloat() / seekBarTip.max,
            ContextCompat.getColor( this,R.color.colorWorstTip),
            ContextCompat.getColor( this,R.color.colorBestTip)) as Int
        tvTipDescription.setTextColor(color)

    }


    private fun computeTipAndTotal() {
        //Get Values of Tip percentage and BAse
    if (etBase.text.isEmpty()){
        tvTipAmount.text = ""
        tvTotalAmount.text = ""
        return
    }
    val baseAmount = etBase.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
        val tipAmount =  baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)
    }

}
