package com.dedan.rekowisatabali.util

fun remap(value: Float, low1: Float, high1: Float, low2: Float, high2: Float): Float {
    return low2 + (value - low1) * (high2 - low2) / (high1 - low1)
}