package com.gdmistry.otpview

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat

class OtpView : LinearLayoutCompat {
    private lateinit var editTextList: MutableList<AppCompatEditText>
    private var editTextCurrPos = 0
    private var count = 4

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    public fun setCount(count: Int) {
        this.count = count
        editTextCurrPos = 0
        removeAllViews()
        init()
    }

    public fun setCursorVisible() {
        for (editText in editTextList) {
            editText.isCursorVisible = true
        }
    }

    public fun setEditTextHint(hint: String) {
        for (editText in editTextList) {
            editText.hint = hint
        }
    }

    public fun setEditTextColor(color: Int) {
        for (editText in editTextList) {
            editText.setTextColor(ResourcesCompat.getColor(resources, color, null))
        }
    }

    public fun setEditTextHintColor(color: Int) {
        for (editText in editTextList) {
            editText.setHintTextColor(ResourcesCompat.getColor(resources, color, null))
        }
    }

    public fun setEditTextSize(size: Float) {
        for (editText in editTextList) {
            editText.textSize = size
        }
    }

    public fun validate(): Boolean {
        for (editText in editTextList) {
            if (editText.text.toString().isEmpty()) {
                return false
            }
        }
        return true
    }

    public fun setEditTextBackground(background: Int) {
        for (editText in editTextList) {
            editText.background = ResourcesCompat.getDrawable(resources, background, null)
        }
    }

    public fun hideUnderline() {
        for (editText in editTextList) {
            editText.background = null
        }
    }

    public fun setOtpText(text: String) {
        for (editText in editTextList) {
            editText.setText(text[0].toString())
        }
    }

    public fun getOtpText(): String {
        val otpTextString = StringBuilder()
        for (editText in editTextList) {
            otpTextString.append(editText.text)
        }
        return otpTextString.toString()
    }

    private fun init() {
        editTextList = mutableListOf()

        for (i in 0 until count) {
            editTextList.add(createEditText(i))
        }
    }

    private fun createEditText(index: Int): AppCompatEditText {
        val editText = AppCompatEditText(context)
        val editTextParams = LayoutParams(0, LayoutParams.WRAP_CONTENT)
        editTextParams.weight = 1f
        editText.layoutParams = editTextParams
        editText.filters = arrayOf(InputFilter.LengthFilter(1))
        editText.gravity = Gravity.CENTER
        editText.isCursorVisible = false
        editText.addTextChangedListener(OnTextChanged(index))
        editText.setOnKeyListener(OnDeleteKeyListener(index))
        editText.setPadding(32, 16, 32, 16)
        addView(editText)
        return editText
    }

    inner class OnDeleteKeyListener(private var editTextPosition: Int) : OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event?.action != KeyEvent.ACTION_DOWN)
                return true

            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (editTextCurrPos == editTextPosition) {
                    val prevPos = editTextPosition - 1
                    if (prevPos >= 0) {
                        editTextList[prevPos].setText("")
                        editTextList[prevPos].requestFocus()
                        editTextCurrPos = prevPos
                    }
                }
            }
            return false
        }
    }

    inner class OnTextChanged(private var editTextPosition: Int) : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (editTextCurrPos == editTextPosition) {
                if (p0 != null && p0.length == 1) {

                    val nextPos = editTextPosition + 1
                    if (nextPos <= editTextList.size - 1) {
                        editTextList[nextPos].requestFocus()

                        if (nextPos == editTextList.size) {
                            hideKeyboard()
                        }
                        editTextCurrPos = nextPos
                    }
                }
            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }

    fun hideKeyboard() {
        val view = this@OtpView
        val inputManager =
            this@OtpView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}