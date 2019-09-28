package life.inaction.assignment.spacexdemo.ui.common

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.popup_option.view.*
import life.inaction.assignment.spacexdemo.R
import life.inaction.assignment.spacexdemo.extension.dp2px

class OptionPopupWindow(
    context: Context,
    customWidth: Int,
    options: List<String>,
    onSelected: (Int) -> Unit
) : PopupWindow(context), LayoutContainer {

    override val containerView: View = LayoutInflater.from(context).inflate(R.layout.popup_option, null, false)

    init {
        contentView = containerView
        width = customWidth
        height = context.dp2px(50f * options.size).toInt() + (options.size - 1)

        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isOutsideTouchable = true
        isFocusable = true
        elevation = context.dp2px(4f)

        val layoutInflater = LayoutInflater.from(context)

        options.forEachIndexed { index, name ->
            val textView = layoutInflater.inflate(R.layout.item_option, containerView.containerLayout, false) as TextView
            textView.text = name
            textView.setOnClickListener {
                onSelected(index)
                dismiss()
            }
            containerView.containerLayout.addView(textView)
        }
    }
}

