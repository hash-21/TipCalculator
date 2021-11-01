package com.example.tipcalculator

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener

private const val TAG="MainActivity"
private const val INITIAL_TIP_PERCENT=15
class MainActivity : AppCompatActivity() {
    private lateinit  var etBaseAmount: EditText
    private lateinit var seekBaarTip:SeekBar
    private lateinit var tvTipPercentLevel:TextView
    private lateinit var tvTipAmount:TextView
    private lateinit var tvTotalAmount:TextView
    private lateinit var tvTipDescription:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount=findViewById(R.id.etBaseAmount)
        seekBaarTip=findViewById(R.id.seekBarTip)
        tvTipPercentLevel=findViewById(R.id.tvTipPercentLevel)
        tvTipAmount=findViewById(R.id.tvTipAmount)
        tvTotalAmount=findViewById(R.id.tvTotalAmount)
        tvTipDescription=findViewById(R.id.tvTipDescription)

        seekBaarTip.progress= INITIAL_TIP_PERCENT
        tvTipPercentLevel.text="$INITIAL_TIP_PERCENT%"
        updateTipDescription(INITIAL_TIP_PERCENT)
        seekBaarTip.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG,"onProgressChanged$progress")
                tvTipPercentLevel.text="$progress%"
                computeTipandTotal()
                updateTipDescription(progress)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        etBaseAmount.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG,"aftertextchanged $s")
                computeTipandTotal()


            }

        })

    }

    private fun updateTipDescription(TipPercent: Int) {
        val tipDescription=when(TipPercent){
            in 0..9->"Poor"
            in 10..14->"Acceptable"
            in 15..19->"Good"
            in 20..24->"Great"
            else->"Amazing"


        }
        tvTipDescription.text=tipDescription
        val color=ArgbEvaluator().evaluate(
            TipPercent.toFloat()/seekBaarTip.max,
            ContextCompat.getColor(this,R.color.color_worst_tip),
            ContextCompat.getColor(this,R.color.color_best_tip)
        ) as Int
        tvTipDescription.setTextColor(color)

    }

    private fun computeTipandTotal() {
        if(etBaseAmount.text.isEmpty()){
            tvTipAmount.text=""
            tvTotalAmount.text=""
            return
        }
        val baseAmount=etBaseAmount.text.toString().toDouble()
        val tipPercent=seekBaarTip.progress

        val tipAmount=baseAmount*tipPercent/100
        val totalAmount=baseAmount+tipAmount

        tvTipAmount.text="%.2f".format(tipAmount)
        tvTotalAmount.text="%.2f".format(totalAmount)
    }
}