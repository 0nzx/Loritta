package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.declarations

import net.perfectdreams.loritta.common.locale.LanguageManager
import net.perfectdreams.loritta.common.utils.TodoFixThisData
import net.perfectdreams.loritta.i18n.I18nKeysData
import net.perfectdreams.loritta.morenitta.LorittaBot
import net.perfectdreams.loritta.common.commands.CommandCategory
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.CinnamonSlashCommandDeclarationWrapper
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.RoleplayAttackExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.RoleplayDanceExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.RoleplayHeadPatExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.RoleplayHighFiveExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.RoleplayHugExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.RoleplayKissExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.RoleplaySlapExecutor
import net.perfectdreams.randomroleplaypictures.client.RandomRoleplayPicturesClient

class RoleplayCommand(languageManager: LanguageManager) : CinnamonSlashCommandDeclarationWrapper(languageManager) {
    companion object {
        val I18N_PREFIX = I18nKeysData.Commands.Command.Roleplay
    }

    override fun declaration() = slashCommand(I18N_PREFIX.Label, CommandCategory.ROLEPLAY, TodoFixThisData) {
        subcommand(I18N_PREFIX.Hug.Label, I18N_PREFIX.Hug.Description) {
            executor = { RoleplayHugExecutor(it, it.randomRoleplayPicturesClient) }
        }

        subcommand(I18N_PREFIX.Kiss.Label, I18N_PREFIX.Kiss.Description) {
            executor = { RoleplayKissExecutor(it, it.randomRoleplayPicturesClient) }
        }

        subcommand(I18N_PREFIX.Slap.Label, I18N_PREFIX.Slap.Description) {
            executor = { RoleplaySlapExecutor(it, it.randomRoleplayPicturesClient) }
        }

        subcommand(I18N_PREFIX.Headpat.Label, I18N_PREFIX.Headpat.Description) {
            executor = { RoleplayHeadPatExecutor(it, it.randomRoleplayPicturesClient) }
        }

        subcommand(I18N_PREFIX.Highfive.Label, I18N_PREFIX.Highfive.Description) {
            executor = { RoleplayHighFiveExecutor(it, it.randomRoleplayPicturesClient) }
        }

        subcommand(I18N_PREFIX.Attack.Label, I18N_PREFIX.Attack.Description) {
            executor = { RoleplayAttackExecutor(it, it.randomRoleplayPicturesClient) }
        }

        subcommand(I18N_PREFIX.Dance.Label, I18N_PREFIX.Dance.Description) {
            executor = { RoleplayDanceExecutor(it, it.randomRoleplayPicturesClient) }
        }
    }
}