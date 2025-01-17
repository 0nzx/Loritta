package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay

import dev.kord.common.Color
import dev.kord.common.entity.ButtonStyle
import dev.kord.common.entity.Snowflake
import net.perfectdreams.discordinteraktions.common.builder.message.actionRow
import net.perfectdreams.discordinteraktions.common.builder.message.create.MessageCreateBuilder
import net.perfectdreams.discordinteraktions.common.builder.message.embed
import net.perfectdreams.discordinteraktions.common.utils.footer
import net.perfectdreams.i18nhelper.core.I18nContext
import net.perfectdreams.loritta.cinnamon.emotes.Emotes
import net.perfectdreams.loritta.common.utils.Gender
import net.perfectdreams.loritta.i18n.I18nKeysData
import net.perfectdreams.loritta.morenitta.LorittaBot
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.mentionUser
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.declarations.RoleplayCommand
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.retribute.RetributeAttackButtonExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.retribute.RetributeDanceButtonExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.retribute.RetributeHeadPatButtonExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.retribute.RetributeHighFiveButtonExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.retribute.RetributeHugButtonExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.retribute.RetributeKissButtonExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.retribute.RetributeRoleplayData
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.retribute.RetributeSlapButtonExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay.source.SourcePictureExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.styled
import net.perfectdreams.loritta.cinnamon.discord.interactions.components.disabledButton
import net.perfectdreams.loritta.cinnamon.discord.interactions.components.interactiveButton
import net.perfectdreams.loritta.cinnamon.discord.interactions.components.loriEmoji
import net.perfectdreams.loritta.cinnamon.discord.utils.ComponentDataUtils
import net.perfectdreams.loritta.cinnamon.discord.utils.UserId
import net.perfectdreams.loritta.common.achievements.AchievementType
import net.perfectdreams.randomroleplaypictures.client.RandomRoleplayPicturesClient
import net.perfectdreams.randomroleplaypictures.common.data.api.PictureSource

object RoleplayUtils {
    val HUG_ATTRIBUTES = RoleplayActionAttributes(
        RoleplayCommand.I18N_PREFIX.Hug.Options.User.Text,
        RandomRoleplayPicturesClient::hug,
        RetributeHugButtonExecutor,
        I18nKeysData.Commands.Command.Roleplay.Hug::Response,
        Color(255, 141, 230),
        Emotes.Blush
    )

    val HEAD_PAT_ATTRIBUTES = RoleplayActionAttributes(
        RoleplayCommand.I18N_PREFIX.Headpat.Options.User.Text,
        RandomRoleplayPicturesClient::headPat,
        RetributeHeadPatButtonExecutor,
        I18nKeysData.Commands.Command.Roleplay.Headpat::Response,
        Color(156, 39, 176),
        Emotes.LoriPat
    )

    val HIGH_FIVE_ATTRIBUTES = RoleplayActionAttributes(
        RoleplayCommand.I18N_PREFIX.Highfive.Options.User.Text,
        RandomRoleplayPicturesClient::highFive,
        RetributeHighFiveButtonExecutor,
        I18nKeysData.Commands.Command.Roleplay.Highfive::Response,
        Color(165, 255, 76),
        Emotes.LoriHi
    )

    val SLAP_ATTRIBUTES = RoleplayActionAttributes(
        RoleplayCommand.I18N_PREFIX.Slap.Options.User.Text,
        RandomRoleplayPicturesClient::slap,
        RetributeSlapButtonExecutor,
        I18nKeysData.Commands.Command.Roleplay.Slap::Response,
        Color(244, 67, 54),
        Emotes.LoriPunch
    )

    val ATTACK_ATTRIBUTES = RoleplayActionAttributes(
        RoleplayCommand.I18N_PREFIX.Attack.Options.User.Text,
        RandomRoleplayPicturesClient::attack,
        RetributeAttackButtonExecutor,
        I18nKeysData.Commands.Command.Roleplay.Attack::Response,
        Color(244, 67, 54),
        Emotes.LoriRage
    )

    val DANCE_ATTRIBUTES = RoleplayActionAttributes(
        RoleplayCommand.I18N_PREFIX.Dance.Options.User.Text,
        RandomRoleplayPicturesClient::dance,
        RetributeDanceButtonExecutor,
        I18nKeysData.Commands.Command.Roleplay.Dance::Response,
        Color(255, 152, 0),
        Emotes.Dancer
    )

    val KISS_ATTRIBUTES = RoleplayActionAttributes(
        RoleplayCommand.I18N_PREFIX.Kiss.Options.User.Text,
        RandomRoleplayPicturesClient::kiss,
        RetributeKissButtonExecutor,
        I18nKeysData.Commands.Command.Roleplay.Kiss::Response,
        Color(233, 30, 99),
        Emotes.LoriKiss
    )

