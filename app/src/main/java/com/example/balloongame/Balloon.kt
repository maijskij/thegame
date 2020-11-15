package com.example.balloongame

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.math.pow
import kotlin.random.Random

data class Balloon(val x: Float, val y: Float, @ColorInt val color: Int, val radius: Float = RADIUS) {

    fun isTouched(touchedX: Float, touchedY: Float) =
        distanceBetween(touchedX, touchedY, x, y) <= RADIUS

    private fun noClashWith(existing: ArrayList<Balloon>): Boolean {
        existing.forEach {
            if (distanceBetween(it.x, it.y, x, y) < RADIUS * 2 + 40f) return false
        }

        return true
    }

    companion object {
        const val RADIUS = 120f
        private const val PADDING = 30

        fun newValidatedBalloon(width: Int, height: Int, existing: ArrayList<Balloon>): Balloon? {
            var newBalloon: Balloon? = null

            run loop@{
                repeat(30) {
                    val balloon = newRandomInstance(width, height)
                    if (balloon.noClashWith(existing)) {
                        newBalloon = balloon
                        return@loop
                    }
                }
            }

            return newBalloon
        }

        private fun newRandomInstance(width: Int, height: Int): Balloon {
            var x = width * Random.nextFloat()
            var y = height * Random.nextFloat()
            val border = RADIUS + PADDING

            if (x < border) {
                x = border
            }

            if (x > width - border) {
                x = width - border
            }

            if (y < border) {
                y = border
            }

            if (y > height - border) {
                y = height - border
            }

            return Balloon(x, y, randomColor())
        }

        private fun randomColor() =
            Color.argb(
                255,
                Random.nextInt(256),
                Random.nextInt(256),
                Random.nextInt(256))

        private fun distanceBetween(x: Float, y: Float, x2: Float, y2: Float): Float {

            val a = x - x2
            val b = y - y2

            return kotlin.math.sqrt(a.pow(2) + b.pow(2))
        }
    }
}