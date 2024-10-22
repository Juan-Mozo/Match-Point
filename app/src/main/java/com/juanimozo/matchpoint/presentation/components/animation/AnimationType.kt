package com.juanimozo.matchpoint.presentation.components.animation

sealed class AnimationType(val duration: Int) {
    class FadeAnimation(duration: Int): AnimationType(duration)
    class SlideAnimation(duration: Int): AnimationType(duration)
    class ExpandAnimation(duration: Int): AnimationType(duration)
}