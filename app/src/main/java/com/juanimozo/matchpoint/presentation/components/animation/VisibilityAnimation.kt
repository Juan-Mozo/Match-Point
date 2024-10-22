package com.juanimozo.matchpoint.presentation.components.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun VisibilityAnimation(
    condition: Boolean,
    animationType: AnimationType,
    content: @Composable ()-> Unit
) {
    AnimatedVisibility(
        visible = condition,
        enter = getEnterTransition(animationType),
        exit = getExitTransition(animationType)
    ) {
        content()
    }
}

@Preview
@Composable
fun PreviewVisibilityAnimation() {
    VisibilityAnimation(true, AnimationType.ExpandAnimation(300)) {
        Text("Hello world!")
    }
}

private fun getEnterTransition(animationType: AnimationType): EnterTransition {
    return when (animationType) {
        is AnimationType.ExpandAnimation -> {
            expandIn(
                animationSpec = tween(
                    durationMillis = animationType.duration,
                    easing = FastOutSlowInEasing
                ),
                expandFrom = Alignment.Center
            )
        }
        is AnimationType.FadeAnimation -> {
            fadeIn(
                animationSpec = tween(
                    durationMillis = animationType.duration,
                    easing = FastOutSlowInEasing
                )
            )
        }
        is AnimationType.SlideAnimation -> {
            slideInHorizontally(
                animationSpec = tween(
                    durationMillis = animationType.duration,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }
}

private fun getExitTransition(animationType: AnimationType): ExitTransition {
    return when (animationType) {
        is AnimationType.ExpandAnimation -> {
            shrinkOut(
                animationSpec = tween(
                    durationMillis = animationType.duration,
                    easing = FastOutSlowInEasing
                ),
                shrinkTowards = Alignment.Center
            )
        }
        is AnimationType.FadeAnimation -> {
            fadeOut(
                animationSpec = tween(
                    durationMillis = animationType.duration,
                    easing = FastOutSlowInEasing
                )
            )
        }
        is AnimationType.SlideAnimation -> {
            slideOutHorizontally(
                animationSpec = tween(
                    durationMillis = animationType.duration,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }
}