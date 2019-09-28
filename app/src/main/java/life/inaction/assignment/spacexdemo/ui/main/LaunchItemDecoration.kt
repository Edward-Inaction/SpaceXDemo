package life.inaction.assignment.spacexdemo.ui.main

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import life.inaction.assignment.spacexdemo.R
import life.inaction.assignment.spacexdemo.extension.dp2px
import kotlin.math.max

class LaunchItemDecoration(private val launchAdapter: LaunchAdapter) : RecyclerView.ItemDecoration() {

    private lateinit var mHeaderTextView: TextView
    private val mDividerPaint = Paint().apply {
        color = Color.parseColor("#cccccc")
    }

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        var headerHeight = 0

        if (position != RecyclerView.NO_POSITION) {
            headerHeight = getHeaderHeight(parent, position)
        }

        outRect.set(0, headerHeight, 0, 0)
    }

    private fun getHeaderHeight(parent: RecyclerView, position: Int): Int {
        val heightDp = if (hasHeader(position)) 40 else 0
        return parent.context.dp2px(heightDp.toFloat()).toInt()
    }

    private fun hasHeader(position: Int): Boolean {
        return launchAdapter.getHeaderText(position).isNotEmpty()
    }

    private fun getHeader(parent: RecyclerView, position: Int): View {
        if (::mHeaderTextView.isInitialized.not()) {
            mHeaderTextView = LayoutInflater.from(parent.context).inflate(R.layout.view_header, parent, false) as TextView

            val widthSpec = View.MeasureSpec.makeMeasureSpec(
                parent.measuredWidth,
                View.MeasureSpec.EXACTLY
            )
            val heightSpec = View.MeasureSpec.makeMeasureSpec(
                parent.measuredHeight,
                View.MeasureSpec.UNSPECIFIED
            )

            val childWidth = ViewGroup.getChildMeasureSpec(
                widthSpec,
                parent.paddingLeft + parent.paddingRight,
                mHeaderTextView.layoutParams.width
            )
            val childHeight = ViewGroup.getChildMeasureSpec(
                heightSpec,
                parent.paddingTop + parent.paddingBottom,
                mHeaderTextView.layoutParams.height
            )

            mHeaderTextView.measure(childWidth, childHeight)
            mHeaderTextView.layout(0, 0, mHeaderTextView.measuredWidth, mHeaderTextView.measuredHeight)
        }

        val text = launchAdapter.getHeaderText(position)

        mHeaderTextView.text = text

        return mHeaderTextView
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val count = parent.childCount
        for (layoutPos in 0 until count) {
            val child = parent.getChildAt(layoutPos)
            val adapterPos = parent.getChildAdapterPosition(child)

            var drawHeadPosition = adapterPos

            if (adapterPos != RecyclerView.NO_POSITION) {
                if (layoutPos == 0) {
                    drawHeadPosition = findHeader(adapterPos)
                }

                if (hasHeader(drawHeadPosition)) {
                    val header = getHeader(parent, drawHeadPosition)
                    canvas.save()

                    val left = child.left
                    val top = getHeaderTop(parent, child, drawHeadPosition, layoutPos)
                    canvas.translate(left.toFloat(), top.toFloat())

                    header.translationX = left.toFloat()
                    header.translationY = top.toFloat()
                    header.draw(canvas)
                    canvas.restore()
                }

                if (adapterPos != 0 && !hasHeader(adapterPos)) {
                    val left = child.left.toFloat()
                    val right = child.right.toFloat()
                    val top = child.top.toFloat()

                    if (top > parent.context.dp2px(40f)) {
                        canvas.drawLine(left, top, right, top, mDividerPaint)
                    }
                }
            }
        }
    }

    private fun findHeader(adapterPos: Int): Int {
        for (i in adapterPos downTo 0) {
            if (hasHeader(i)) {
                return i
            }
        }
        return 0
    }

    private fun getHeaderTop(parent: RecyclerView, child: View, adapterPos: Int, layoutPos: Int): Int {
        val headerHeight = getHeaderHeight(parent, adapterPos)
        var top = child.y.toInt() - headerHeight
        if (layoutPos == 0) {
            val count = parent.childCount
            // find next view with header and compute the offscreen push if needed
            for (i in 1 until count) {
                val adapterPosHere = parent.getChildAdapterPosition(parent.getChildAt(i))
                if (adapterPosHere != RecyclerView.NO_POSITION && hasHeader(adapterPosHere)) {
                    val next = parent.getChildAt(i)
                    val offset = next.y.toInt() - (headerHeight + getHeaderHeight(parent, adapterPosHere))
                    return if (offset < 0) {
                        offset
                    } else {
                        break
                    }
                }
            }

            top = max(0, top)
        }

        return top
    }
}
