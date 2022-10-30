package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.utils.declarations

import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.CinnamonSlashCommandDeclarationWrapper
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.utils.morse.MorseFromExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.utils.morse.MorseToExecutor
import net.perfectdreams.loritta.common.commands.CommandCategory
import net.perfectdreams.loritta.common.locale.LanguageManager
import net.perfectdreams.loritta.i18n.I18nKeysData

class MorseCommand(languageManager: LanguageManager) : CinnamonSlashCommandDeclarationWrapper(languageManager) {
    companion object {
        val I18N_PREFIX = I18nKeysData.Commands.Command.Morse
    }

    override fun declaration() = slashCommand(I18N_PREFIX.Label, CommandCategory.UTILS, I18N_PREFIX.Description) {
        subcommand(I18N_PREFIX.ToMorseLabel, I18N_PREFIX.DescriptionToMorse) {
            executor = { MorseToExecutor(it) }
        }
        subcommand(I18N_PREFIX.FromMorseLabel, I18N_PREFIX.DescriptionFromMorse) {
            executor = { MorseFromExecutor(it) }
        }
    }
}