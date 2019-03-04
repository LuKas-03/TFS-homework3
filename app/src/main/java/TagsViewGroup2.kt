import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import tfs.homeworks.homework3.R

@RemoteViews.RemoteView
class TagsViewGroup2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    init {
        inflate(context, R.layout.tags_view_group, this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var newWidth = widthSize
        when (widthMode) {
            MeasureSpec.AT_MOST -> newWidth = widthSize
            MeasureSpec.EXACTLY -> newWidth = widthSize
            MeasureSpec.UNSPECIFIED -> newWidth = widthSize
        }

        val heightMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(widthMeasureSpec)
        var newHeight = heightSize
        when (heightMode) {
            MeasureSpec.AT_MOST -> newHeight = heightSize
            MeasureSpec.EXACTLY -> newHeight = heightSize
            MeasureSpec.UNSPECIFIED -> newHeight = heightSize
        }


        for (index in 0 until childCount) {
            val child = getChildAt(index)
            if (child.visibility != View.GONE) {
                val childWidthSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.AT_MOST)
                val childHeightSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST)
                measureChild(child, childWidthSpec, childHeightSpec)
            }
        }

        setMeasuredDimension(newWidth, newHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var top = 0
        val width = r - l
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            if (child.visibility == View.GONE) {
                child.layout(0, 0, 0, 0)
                continue
            }
            if (left + child.measuredWidth > width) {
                left = 0
                top += child.measuredHeight
            }
            child.layout(left, top, left + child.measuredWidth, top + child.measuredHeight)
            left += child.measuredWidth
        }
    }

}