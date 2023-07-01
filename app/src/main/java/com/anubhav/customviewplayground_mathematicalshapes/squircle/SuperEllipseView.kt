package com.anubhav.customviewplayground_mathematicalshapes.squircle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.anubhav.customviewplayground_mathematicalshapes.R
import kotlin.math.*

/**
 * This Class Creates A View for the Equation x^4 +y^4 = r^4
 */

class SuperEllipseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var borderColor: Int = ContextCompat.getColor(context, R.color.black)
    private var borderWidth: Float = 4f
    private var fillColor: Int = ContextCompat.getColor(context, R.color.black)
    private var radius: Float = 100f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SuperEllipseView,
            0, 0
        ).apply {
            try {
                borderColor = getColor(R.styleable.SuperEllipseView_borderColor, borderColor)
                borderWidth = getDimension(R.styleable.SuperEllipseView_borderWidth, borderWidth)
                fillColor = getColor(R.styleable.SuperEllipseView_fillColor, fillColor)
                radius = getDimension(R.styleable.SuperEllipseView_radius, radius)
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.FILL
        paint.color = fillColor
        drawSuperEllipse(canvas, paint)

        paint.style = Paint.Style.STROKE
        paint.color = borderColor
        paint.strokeWidth = borderWidth
        drawSuperEllipse(canvas, paint)
    }

    private fun drawSuperEllipse(canvas: Canvas, paint: Paint) {
        path.reset()

        val centerX = width / 2f
        val centerY = height / 2f
        val steps = 360
        val dt = 2 * Math.PI / steps

        for (i in 0 until steps) {
            val t = i * dt
            val cosT = cos(t)
            val sinT = sin(t)

            val x = radius * cosT.sign * abs(cosT).pow(0.5)
            val y = radius * sinT.sign * abs(sinT).pow(0.5)

            if (i == 0) {
                path.moveTo(centerX + x.toFloat(), centerY + y.toFloat())
            } else {
                path.lineTo(centerX + x.toFloat(), centerY + y.toFloat())
            }
        }

        path.close()
        canvas.drawPath(path, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = (2 * radius).toInt()
        setMeasuredDimension(size, size)
    }

}
