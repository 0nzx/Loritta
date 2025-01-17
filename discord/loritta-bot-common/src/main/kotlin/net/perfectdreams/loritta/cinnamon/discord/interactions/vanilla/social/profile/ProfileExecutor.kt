package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.social.profile

import dev.kord.common.entity.ButtonStyle
import dev.kord.core.entity.User
import net.perfectdreams.discordinteraktions.common.builder.message.MessageBuilder
import net.perfectdreams.discordinteraktions.common.builder.message.actionRow
import net.perfectdreams.discordinteraktions.common.commands.options.SlashCommandArguments
import net.perfectdreams.i18nhelper.core.I18nContext
import net.perfectdreams.loritta.morenitta.LorittaBot
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.ApplicationCommandContext
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.CinnamonSlashCommandExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.GuildApplicationCommandContext
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.options.LocalizedApplicationCommandOptions
import net.perfectdreams.loritta.cinnamon.discord.interactions.components.interactiveButtonWithDatabaseData
import net.perfectdreams.loritta.cinnamon.discord.interactions.components.loriEmoji
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.social.declarations.ProfileCommand
import net.perfectdreams.loritta.cinnamon.discord.utils.UserUtils
import net.perfectdreams.loritta.cinnamon.emotes.Emotes
import net.perfectdreams.loritta.i18n.I18nKeysData
import net.perfectdreams.loritta.morenitta.profile.ProfileDesignManager

class ProfileExecutor(loritta: LorittaBot) : CinnamonSlashCommandExecutor(loritta) {
    inner class Options : LocalizedApplicationCommandOptions(loritta) {
        val user = optionalUser("user", ProfileCommand.PROFILE_VIEW_I18N_PREFIX.Options.User.Text)
    }

    companion object {
        suspend fun createMessage(
            loritta: LorittaBot,
            i18nContext: I18nContext,
            sender: User,
            userToBeViewed: User,
            result: ProfileDesignManager.ProfileCreationResult
        ): suspend MessageBuilder.() -> (Unit) = {
            addFile("profile.${result.imageFormat.extension}", result.image.inputStream())

            if (userToBeViewed == sender) {
                actionRow {
                    interactiveButtonWithDatabaseData(
                        loritta,
                        ButtonStyle.Secondary,
                        ChangeAboutMeButtonExecutor,
                        ChangeAboutMeButtonData(
                            userToBeViewed.id,
                            result.aboutMe
                        )
                    ) {
                        label = i18nContext.get(I18nKeysData.Commands.Command.Profileview.ChangeAboutMe)
                        loriEmoji = Emotes.LoriReading
                    }
                }
            }
        }
    }

    override val options = Options()

    override suspend fun execute(context: ApplicationCommandContext, args: SlashCommandArguments) {
        val userToBeViewed = args[options.user] ?: context.user

        if (UserUtils.handleIfUserIsBanned(loritta, context, userToBeViewed))
            return

        context.deferChannelMessage()

        val guild = if (context is GuildApplicationCommandContext) loritta.kord.getGuild(context.guildId) else null

        val result = loritta.profileDesignManager.createProfile(
            loritta,
            context.i18nContext,
            context.locale,
            loritta.profileDesignManager.transformUserToProfileUserInfoData(context.user),
            loritta.profileDesignManager.transformUserToProfileUserInfoData(userToBeViewed),
            guild?.let { loritta.profileDesignManager.transformGuildToProfileGuildInfoData(it) }
        )

        val message = createMessage(loritta, context.i18nContext, context.user, userToBeViewed, result)

        context.sendMessage {
            message()
        }
    }
}
