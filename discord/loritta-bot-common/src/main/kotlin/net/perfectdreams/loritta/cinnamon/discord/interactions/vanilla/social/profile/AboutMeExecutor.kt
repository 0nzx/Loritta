package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.social.profile

import net.perfectdreams.loritta.cinnamon.emotes.Emotes
import net.perfectdreams.loritta.common.utils.Gender
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.ApplicationCommandContext
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.CinnamonSlashCommandExecutor
import net.perfectdreams.loritta.morenitta.LorittaBot
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.options.LocalizedApplicationCommandOptions
import net.perfectdreams.discordinteraktions.common.commands.options.SlashCommandArguments
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.social.declarations.GenderCommand
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.styled
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.social.declarations.ProfileCommand
import net.perfectdreams.loritta.cinnamon.discord.utils.getOrCreateUserProfile
import net.perfectdreams.loritta.common.utils.TodoFixThisData

class AboutMeExecutor(loritta: LorittaBot) : CinnamonSlashCommandExecutor(loritta) {
    inner class Options : LocalizedApplicationCommandOptions(loritta) {
        val aboutMe = string("about_me", ProfileCommand.ABOUT_ME_I18N_PREFIX.Options.Aboutme.Text)
    }

    override val options = Options()

    override suspend fun execute(context: ApplicationCommandContext, args: SlashCommandArguments) {
        context.deferChannelMessageEphemerally()

        val newAboutMe = args[options.aboutMe]

        val userSettings = context.loritta.pudding.users.getOrCreateUserProfile(context.user)
            .getProfileSettings()

        userSettings.setAboutMe(newAboutMe)

        context.sendEphemeralMessage {
            styled(
                context.i18nContext.get(ProfileCommand.ABOUT_ME_I18N_PREFIX.SuccessfullyChanged(newAboutMe)),
                Emotes.Tada
            )
        }
    }
}
