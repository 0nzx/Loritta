package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.discord.avatar

import dev.kord.common.Color
import dev.kord.common.entity.ButtonStyle
import dev.kord.common.entity.DiscordGuildMember
import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Icon
import dev.kord.rest.Image
import dev.kord.rest.json.JsonErrorCode
import dev.kord.rest.request.KtorRequestException
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import net.perfectdreams.discordinteraktions.common.builder.message.MessageBuilder
import net.perfectdreams.discordinteraktions.common.builder.message.actionRow
import net.perfectdreams.discordinteraktions.common.builder.message.embed
import net.perfectdreams.discordinteraktions.common.utils.footer
import net.perfectdreams.i18nhelper.core.I18nContext
import net.perfectdreams.loritta.i18n.I18nKeysData
import net.perfectdreams.loritta.morenitta.LorittaBot
import net.perfectdreams.loritta.cinnamon.discord.interactions.components.data.SingleUserComponentData
import net.perfectdreams.loritta.cinnamon.discord.interactions.components.interactiveButton
import net.perfectdreams.loritta.cinnamon.discord.utils.ComponentDataUtils
import net.perfectdreams.loritta.cinnamon.discord.utils.NotableUserIds
import net.perfectdreams.loritta.cinnamon.discord.utils.UserUtils
import net.perfectdreams.loritta.cinnamon.discord.utils.toKordColor
import net.perfectdreams.loritta.common.utils.LorittaColors
import kotlin.time.Duration.Companion.minutes

object UserDataUtils {
    suspend fun getInteractionDataOrRetrieveViaRestIfItDoesNotExist(
        loritta: LorittaBot,
        decodedInteractionData: SwitchAvatarInteractionIdData,
        isLookingGuildProfileAvatar: Boolean
    ): ViewingUserAvatarData {
        val storedInteractionData = loritta.pudding.interactionsData.getInteractionData(decodedInteractionData.interactionDataId)

        if (storedInteractionData == null) {
            // ID is not present, try pulling the user data via REST
            val guildId = decodedInteractionData.guildId

            var member: DiscordGuildMember? = null
            val user = loritta.rest.user.getUser(decodedInteractionData.viewingAvatarOfId)

            if (guildId != null)
                try {
                    member = loritta.rest.guild.getGuildMember(guildId, decodedInteractionData.viewingAvatarOfId)
                } catch (e: KtorRequestException) {
                    if (e.error?.code != JsonErrorCode.UnknownMember)
                        throw e
                }

            val memberAvatarHash = member?.avatar?.value
            // If we tried looking at the user's guild profile avatar, but if it doesn't exist, fallback to global user avatar data
            return if (isLookingGuildProfileAvatar && memberAvatarHash != null) {
                ViewingGuildProfileUserAvatarData(
                    user.username,
                    user.discriminator.toInt(),
                    user.avatar,
                    memberAvatarHash
                )
            } else {
                ViewingGlobalUserAvatarData(
                    user.username,
                    user.discriminator.toInt(),
                    user.avatar,
                    memberAvatarHash
                )
            }
        }

        return Json.decodeFromJsonElement(storedInteractionData)
    }

