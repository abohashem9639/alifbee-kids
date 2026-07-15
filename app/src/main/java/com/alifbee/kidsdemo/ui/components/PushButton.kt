package com.alifbee.kidsdemo.ui.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.databinding.ViewPushButtonBinding

class PushButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val base: View
    private val face: LinearLayout
    private val icon: ImageView
    private val label: TextView

    private var depthPx = dp(DEFAULT_DEPTH_DP)
    private var cornerRadiusPx = dp(DEFAULT_RADIUS_DP).toFloat()
    private var faceColor = DEFAULT_FACE_COLOR
    private var baseColor = DEFAULT_BASE_COLOR
    private var customStyle = false

    init {
        val binding = ViewPushButtonBinding.inflate(LayoutInflater.from(context), this)
        base = binding.pbBase
        face = binding.pbFace
        icon = binding.pbIcon
        label = binding.pbText

        context.obtainStyledAttributes(attrs, R.styleable.PushButton).use { a ->
            label.text = a.getString(R.styleable.PushButton_pbText).orEmpty()
            label.setTextColor(a.getColor(R.styleable.PushButton_pbTextColor, Color.WHITE))

            val textSize = a.getDimension(R.styleable.PushButton_pbTextSize, -1f)
            if (textSize > 0f) label.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

            val iconRes = a.getResourceId(R.styleable.PushButton_pbIcon, 0)
            if (iconRes != 0) {
                icon.setImageResource(iconRes)
                icon.isVisible = true
            }

            faceColor = a.getColor(R.styleable.PushButton_pbFaceColor, DEFAULT_FACE_COLOR)
            baseColor = a.getColor(R.styleable.PushButton_pbBaseColor, DEFAULT_BASE_COLOR)
            cornerRadiusPx = a.getDimension(R.styleable.PushButton_pbCornerRadius, cornerRadiusPx)
            depthPx = a.getDimensionPixelSize(R.styleable.PushButton_pbDepth, depthPx)

            customStyle = a.hasValue(R.styleable.PushButton_pbFaceColor) ||
                a.hasValue(R.styleable.PushButton_pbBaseColor) ||
                a.hasValue(R.styleable.PushButton_pbCornerRadius)
        }

        isClickable = true
        isFocusable = true
        applyAppearance()
    }

    private fun applyAppearance() {
        if (customStyle) {
            base.background = buildBaseDrawable()
            face.background = buildFaceDrawable()
        } else {
            base.background = ContextCompat.getDrawable(context, R.drawable.push_button_base)
            face.background = ContextCompat.getDrawable(context, R.drawable.push_button_face)
        }
        base.updateLayoutParams<MarginLayoutParams> { topMargin = depthPx }
        face.updateLayoutParams<MarginLayoutParams> { bottomMargin = depthPx }
    }

    private fun buildBaseDrawable(): Drawable = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = cornerRadiusPx
        setColor(baseColor)
    }

    private fun buildFaceDrawable(): Drawable {
        val gradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(lighten(faceColor, 0.20f), faceColor)
        ).apply {
            cornerRadius = cornerRadiusPx
            setStroke(dp(2), baseColor)
        }
        val gloss = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(GLOSS_TOP, GLOSS_BOTTOM)
        ).apply {
            cornerRadius = cornerRadiusPx
        }
        return LayerDrawable(arrayOf(gradient, gloss))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val unspecified = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        face.measure(unspecified, unspecified)

        val desiredWidth = face.measuredWidth
        val desiredHeight = face.measuredHeight + depthPx

        val width = View.resolveSize(desiredWidth, widthMeasureSpec)
        val height = View.resolveSize(desiredHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)

        val exactWidth = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
        val layerHeight = MeasureSpec.makeMeasureSpec(height - depthPx, MeasureSpec.EXACTLY)
        face.measure(exactWidth, layerHeight)
        base.measure(exactWidth, layerHeight)
    }

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        face.animate()
            .translationY(if (pressed) depthPx.toFloat() else 0f)
            .setDuration(PRESS_DURATION)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    fun setText(value: CharSequence) {
        label.text = value
    }

    fun setFaceColor(color: Int) {
        faceColor = color
        baseColor = darken(color, 0.28f)
        customStyle = true
        base.background = buildBaseDrawable()
        face.background = buildFaceDrawable()
    }

    fun setLabelColor(color: Int) {
        label.setTextColor(color)
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private fun lighten(color: Int, fraction: Float): Int = Color.rgb(
        (Color.red(color) + (255 - Color.red(color)) * fraction).toInt(),
        (Color.green(color) + (255 - Color.green(color)) * fraction).toInt(),
        (Color.blue(color) + (255 - Color.blue(color)) * fraction).toInt()
    )

    private fun darken(color: Int, fraction: Float): Int = Color.rgb(
        (Color.red(color) * (1 - fraction)).toInt(),
        (Color.green(color) * (1 - fraction)).toInt(),
        (Color.blue(color) * (1 - fraction)).toInt()
    )

    companion object {
        private const val DEFAULT_DEPTH_DP = 6
        private const val DEFAULT_RADIUS_DP = 28
        private val DEFAULT_FACE_COLOR = 0xFF4B7DEC.toInt()
        private val DEFAULT_BASE_COLOR = 0xFF3D2B7A.toInt()
        private const val GLOSS_TOP = 0x40FFFFFF
        private const val GLOSS_BOTTOM = 0x00FFFFFF
        private const val PRESS_DURATION = 120L
    }
}
