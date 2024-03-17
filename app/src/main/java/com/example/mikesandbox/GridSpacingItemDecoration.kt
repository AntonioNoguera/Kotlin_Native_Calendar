package com.example.mikesandbox

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val context: Context,
    private val spanCount: Int,
    private val horizontalSpacingDp: Int,
    private val verticalSpacingDp: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val horizontalSpacingPx = dpToPx(horizontalSpacingDp)
        val verticalSpacingPx = dpToPx(verticalSpacingDp)

        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left = horizontalSpacingPx - column * horizontalSpacingPx / spanCount
            outRect.right = (column + 1) * horizontalSpacingPx / spanCount

            if (position < spanCount) {
                outRect.top = verticalSpacingPx
            }
            outRect.bottom = verticalSpacingPx
        } else {
            outRect.left = column * horizontalSpacingPx / spanCount
            outRect.right = horizontalSpacingPx - (column + 1) * horizontalSpacingPx / spanCount
            if (position >= spanCount) {
                outRect.top = verticalSpacingPx
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
}
