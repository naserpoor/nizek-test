package ir.alireza.naserpour.iconicbuttonlib

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.getDimensionOrThrow
import androidx.core.view.updateLayoutParams
import ir.alireza.naserpour.iconic_button_lib.R
import java.lang.Exception

class CustomizableGenericButton @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(ctx, attrs, defStyleAttr, defStyleRes) {

    private var icon: ImageView
    private var title: TextView
    private var description: TextView


    var titleSize: Float
        get() = title.textSize
        set(value) {
            title.textSize = value
        }

    var titleText: String
        get() = description.text.toString()
        set(value) {
            description.text = value
        }

    var descriptionSize: Float
        get() = description.textSize
        set(value) {
            description.textSize = value
        }

    var descriptionText: String
        get() = description.text.toString()
        set(value) {
            description.text = value
        }

    var iconDrawable: Drawable?
        get() = icon.drawable
        set(value) {
            icon.setImageDrawable(value)
        }

    var iconTextMargin: Int = 0
        set(value) {
            if (buttonType == 2 || value == 0) {
                setLayoutIconTextMargin(value)
            }
            field = value
        }

    private fun setLayoutIconTextMargin(value: Int) {
        title.updateLayoutParams<LayoutParams> {
            marginStart = value
        }
        description.updateLayoutParams<LayoutParams> {
            marginStart = value
        }
    }

    var iconSize: Int
        set(value) {
            icon.updateLayoutParams<LayoutParams> {
                width = value
                height = value
            }
        }
        get() = icon.layoutParams.width

    var buttonType: Int = 0
        set(value) {
            if (value in 0..2) {
                field = value
            }
            when (value) {
                0 -> {
                    icon.visibility = View.GONE
                    description.visibility = View.GONE
                    setLayoutIconTextMargin(0)
                }
                1 -> {
                    icon.visibility = View.GONE
                    description.visibility = View.VISIBLE
                    setLayoutIconTextMargin(0)
                }
                2 -> {
                    icon.visibility = View.VISIBLE
                    description.visibility = View.VISIBLE
                    setLayoutIconTextMargin(iconTextMargin)
                }
            }
        }

    var titleColor: Int
        get() = title.currentTextColor
        set(value) {
            title.setTextColor(value)
        }

    var descriptionColor: Int
        get() = description.currentTextColor
        set(value) {
            description.setTextColor(value)
        }

    var cornerRadius: Float = 0f
        set(value) {
            field = value
            createBg()
        }

    var touchEffectColor: Int = Color.LTGRAY
        set(value) {
            field = value
            createBg()
        }

    var buttonBackgroundColor: Int = Color.WHITE
        set(value) {
            field = value
            createBg()
        }

    private fun createBg() {
        background = RippleDrawable(
            ColorStateList.valueOf(touchEffectColor),
            ShapeDrawable(
                RoundRectShape(
                    FloatArray(8) { cornerRadius },
                    null,
                    null
                )
            ).apply {
                paint.color = buttonBackgroundColor
            },
            null
        )
    }

    init {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomizableGenericButton, defStyleAttr, defStyleRes)

        icon = ImageView(context)
        icon.id = R.id.icon
        icon.setImageDrawable(attributes.getDrawable(R.styleable.CustomizableGenericButton_icon))
        addView(icon)

        title = TextView(context)
        title.id = R.id.title
        title.setTypeface(title.typeface, Typeface.BOLD)
        title.text = attributes.getText(R.styleable.CustomizableGenericButton_title)
        title.gravity = Gravity.CENTER_HORIZONTAL
        title.setTextColor(attributes.getColor(R.styleable.CustomizableGenericButton_titleColor, title.currentTextColor))
        try {
            title.textSize = attributes.getDimensionOrThrow(R.styleable.CustomizableGenericButton_titleSize)
        } catch (e: Exception) {
        }
        addView(title)

        description = TextView(context)
        description.id = R.id.description
        description.text = attributes.getText(R.styleable.CustomizableGenericButton_description)
        description.gravity = Gravity.CENTER_HORIZONTAL
        description.setTextColor(
            attributes.getColor(
                R.styleable.CustomizableGenericButton_descriptionColor,
                description.currentTextColor
            )
        )
        try {
            description.textSize = attributes.getDimensionOrThrow(R.styleable.CustomizableGenericButton_descriptionSize)
        } catch (e: Exception) {
        }
        addView(description)

        ConstraintSet().let {
            it.clone(context, R.layout.iconic_button_constraints)
            it.applyTo(this)
        }


        cornerRadius = attributes.getDimension(R.styleable.CustomizableGenericButton_cornerRadius, 0f)
        buttonBackgroundColor = attributes.getColor(R.styleable.CustomizableGenericButton_buttonBackgroundColor, Color.WHITE)
        touchEffectColor = attributes.getColor(R.styleable.CustomizableGenericButton_touchEffectColor, Color.LTGRAY)

        iconTextMargin = attributes.getDimensionPixelSize(R.styleable.CustomizableGenericButton_iconTextMargin, 10)

        buttonType = attributes.getInt(R.styleable.CustomizableGenericButton_buttonType, 0)

        iconSize =
            attributes.getDimensionPixelSize(R.styleable.CustomizableGenericButton_iconSize, ViewGroup.LayoutParams.MATCH_PARENT)

        isClickable = true

        attributes.recycle()
    }

}