package com.example.balloongame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.collections.ArrayList
import kotlin.random.Random



class BalloonField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val redrawHandler = Handler()
    private val balloonGenerationHandler = Handler()



    private val balloons = ArrayList<Balloon>()
    private val paint = Paint()

    private var popped: (() -> Unit)? = null

    private val invalidateRunnable = Runnable {
        invalidate()
        scheduleNextRedraw()
    }

    private val balloonGenerationRunnable = Runnable {
        generateAndDrawCircle()
        scheduleNextBalloonGeneration()
    }

    private fun scheduleNextRedraw(){
        redrawHandler.postDelayed(invalidateRunnable, 500)
    }

    private fun scheduleNextBalloonGeneration(){
        balloonGenerationHandler.postDelayed(balloonGenerationRunnable, 500)
    }

    init {
        paint.style = Paint.Style.FILL

        startBalloonGenerationLoop()
    }

    fun setBaloonPopped(event: ()-> Unit){
        popped = event
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawBalloons(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        startDrawingLoop()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                return onUserTouched(event.x, event.y)
            }
        }
        return false
    }

    private fun onUserTouched(x: Float, y: Float): Boolean {
        val balloonsIterator = balloons.iterator()
        while (balloonsIterator.hasNext()) {
            val balloon = balloonsIterator.next()
            if (balloon.isTouched(x, y)) {
                balloonsIterator.remove()
                invalidate()
                popped?.invoke()
                return true
            }
        }

        return false
    }

    private fun startDrawingLoop(){
        redrawHandler.removeCallbacksAndMessages(null)
        scheduleNextRedraw()
    }

    private fun startBalloonGenerationLoop(){
        balloonGenerationHandler.removeCallbacksAndMessages(null)
        scheduleNextBalloonGeneration()
    }

    private fun generateAndDrawCircle() {
        Balloon.newValidatedBalloon(width, height, balloons)?.let {
            balloons.add(it)
        }
    }

    private fun drawBalloons(canvas: Canvas?) {
        canvas?.let {
            balloons.forEach { item ->
                paint.color = item.color
                canvas.drawCircle(item.x, item.y, item.radius, paint)
            }
        }
    }
}
