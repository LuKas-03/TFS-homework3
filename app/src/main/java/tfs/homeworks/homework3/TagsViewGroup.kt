package tfs.homeworks.homework3

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import kotlin.math.min


class TagsViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val indentSize : Int
    private val gravity : Int
    private val tagsHeight : Int

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TagsViewGroup)
        gravity = typedArray.getInt(R.styleable.TagsViewGroup_android_layout_gravity, Gravity.LEFT)
        indentSize = typedArray.getDimensionPixelSize(R.styleable.TagsViewGroup_tvg_indent_size, 0)
        tagsHeight = typedArray.getDimensionPixelSize(R.styleable.TagsViewGroup_tvg_tags_height, 18)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var maxHeight = setMeasureChild(widthMeasureSpec)



        val heightMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(widthMeasureSpec)
        var newHeight = heightSize
        when (heightMode) {
            MeasureSpec.AT_MOST -> newHeight = min(heightSize, maxHeight)
            MeasureSpec.EXACTLY -> newHeight = heightSize
            MeasureSpec.UNSPECIFIED -> newHeight = maxHeight
        }

        setMeasuredDimension(widthSize, newHeight)
    }

    private fun setMeasureChild(widthMeasureSpec: Int): Int {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var contentHeight = indentSize / 2
        var contentWidth = indentSize / 2
        val tagsHeightSpec = MeasureSpec.makeMeasureSpec(tagsHeight, MeasureSpec.EXACTLY)

        for (index in 0 until childCount) {
            val child = getChildAt(index)
            measureChild(child, widthMeasureSpec, tagsHeightSpec)
            if (child.visibility != View.GONE) {
                if (contentWidth + child.width + (indentSize / 2) > widthSize) {
                    contentHeight += tagsHeight + indentSize
                    contentWidth = indentSize / 2
                }
                contentWidth += child.width + indentSize
            }
        }

        // убираем лишний отступ
        contentHeight -= indentSize / 2
        return contentHeight
    }
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (gravity == Gravity.LEFT) {
            setTagsLaoyoutForLeftGravity(l, t, r)
        }
        else {
            setTagsLaoyoutForRightGravity(l, t, r)
        }
    }

    private fun setTagsLaoyoutForLeftGravity(l: Int, t: Int, r: Int) {
        var left = l + indentSize / 2
        var top = t + indentSize / 2
        val width = r - l

        for (index in 0 until childCount) {
            val child = getChildAt(index)
            if (child.visibility == View.GONE) {
                child.layout(0, 0, 0, 0)
                continue
            }
            if (left + child.measuredWidth + (indentSize / 2) > width) {
                left = indentSize / 2
                top += tagsHeight + indentSize
            }
            child.layout(left, top, left + child.measuredWidth, top + tagsHeight)
            left += child.measuredWidth + indentSize
        }
    }

    private fun setTagsLaoyoutForRightGravity(l: Int, t: Int, r: Int) {
        var left = r - l - indentSize / 2
        var top = t + indentSize / 2

        for (index in 0 until childCount) {
            val child = getChildAt(index)
            if (child.visibility == View.GONE) {
                child.layout(0, 0, 0, 0)
                continue
            }
            if (left - child.measuredWidth + (indentSize / 2) <= l) {
                left = r - l - indentSize / 2
                top += tagsHeight + indentSize
            }
            child.layout(left - child.measuredWidth, top, left, top + tagsHeight)
            left -= (child.measuredWidth + indentSize)
        }
    }
}
