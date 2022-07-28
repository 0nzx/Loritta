package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.discord.declarations

import net.perfectdreams.loritta.cinnamon.locale.LanguageManager
import net.perfectdreams.loritta.cinnamon.i18n.I18nKeysData
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.CinnamonSlashCommandDeclarationWrapper
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.CommandCategory
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.discord.UserBannerExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.discord.avatar.UserAvatarSlashExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.discord.info.UserInfoSlashExecutor

class UserCommand(languageManager: LanguageManager) : CinnamonSlashCommandDeclarationWrapper(languageManager) {
    companion object {
        val I18N_PREFIX = I18nKeysData.Commands.Command.User
    }

    override fun declaration() = slashCommand("user", CommandCategory.DISCORD, I18N_PREFIX.Description) {
        subcommand("avatar", I18N_PREFIX.Avatar.Description) {
            executor = { UserAvatarSlashExecutor(it) }
        }

        subcommand("info", I18N_PREFIX.Info.Description) {
            executor = { UserInfoSlashExecutor(it, it.http) }
        }

        subcommand("banner", I18N_PREFIX.Banner.Description) {
            executor = { UserBannerExecutor(it) }
        }
    }
}