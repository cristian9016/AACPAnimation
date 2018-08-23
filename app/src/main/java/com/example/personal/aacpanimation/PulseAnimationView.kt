package com.example.personal.aacpanimation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.LinearInterpolator

class PulseAnimationView : View {

    val COLOR_ADJUSTER = 5
    val ANIMATION_DURATION = 4000.toLong()
    val ANIMATION_DELAY = 1000.toLong()
    var mPulseAnimatorSet = AnimatorSet()
    var mRadius = 0f
    val mPaint = Paint()
    var mX = 0f
    var mY = 0f


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    fun setRadius(radius: Float) {
        this.mRadius = radius
        mPaint.color = Color.GREEN + ((radius.toInt())/COLOR_ADJUSTER)
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val growAnimator = ObjectAnimator.ofFloat(this,"radius",0f,width.toFloat())
        growAnimator.duration = ANIMATION_DURATION
        growAnimator.interpolator = LinearInterpolator()
        val shrinkAnimator = ObjectAnimator.ofFloat(this,"radius",width.toFloat(), 0f)
        shrinkAnimator.duration = ANIMATION_DURATION
        shrinkAnimator.interpolator = LinearOutSlowInInterpolator()
        shrinkAnimator.startDelay = ANIMATION_DELAY
        val repeatAnimator = ObjectAnimator.ofFloat(this,"radius",0f,width.toFloat())
        repeatAnimator.duration = ANIMATION_DURATION
        repeatAnimator.startDelay = ANIMATION_DELAY
        repeatAnimator.repeatCount = 1
        repeatAnimator.repeatMode = ValueAnimator.REVERSE
        mPulseAnimatorSet.play(growAnimator).before(shrinkAnimator)
        mPulseAnimatorSet.play(repeatAnimator).after(shrinkAnimator)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawCircle(mX,mY,mRadius,mPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event!!.actionMasked == MotionEvent.ACTION_DOWN){
            mX = event.x
            mY = event.y
        }
//        if(mPulseAnimatorSet != null && mPulseAnimatorSet.isRunning) mPulseAnimatorSet.cancel()
        mPulseAnimatorSet.start()
        return super.onTouchEvent(event)
    }
}
