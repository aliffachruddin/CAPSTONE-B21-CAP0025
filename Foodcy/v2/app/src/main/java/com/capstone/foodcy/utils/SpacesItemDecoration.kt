package com.capstone.foodcy.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class SpacesItemDecoration(private val mSpace: Int) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = mSpace
        outRect.right = mSpace
        outRect.bottom = mSpace

        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = mSpace;
    }


}