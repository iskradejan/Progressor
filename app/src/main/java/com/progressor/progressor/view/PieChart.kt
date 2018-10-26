package com.progressor.progressor.view

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet
import android.view.View;
import com.progressor.progressor.R

class PieChart @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var musclePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var fatPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bonePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var basePaint: Paint = Paint()

    private var muscle: Int = 0
    private var fat: Int = 0
    private var bone: Int = 0
    private var baseAngleValue: Int = 0

    private var graphTotals: Int = 0

    private var graph: RectF = RectF()

    init {
        basePaint.color = context.resources.getColor(R.color.white, null)
        basePaint.style = Paint.Style.FILL

        musclePaint.color = context.resources.getColor(R.color.baseMuscle, null)
        musclePaint.style = Paint.Style.FILL

        fatPaint.color = context.resources.getColor(R.color.baseFat, null)
        fatPaint.style = Paint.Style.FILL

        bonePaint.color = context.resources.getColor(R.color.baseBone, null)
        bonePaint.style = Paint.Style.FILL
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        baseAngleValue = 0
        val canvasWidth = width

        graph.set(0f, 0f, canvasWidth.toFloat(), canvasWidth.toFloat())
        canvas.drawArc(graph, 0f, 360f, true, basePaint)

        setGraph(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        var width: Int
        var height: Int

        if (widthMode == View.MeasureSpec.EXACTLY || widthMode == View.MeasureSpec.AT_MOST) {
            width = widthSize
        } else {
            width = -1
        }

        if (heightMode == View.MeasureSpec.EXACTLY || heightMode == View.MeasureSpec.AT_MOST) {
            height = heightSize
        } else {
            height = -1
        }

        if (height >= 0 && width >= 0) {
            width = Math.min(height, width)
        } else if (height >= 0) {
            width = height * 2
        } else {
            width = 0
        }

        //MUST CALL THIS
        setMeasuredDimension(width, width)
    }

    fun setValues(muscle: Int, fat: Int, bone: Int) {
        this.muscle = muscle
        this.fat = fat
        this.bone = bone

        graphTotals = muscle + fat + bone
    }

    private fun setGraph(canvas: Canvas) {
        setMuscleGraph(canvas)
        setFatGraph(canvas)
        setBoneGraph(canvas)
        setWhiteCircle(canvas)
        baseAngleValue = 0
    }

    private fun setMuscleGraph(canvas: Canvas) {
        val newAngle = getAngle(muscle)
        if (newAngle > 0) {
            canvas.drawArc(graph, baseAngleValue.toFloat(), Math.ceil(newAngle).toInt().toFloat(), true, musclePaint)
        }
        baseAngleValue += Math.ceil(newAngle).toInt()
    }

    private fun setFatGraph(canvas: Canvas) {
        val newAngle = getAngle(fat)
        if (newAngle > 0) {
            canvas.drawArc(graph, baseAngleValue.toFloat(), Math.ceil(newAngle).toInt().toFloat(), true, fatPaint)
        }
        baseAngleValue += Math.ceil(newAngle).toInt()
    }

    private fun setBoneGraph(canvas: Canvas) {
        val newAngle = getAngle(bone)
        if (newAngle > 0) {
            canvas.drawArc(graph, baseAngleValue.toFloat(), Math.ceil(newAngle).toInt().toFloat(), true, bonePaint)
        }
        baseAngleValue += Math.ceil(newAngle).toInt()
    }

    private fun setWhiteCircle(canvas: Canvas) {
        val cenPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        val radius = graph.width() / 2 - 50
        cenPaint.setStyle(Paint.Style.FILL)
        cenPaint.setColor(Color.WHITE)
        cenPaint.isAntiAlias = true
        canvas.drawCircle((graph.width().toInt() / 2).toFloat(), (graph.width().toInt() / 2).toFloat(), radius, cenPaint)
    }

    private fun getAngle(sectionGraphValue: Int): Double {
        return sectionGraphValue.toDouble() / graphTotals.toDouble() * 360
    }
}