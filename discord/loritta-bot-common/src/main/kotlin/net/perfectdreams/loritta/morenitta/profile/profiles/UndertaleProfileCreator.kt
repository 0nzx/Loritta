package net.perfectdreams.loritta.morenitta.profile.profiles

import dev.kord.common.entity.Snowflake
import net.perfectdreams.loritta.morenitta.LorittaBot
import net.perfectdreams.loritta.morenitta.dao.Profile
import net.perfectdreams.loritta.morenitta.utils.*
import net.perfectdreams.loritta.common.locale.BaseLocale
import net.perfectdreams.loritta.deviousfun.entities.Guild
import net.perfectdreams.i18nhelper.core.I18nContext
import net.perfectdreams.loritta.morenitta.profile.ProfileGuildInfoData
import net.perfectdreams.loritta.morenitta.profile.ProfileUserInfoData
import net.perfectdreams.loritta.morenitta.profile.ProfileUtils
import net.perfectdreams.loritta.morenitta.utils.extensions.readImage
import java.awt.*
import java.awt.image.BufferedImage
import java.io.File

class UndertaleProfileCreator(loritta: LorittaBot) : StaticProfileCreator(loritta, "undertaleBattle") {
    override suspend fun create(
        sender: ProfileUserInfoData,
        user: ProfileUserInfoData,
        userProfile: Profile,
        guild: ProfileGuildInfoData?,
        badges: List<BufferedImage>,
        locale: BaseLocale,
        i18nContext: I18nContext,
        background: BufferedImage,
        aboutMe: String,
        allowedDiscordEmojis: List<Snowflake>?
    ): BufferedImage {
        val profileWrapper = readImage(File(LorittaBot.ASSETS, "profile/undertale/profile_wrapper.png"))

        val determinationMono = Constants.DETERMINATION_MONO

        val base = BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB) // Base
        val graphics = base.graphics.enableFontAntiAliasing()

        graphics.drawImage(background.getScaledInstance(800, 600, BufferedImage.SCALE_SMOOTH), 0, 0, null)

        graphics.font = determinationMono.deriveFont(Font.PLAIN, 22f)
        graphics.color = Color.WHITE

        val avatar = LorittaUtils.downloadImage(loritta, user.avatarUrl)!!
            .getScaledInstance(159, 159, BufferedImage.SCALE_SMOOTH)

        graphics.drawImage(profileWrapper, 0, 0, null)

        drawAvatar(avatar, graphics)

        graphics.drawText(loritta, "* ${user.name} appears!", 56, 347) // Nome do usuário

        drawReputations(user, graphics)

        drawBadges(badges, graphics)

        drawMarriageStatus(userProfile, locale, graphics)

        val biggestStrWidth = drawUserInfo(user, userProfile, guild, graphics)

        drawAboutMeWrapSpaces(
            graphics,
            graphics.fontMetrics,
            aboutMe,
            56,
            375,
            751 - biggestStrWidth - 4,
            600,
            allowedDiscordEmojis
        )

        return base.makeRoundedCorners(15)
    }

    fun drawAvatar(avatar: Image, graphics: Graphics) {
        graphics.drawImage(
            avatar.toBufferedImage(),
            321,
            153,
            null
        )
    }

    fun drawBadges(badges: List<BufferedImage>, graphics: Graphics) {
        var y = 3
        for (badge in badges) {
            graphics.drawImage(badge.getScaledInstance(32, 32, BufferedImage.SCALE_SMOOTH), 3, y, null)
            y += 35
        }
    }

    suspend fun drawReputations(user: ProfileUserInfoData, graphics: Graphics) {
        val font = graphics.font
        val reputations = ProfileUtils.getReputationCount(loritta, user)

        ImageUtils.drawCenteredString(graphics, "$reputations reps", Rectangle(654, 546, 104, 37), font)
    }

    suspend fun drawMarriageStatus(userProfile: Profile, locale: BaseLocale, graphics: Graphics) {
        ProfileUtils.getMarriageInfo(loritta, userProfile)?.let { (marriage, marriedWith) ->
            val font = graphics.font
            val marriedWithText = "${locale["profile.marriedWith"]} ${marriedWith.name}#${marriedWith.discriminator}"

            ImageUtils.drawCenteredString(graphics, marriedWithText, Rectangle(42, 543, 522, 47), font)
        }
    }

    suspend fun drawUserInfo(
        user: ProfileUserInfoData,
        userProfile: Profile,
        guild: ProfileGuildInfoData?,
        graphics: Graphics
    ): Int {
        val userInfo = mutableListOf<String>()
        userInfo.add("Global")
        val globalPosition = ProfileUtils.getGlobalExperiencePosition(loritta, userProfile)
        if (globalPosition != null)
            userInfo.add("#$globalPosition / ${userProfile.xp} XP")
        else
            userInfo.add("${userProfile.xp} XP")

        if (guild != null) {
            val localProfile = ProfileUtils.getLocalProfile(loritta, guild, user)

            val localPosition = ProfileUtils.getLocalExperiencePosition(loritta, localProfile)

            val xpLocal = localProfile?.xp

            // Iremos remover os emojis do nome da guild, já que ele não calcula direito no stringWidth
            userInfo.add(guild.name.replace(Constants.EMOJI_PATTERN.toRegex(), ""))
            if (xpLocal != null) {
                if (localPosition != null) {
                    userInfo.add("#$localPosition / $xpLocal XP")
                } else {
                    userInfo.add("$xpLocal XP")
                }
            } else {
                userInfo.add("???")
            }
        }

        val globalEconomyPosition = ProfileUtils.getGlobalEconomyPosition(loritta, userProfile)

        userInfo.add("Sonhos")
        if (globalEconomyPosition != null)
            userInfo.add("#$globalEconomyPosition / ${userProfile.money}")
        else
            userInfo.add("${userProfile.money}")

        val biggestStrWidth =
            graphics.fontMetrics.stringWidth(userInfo.maxByOrNull { graphics.fontMetrics.stringWidth(it) }!!)

        var y = 347
        for (line in userInfo) {
            graphics.drawText(loritta, line, 749 - biggestStrWidth - 2, y)
            y += 18
        }

        return biggestStrWidth
    }
}