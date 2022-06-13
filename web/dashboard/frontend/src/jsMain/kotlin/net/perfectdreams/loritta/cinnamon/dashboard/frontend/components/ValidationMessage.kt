package net.perfectdreams.loritta.cinnamon.dashboard.frontend.components

import androidx.compose.runtime.Composable
import net.perfectdreams.loritta.cinnamon.dashboard.frontend.utils.SVGIconManager
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun ValidationMessage(
    status: ValidationMessageStatus,
    message: String,
) = ValidationMessage(
    status
) {
    Div {
        Text(message)
    }
}

@Composable
fun ValidationMessage(
    status: ValidationMessageStatus,
    block: @Composable () -> (Unit),
) {
    Div(attrs = {
        classes("validation", status.className)
    }) {
        block()
    }
}

@Composable
fun ValidationMessageWithIcon(
    status: ValidationMessageStatus,
    icon: SVGIconManager.SVGIcon,
    message: String,
) = ValidationMessage(status) {
    Div(attrs = { classes("icon") }) {
        UIIcon(icon)
    }

    Div {
        Text(message)
    }
}

enum class ValidationMessageStatus(val className: String) {
    SUCCESS("success"),
    ERROR("error"),
    NEUTRAL("neutral")
}