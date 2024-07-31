import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun PulsatingBackgroundButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.shape,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    animationEnabled: Boolean = true,
    content: @Composable () -> Unit = {}
) {
    // Define two colors for the pulsating effect
    val initialColor = MaterialTheme.colorScheme.primary // Purple
    val targetColor = MaterialTheme.colorScheme.inversePrimary // Light Purple

    // State to control the color
    var isInitialColor by remember { mutableStateOf(true) }

    // Animate the color transition
    val animatedColor by animateColorAsState(
        targetValue = if (isInitialColor) initialColor else targetColor,
        animationSpec = tween(durationMillis = 2000) // Duration of the animation
    )

    if (animationEnabled) {
        // Switch colors back and forth
        LaunchedEffect(Unit) {
            while (true) {
                delay(1000) // Delay between color changes
                isInitialColor = !isInitialColor
            }
        }
    }

    // Button with animated background color
    Button(
        onClick = onClick,
        shape = shape,
        contentPadding = contentPadding,
        colors = ButtonDefaults.buttonColors(containerColor = animatedColor),
        modifier = modifier
    ) {
        content()
    }
}
