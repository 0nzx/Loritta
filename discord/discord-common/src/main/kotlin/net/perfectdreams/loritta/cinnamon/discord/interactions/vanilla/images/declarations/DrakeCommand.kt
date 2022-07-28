package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.declarations

import net.perfectdreams.gabrielaimageserver.client.GabrielaImageServerClient
import net.perfectdreams.loritta.cinnamon.locale.LanguageManager
import net.perfectdreams.loritta.cinnamon.i18n.I18nKeysData
import net.perfectdreams.loritta.cinnamon.discord.LorittaCinnamon
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.CommandCategory
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.CinnamonSlashCommandDeclarationWrapper
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.BolsoDrakeExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.DrakeExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.LoriDrakeExecutor

class DrakeCommand(languageManager: LanguageManager) : CinnamonSlashCommandDeclarationWrapper(languageManager) {
    companion object {
        val I18N_PREFIX = I18nKeysData.Commands.Command.Drake
    }

    override fun declaration() = slashCommand("drake", CommandCategory.IMAGES, I18N_PREFIX.Description) {
        subcommand("drake", I18N_PREFIX.Drake.Description) {
            executor = { DrakeExecutor(it, it.gabrielaImageServerClient) }
        }

        subcommand("bolsonaro", I18N_PREFIX.Bolsonaro.Description) {
            executor = { BolsoDrakeExecutor(it, it.gabrielaImageServerClient) }
        }

        subcommand("lori", I18N_PREFIX.Lori.Description) {
            executor = { LoriDrakeExecutor(it, it.gabrielaImageServerClient) }
        }
    }
}