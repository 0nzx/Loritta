package net.perfectdreams.loritta.cinnamon.dashboard.frontend.components

import androidx.compose.runtime.Composable
import net.perfectdreams.i18nhelper.core.I18nContext
import net.perfectdreams.loritta.cinnamon.dashboard.frontend.utils.LoadingGifs
import net.perfectdreams.loritta.i18n.I18nKeysData
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Span

@Composable
fun InlineLoadingSection(i18nContext: I18nContext) {
    Span(attrs = { classes("inline-loading-section") }) {
        Img(LoadingGifs.list.random())

        Div {
            LocalizedText(i18nContext, I18nKeysData.Website.Dashboard.Loading)
        }
    }
}