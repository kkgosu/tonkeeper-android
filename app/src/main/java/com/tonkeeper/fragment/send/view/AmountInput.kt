package com.tonkeeper.fragment.send.view

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText

class AmountInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.editTextStyle,
) : AppCompatEditText(context, attrs, defStyle), TextWatcher {

    private companion object {
        private const val maxTextSize = 40f
    }

    init {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, maxTextSize)
        addTextChangedListener(this)
    }

    private fun checkTextSize(s: CharSequence) {
        val maxWidth = measuredWidth
        val textWidth = paint.measureText(s, 0, s.length)
        if (textWidth >= maxWidth) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - 2)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }

    override fun afterTextChanged(s: Editable?) {
        val editable = s ?: return
        if (editable.length > 1 && editable.startsWith("0") && !editable.startsWith("0.")) {
            editable.delete(0, 1)
        }
    }

    fun setMaxLength(maxLength: Int) {
        filters = arrayOf(InputFilter.LengthFilter(maxLength))
    }
}