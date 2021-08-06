package net.perfectdreams.loritta.commands.videos.declarations

import net.perfectdreams.loritta.commands.videos.CarlyAaahExecutor
import net.perfectdreams.loritta.common.commands.CommandCategory
import net.perfectdreams.loritta.common.commands.declarations.CommandDeclaration
import net.perfectdreams.loritta.common.locale.LocaleKeyData
import net.perfectdreams.loritta.common.utils.toI18nHelper

object CarlyAaahCommand : CommandDeclaration {
    const val LOCALE_PREFIX = "commands.command.carlyaaah"

    override fun declaration() = command(listOf("carlyaaah"), CommandCategory.VIDEOS, LocaleKeyData("$LOCALE_PREFIX.description").toI18nHelper()) {
        executor = CarlyAaahExecutor
    }
}