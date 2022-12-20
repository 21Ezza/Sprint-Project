package com.example.sprint_project.features.home.usercardview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.sprint_project.databinding.LayoutUserCardBinding

class UserCardView @JvmOverloads constructor(
    context: Context,
    attribbuteSet: AttributeSet? = null,
    styleAttribute: Int = 0,
): ConstraintLayout(context, attribbuteSet, styleAttribute) {
    private var userInterface: UserCardInterface? = null
    private var binding: LayoutUserCardBinding

    init {
        binding = LayoutUserCardBinding.inflate(LayoutInflater.from(context), this, true)

        binding.userIcon.setOnClickListener {
            userInterface?.onClickIcon()
        }
    }

    fun setListener(listener: UserCardInterface) {
        this.userInterface = listener
    }

    fun setUpperText(text: String) {
        binding.nameOfuser.text = text
    }

    fun setLowerText(text: String) {
        binding.rekNum.text = text
    }

    fun setTextColor(upperColorId: Int? = null, lowerColorId: Int? = null) {
        if (upperColorId != null)
            binding.nameOfuser.setTextColor(ContextCompat.getColor(context, upperColorId))


        if (lowerColorId != null)
            binding.rekNum.setTextColor(ContextCompat.getColor(context, lowerColorId))
    }

    fun setTextSize(topTextSize: Float? = null, bottomTextSize: Float? = null) {
        if (topTextSize != null)
            binding.nameOfuser.setTextSize(TypedValue.COMPLEX_UNIT_SP, topTextSize)

        if (bottomTextSize != null)
            binding.rekNum.setTextSize(TypedValue.COMPLEX_UNIT_SP, bottomTextSize)
    }

    fun setIcon(iconId: Int? = null) {
        if (iconId != null)
            binding.userIcon.setImageDrawable(ContextCompat.getDrawable(context, iconId))
    }

    fun destroyCard() {
        binding.userIcon.setOnClickListener {
            binding.userCard.isVisible = false
        }
    }
}