    val RETRIBUTABLE_ACTIONS_BY_LORITTA_EASTER_EGG = listOf(
        HUG_ATTRIBUTES,
        HEAD_PAT_ATTRIBUTES,
        HIGH_FIVE_ATTRIBUTES,
        DANCE_ATTRIBUTES
    )

    suspend fun handleRoleplayMessage(
        loritta: LorittaBot,
        i18nContext: I18nContext,
        data: RetributeRoleplayData,
        client: RandomRoleplayPicturesClient,
        roleplayActionAttributes: RoleplayActionAttributes
    ): RoleplayResponse {
        var (_, giver, receiver) = data
        var embedResponse = roleplayActionAttributes.embedResponse

        val achievements = mutableListOf<AchievementTarget>()

        // I will be honest, this is kind of a hack
        // However it doesn't really matter, does it? ;P
        //
        // Easter eggs to specific actions
        when (roleplayActionAttributes) {
            // ===[ KISS ]===
            KISS_ATTRIBUTES -> {
                if (receiver == loritta.config.loritta.discord.applicationId) {
                    return RoleplayResponse(listOf(AchievementTarget(giver, AchievementType.TRIED_KISSING_LORITTA))) {
                        styled(
                            i18nContext.get(I18nKeysData.Commands.Command.Roleplay.Kiss.ResponseLori),
                            Emotes.LoriBonk
                        )
                    }
                }

                if (receiver == giver) {
                    embedResponse = { giverMention, _ -> I18nKeysData.Commands.Command.Roleplay.Kiss.ResponseSelf(giverMention) }
                } else {
                    val giverMarriage = loritta.pudding.marriages.getMarriageByUser(UserId(giver))
                    val receiverMarriage = loritta.pudding.marriages.getMarriageByUser(UserId(receiver))

                    // "Talarico é o cara que cobiça a mulher do próximo e as vezes até dos amigos."
                    // "Grass cutter"/"Grass cutter" in english
                    if (receiverMarriage != null && giverMarriage?.id != receiverMarriage.id) {
                        // Talarico achievement
                        achievements.add(AchievementTarget(giver, AchievementType.GRASS_CUTTER))
                    }

                    achievements.add(AchievementTarget(giver, AchievementType.GAVE_FIRST_KISS))
                    achievements.add(AchievementTarget(receiver, AchievementType.RECEIVED_FIRST_KISS))
                }
            }

            // ===[ SLAP ]===
            SLAP_ATTRIBUTES -> {
                if (giver == receiver) {
                    embedResponse = { giverMention, _ -> I18nKeysData.Commands.Command.Roleplay.Slap.ResponseSelf(giverMention) }
                } else if (receiver == loritta.config.loritta.discord.applicationId) {
                    achievements.add(AchievementTarget(giver, AchievementType.TRIED_HURTING_LORITTA))

                    val oldGiver = giver
                    val oldReceiver = receiver

                    receiver = oldGiver
                    giver = oldReceiver

                    embedResponse = I18nKeysData.Commands.Command.Roleplay.Slap::ResponseLori
                }
            }

            // ===[ ATTACK ]===
            ATTACK_ATTRIBUTES -> {
                if (giver == receiver) {
                    embedResponse = { giverMention, _ -> I18nKeysData.Commands.Command.Roleplay.Attack.ResponseSelf(giverMention) }
                } else if (receiver == loritta.config.loritta.discord.applicationId) {
                    achievements.add(AchievementTarget(giver, AchievementType.TRIED_HURTING_LORITTA))

                    val oldGiver = giver
                    val oldReceiver = receiver

                    receiver = oldGiver
                    giver = oldReceiver

                    embedResponse = I18nKeysData.Commands.Command.Roleplay.Attack::ResponseLori
                }
            }

            // ===[ HUG ]===
            HUG_ATTRIBUTES -> {
                if (receiver == loritta.config.loritta.discord.applicationId) {
                    embedResponse = I18nKeysData.Commands.Command.Roleplay.Hug::ResponseLori
                } else if (giver == receiver) {
                    // If the giver is the same as the receiver, let's switch it to Loritta hugging the user
                    giver = loritta.config.loritta.discord.applicationId

                    embedResponse = I18nKeysData.Commands.Command.Roleplay.Hug::ResponseSelfLori
                }
            }

            // ===[ HEAD PAT ]===
            HEAD_PAT_ATTRIBUTES -> {
                if (receiver == loritta.config.loritta.discord.applicationId) {
                    embedResponse = I18nKeysData.Commands.Command.Roleplay.Headpat::ResponseLori
                } else if (giver == receiver) {
                    // If the giver is the same as the receiver, let's switch it to Loritta hugging the user
                    giver = loritta.config.loritta.discord.applicationId

                    embedResponse = I18nKeysData.Commands.Command.Roleplay.Headpat::ResponseSelfLori
                }
            }

            // ===[ HIGH FIVE ]===
            HIGH_FIVE_ATTRIBUTES -> {
                if (receiver == loritta.config.loritta.discord.applicationId) {
                    embedResponse = I18nKeysData.Commands.Command.Roleplay.Highfive::ResponseLori
                } else if (giver == receiver) {
                    // If the giver is the same as the receiver, let's switch it to Loritta hugging the user
                    giver = loritta.config.loritta.discord.applicationId

                    embedResponse = I18nKeysData.Commands.Command.Roleplay.Highfive::ResponseSelfLori
                }
            }

            // ===[ DANCE ]===
            DANCE_ATTRIBUTES -> {
                if (giver == receiver) {
                    embedResponse = { giverMention, _ -> I18nKeysData.Commands.Command.Roleplay.Dance.ResponseSelf(giverMention) }
                }
            }
        }

        val gender1 = loritta.pudding.users.getOrCreateUserProfile(UserId(giver)).getProfileSettings().gender
        val gender2 = loritta.pudding.users.getOrCreateUserProfile(UserId(receiver)).getProfileSettings().gender

        val result = roleplayActionAttributes.actionBlock.invoke(
            client,
            when (gender1) {
                Gender.MALE -> net.perfectdreams.randomroleplaypictures.common.Gender.MALE
                Gender.FEMALE -> net.perfectdreams.randomroleplaypictures.common.Gender.FEMALE
                Gender.UNKNOWN -> net.perfectdreams.randomroleplaypictures.common.Gender.UNKNOWN
            },
            when (gender2) {
                Gender.MALE -> net.perfectdreams.randomroleplaypictures.common.Gender.MALE
                Gender.FEMALE -> net.perfectdreams.randomroleplaypictures.common.Gender.FEMALE
                Gender.UNKNOWN -> net.perfectdreams.randomroleplaypictures.common.Gender.UNKNOWN
            }
        )

        val (picturePath, pictureSource) = result

        val pictureSourceData = if (pictureSource != null)
            loritta.encodeDataForComponentOrStoreInDatabase<PictureSource>(pictureSource)
        else null

        return RoleplayResponse(achievements) {
            embed {
                description = buildString {
                    if (data.combo >= 3) {
                        append("**_[COMBO ${data.combo}X]_**")
                        append(" ")
                    }

                    append(roleplayActionAttributes.embedEmoji.toString())
                    append(" ")
                    append("**${i18nContext.get(embedResponse.invoke(mentionUser(giver), mentionUser(receiver)))}**")
                }

                image = client.baseUrl + "/img/$picturePath"

                color = roleplayActionAttributes.embedColor

                footer(picturePath)
            }

            actionRow {
                if (giver != receiver) {
                    interactiveButton(
                        ButtonStyle.Primary,
                        i18nContext.get(RoleplayCommand.I18N_PREFIX.Retribute),
                        roleplayActionAttributes.retributionButtonDeclaration,
                        ComponentDataUtils.encode(
                            RetributeRoleplayData(
                                receiver,
                                receiver,
                                giver,
                                data.combo + 1
                            )
                        )
                    ) {
                        loriEmoji = roleplayActionAttributes.embedEmoji
                    }
                } else {
                    // Don't let someone retribute their own action
                    disabledButton(ButtonStyle.Primary) {
                        label = i18nContext.get(RoleplayCommand.I18N_PREFIX.Retribute)
                        loriEmoji = roleplayActionAttributes.embedEmoji
                    }
                }

                if (pictureSourceData != null) {
                    interactiveButton(
                        ButtonStyle.Secondary,
                        i18nContext.get(RoleplayCommand.I18N_PREFIX.PictureSource),
                        SourcePictureExecutor,
                        pictureSourceData
                    ) {
                        loriEmoji = Emotes.LoriReading
                    }
                } else {
                    disabledButton(ButtonStyle.Secondary) {
                        label = i18nContext.get(RoleplayCommand.I18N_PREFIX.PictureSource)
                        loriEmoji = Emotes.LoriReading
                    }
                }
            }
        }
    }

    data class RoleplayResponse(
        val achievements: List<AchievementTarget>,
        val builder: MessageCreateBuilder.() -> (Unit)
    )

    data class AchievementTarget(
        val target: Snowflake,
        val achievement: AchievementType
    )
}