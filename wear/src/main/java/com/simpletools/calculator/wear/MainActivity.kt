package com.simpletools.calculator.wear

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.simplemobiletools.commons.extensions.performHapticFeedback
import com.simpletools.calculator.commons.extensions.config
import com.simpletools.calculator.commons.helpers.*
import kotlinx.android.synthetic.main.activity_main.*
import me.grantland.widget.AutofitHelper


class MainActivity : WearableActivity(), Calculator {

    override fun displayToast(message: String) {

    }

    lateinit var calc: CalculatorImpl
    private var vibrateOnButtonPress = true

    lateinit var mDetector: GestureDetector


    class SimpleGestureListener(val activity: MainActivity) : GestureDetector.SimpleOnGestureListener() {

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            if(Math.abs(velocityY) > Math.abs(velocityX)){
                if (e2!!.y > e1!!.y) {
                    Log.d("debug","DOWN")
                    activity.showOperations()
                    // direction up
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
            println("LONG PRESS")
        }
    }

    override fun setValue(value: String) {
        result.text = value
    }

    override fun setFormula(value: String) {}

    override fun setClear(value: String) {
        btn_clear.text = value
    }

    override fun getResult(): String {
        return result.text.toString()
    }

    override fun getFormula(): String {
        return ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calc = CalculatorImpl(this)
        vibrateOnButtonPress = config.vibrateOnButtonPress

        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }

        mDetector = GestureDetector(
                this, SimpleGestureListener(this))

        btn_clear.setOnClickListener { calc.handleClear(); checkHaptic(it) }
        btn_clear.setOnLongClickListener { calc.handleReset(); true }

        btn_multiply.setOnClickListener { calc.handleOperation(MULTIPLY); showNumpad() }
        btn_minus.setOnClickListener { calc.handleOperation(MINUS); showNumpad()}
        btn_divide.setOnClickListener { calc.handleOperation(DIVIDE);  showNumpad()}
        btn_plus.setOnClickListener { calc.handleOperation(PLUS);  showNumpad()}

        btn_equals.setOnClickListener { calc.handleEquals(); }

        AutofitHelper.create(result)
        // Enables Always-on
        setAmbientEnabled()
    }

    fun showOperations(){
        val linearLayout = findViewById(R.id.operation) as LinearLayout
        linearLayout.setVisibility(View.VISIBLE);
        val resultHeader = findViewById(R.id.result_header) as LinearLayout
        resultHeader.setVisibility(View.GONE);
        val numPad= findViewById(R.id.num_pad) as LinearLayout
        numPad.setVisibility(View.GONE);
    }

    fun showNumpad(){
        val linearLayout = findViewById(R.id.operation) as LinearLayout
        linearLayout.setVisibility(View.GONE);
        val resultHeader = findViewById(R.id.result_header) as LinearLayout
        resultHeader.setVisibility(View.VISIBLE);
        val numPad= findViewById(R.id.num_pad) as LinearLayout
        numPad.setVisibility(View.VISIBLE);
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Log.d("debug", "dispatchTouchEvent")
        this.mDetector?.onTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

}