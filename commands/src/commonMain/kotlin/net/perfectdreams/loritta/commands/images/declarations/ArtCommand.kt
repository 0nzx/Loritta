package net.perfectdreams.loritta.commands.images.declarations

import net.perfectdreams.loritta.commands.images.ArtExecutor
import net.perfectdreams.loritta.common.commands.CommandCategory
import net.perfectdreams.loritta.common.commands.declarations.CommandDeclaration
import net.perfectdreams.loritta.common.locale.LocaleKeyData
import net.perfectdreams.loritta.common.utils.toI18nHelper

object ArtCommand : CommandDeclaration {
    const val LOCALE_PREFIX = "commands.command.art"

    override fun declaration() = command(listOf("art", "arte"), CommandCategory.IMAGES, LocaleKeyData("$LOCALE_PREFIX.description").toI18nHelper()) {
        executor = ArtExecutor
    }
}