    /**
     * Creates an avatar preview embed from the data in [data]
     */
    fun createAvatarPreviewMessage(
        loritta: LorittaBot,
        i18nContext: I18nContext,
        lorittaId: Snowflake,
        interactionData: SwitchAvatarInteractionIdData,
        data: ViewingUserAvatarData
    ): suspend MessageBuilder.() -> (Unit) {
        val now = Clock.System.now()

        // Unwrap our data so it is easier to access
        val userId = interactionData.viewingAvatarOfId
        val userName = data.userName
        val userDiscriminator = data.discriminator

        val userAvatar: Icon?
        val avatarHash: String?

        // Convert our stored data into Icons, and store them in the "avatarHash" variable so we can check later if it is a GIF or not
        when (data) {
            is ViewingGuildProfileUserAvatarData -> {
                avatarHash = data.memberAvatarId
                userAvatar = Icon.MemberAvatar(
                    interactionData.guildId!!,
                    userId,
                    data.memberAvatarId,
                    loritta.interaKTions.kord
                )
            }
            is ViewingGlobalUserAvatarData -> {
                avatarHash = data.userAvatarId
                userAvatar = UserUtils.createUserAvatarOrDefaultUserAvatar(
                    loritta.interaKTions.kord,
                    interactionData.viewingAvatarOfId,
                    avatarHash,
                    userDiscriminator
                )
            }
        }

        // Create the avatar message
        return {
            embed {
                title = "\uD83D\uDDBC $userName"

                // Specific User Avatar Easter Egg Texts
                val easterEggFooterTextKey = when {
                    // Easter Egg: Looking up yourself
                    interactionData.userId == interactionData.viewingAvatarOfId -> I18nKeysData.Innercommands.Innercommand.Inneruser.Inneravatar.YourselfEasterEgg

                    // Easter Egg: Loritta/Application ID
                    // TODO: Show who made the fan art during the Fan Art Extravaganza
                    userId == lorittaId -> I18nKeysData.Innercommands.Innercommand.Inneruser.Inneravatar.LorittaEasterEgg

                    // Easter Egg: Pantufa
                    userId == NotableUserIds.PANTUFA -> I18nKeysData.Innercommands.Innercommand.Inneruser.Inneravatar.PantufaEasterEgg

                    // Easter Egg: Gabriela
                    userId == NotableUserIds.GABRIELA -> I18nKeysData.Innercommands.Innercommand.Inneruser.Inneravatar.GabrielaEasterEgg

                    // Easter Egg: Carl-bot
                    userId == NotableUserIds.CARLBOT -> I18nKeysData.Innercommands.Innercommand.Inneruser.Inneravatar.CarlbotEasterEgg

                    // Easter Egg: Dank Memer
                    userId == NotableUserIds.DANK_MEMER -> I18nKeysData.Innercommands.Innercommand.Inneruser.Inneravatar.DankMemerEasterEgg

                    // Easter Egg: Mantaro
                    userId == NotableUserIds.MANTARO -> I18nKeysData.Innercommands.Innercommand.Inneruser.Inneravatar.MantaroEasterEgg

                    // Easter Egg: Erisly
                    userId == NotableUserIds.ERISLY -> I18nKeysData.Innercommands.Innercommand.Inneruser.Inneravatar.ErislyEasterEgg

                    // Easter Egg: Kuraminha
                    userId == NotableUserIds.KURAMINHA -> I18nKeysData.Innercommands.Innercommand.Inneruser.Inneravatar.KuraminhaEasterEgg

                    // Nothing else, just use null
                    else -> null
                }

                // If the text is present, set it as the footer!
                if (easterEggFooterTextKey != null)
                    footer(i18nContext.get(easterEggFooterTextKey))

                color = LorittaColors.DiscordBlurple.toKordColor()

                // This should NEVER be null at this point!
                val imageUrl = userAvatar.let {
                    it.cdnUrl.toUrl {
                        this.format = userAvatar.format
                        this.size = Image.Size.Size2048
                    }
                }

                image = imageUrl

                actionRow {
                    // "Open Avatar in Browser" button
                    linkButton(
                        url = imageUrl
                    ) {
                        label = i18nContext.get(I18nKeysData.Innercommands.Innercommand.Inneruser.Inneravatar.OpenAvatarInBrowser)
                    }

                    // Additional avatar switch buttons, if needed
                    val memberAvatarId = data.memberAvatarId
                    if (data is ViewingGuildProfileUserAvatarData) {
                        // If the user is currently viewing the user's guild profile avatar, add a button to switch to the original avatar
                        val id = loritta.pudding.interactionsData.insertInteractionData(
                            Json.encodeToJsonElement<ViewingUserAvatarData>(
                                ViewingGlobalUserAvatarData(
                                    data.userName,
                                    data.discriminator,
                                    data.userAvatarId,
                                    data.memberAvatarId
                                )
                            ).jsonObject,
                            now,
                            now + 15.minutes // Expires after 15m
                        )

                        interactiveButton(
                            ButtonStyle.Primary,
                            SwitchToGlobalAvatarExecutor,
                            ComponentDataUtils.encode(
                                interactionData
                                    .copy(interactionDataId = id)
                            )
                        ) {
                            label = i18nContext.get(I18nKeysData.Innercommands.Innercommand.Inneruser.Inneravatar.ViewUserGlobalAvatar)
                        }
                    } else if (memberAvatarId != null) {
                        // If the user is currently viewing the user's avatar, and the user has a guild profile avatar, add a button to switch to the guild profile avatar
                        val id = loritta.pudding.interactionsData.insertInteractionData(
                            Json.encodeToJsonElement<ViewingUserAvatarData>(
                                ViewingGuildProfileUserAvatarData(
                                    data.userName,
                                    data.discriminator,
                                    data.userAvatarId,
                                    memberAvatarId
                                )
                            ).jsonObject,
                            now,
                            now + 15.minutes // Expires after 15m
                        )

                        interactiveButton(
                            ButtonStyle.Primary,
                            SwitchToGuildProfileAvatarExecutor,
                            ComponentDataUtils.encode(
                                interactionData
                                    .copy(interactionDataId = id)
                            )
                        ) {
                            label = i18nContext.get(I18nKeysData.Innercommands.Innercommand.Inneruser.Inneravatar.ViewUserGuildProfileAvatar)
                        }
                    }
                }
            }
        }
    }

    /**
     * We don't store the data in [ViewingUserAvatarData] here because it won't fit in the Custom ID! (avatar IDs are one hecc of a chonky boi)
     */
    @Serializable
    data class SwitchAvatarInteractionIdData(
        override val userId: Snowflake,
        val viewingAvatarOfId: Snowflake,
        val guildId: Snowflake?,
        val targetType: MessageTargetType,
        val interactionDataId: Long
    ) : SingleUserComponentData

    @Serializable
    sealed class ViewingUserAvatarData {
        abstract val userName: String
        abstract val discriminator: Int
        abstract val userAvatarId: String?
        abstract val memberAvatarId: String?
    }

    @Serializable
    data class ViewingGlobalUserAvatarData(
        override val userName: String,
        override val discriminator: Int,
        override val userAvatarId: String?,
        override val memberAvatarId: String?
    ) : ViewingUserAvatarData()

    @Serializable
    data class ViewingGuildProfileUserAvatarData(
        override val userName: String,
        override val discriminator: Int,
        override val userAvatarId: String?,
        override val memberAvatarId: String
    ) : ViewingUserAvatarData()
}