package com.cumiterbang.mypokedexmobile.utils

import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class CustomShimmerPlaceholder {
    companion object fun getPlaceholder(): ShimmerDrawable {
        val shimmer =
            Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.7f) //the alpha of the underlying children
                .setHighlightAlpha(0.8f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()

        return ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
    }
}