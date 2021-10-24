package com.quibbly.kontent.ui.composables

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.platform.debugInspectorInfo


@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.shimmer() = composed(
    inspectorInfo = debugInspectorInfo {
        name = "shimmerModifier"
    }
) {
    val baseColor = LocalContentColor.current
    val edgeColor = baseColor.copy(alpha = ContentAlpha.medium * 0.3f)
    val middleColor = baseColor.copy(alpha = ContentAlpha.medium * 0.16f)
    val colors = listOf(
        edgeColor,
        middleColor,
        edgeColor,
    )

    val infiniteTransition = rememberInfiniteTransition()
    val progress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    remember {
        ShimmerModifier(
            colors = colors,
            progress = progress
        )
    }
}

private class ShimmerModifier(
    val colors: List<Color>,
    val progress: State<Float>,
) : DrawModifier {

    override fun ContentDrawScope.draw() {
        val gWidth = size.width
        val gWidth2 = gWidth * 2
        val xStartShimmer = progress.value * (size.width + gWidth2) - gWidth
        val yStartShimmer = progress.value * (size.height + gWidth2) - gWidth

        drawRect(
            brush = linearGradient(
                start = Offset(xStartShimmer, yStartShimmer),
                end = Offset(xStartShimmer + gWidth, yStartShimmer + gWidth),
                colors = colors,
            ),
        )
    }

}