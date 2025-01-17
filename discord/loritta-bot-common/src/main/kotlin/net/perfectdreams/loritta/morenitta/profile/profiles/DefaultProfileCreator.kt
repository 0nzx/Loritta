package net.perfectdreams.loritta.morenitta.profile.profiles

import dev.kord.common.entity.Snowflake
import net.perfectdreams.loritta.morenitta.LorittaBot
import net.perfectdreams.loritta.morenitta.dao.Profile
import net.perfectdreams.loritta.morenitta.utils.ImageUtils
import net.perfectdreams.loritta.morenitta.utils.LorittaUtils
import net.perfectdreams.loritta.morenitta.utils.drawText
import net.perfectdreams.loritta.morenitta.utils.enableFontAntiAliasing
import net.perfectdreams.loritta.common.locale.BaseLocale
import net.perfectdreams.loritta.morenitta.utils.makeRoundedCorners
import net.perfectdreams.loritta.morenitta.utils.toBufferedImage
import net.perfectdreams.i18nhelper.core.I18nContext
import net.perfectdreams.loritta.morenitta.profile.ProfileGuildInfoData
import net.perfectdreams.loritta.morenitta.profile.ProfileUserInfoData
import net.perfectdreams.loritta.morenitta.profile.ProfileUtils
import net.perfectdreams.loritta.morenitta.utils.extensions.readImage
import java.awt.Font
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream

class DefaultProfileCreator(loritta: LorittaBot) : StaticProfileCreator(loritta, "modernBlurple") {
	fun drawSection(graphics: Graphics, latoBlack20: Font, latoBold20: Font, title: String, subtext: String, x: Int, y: Int): Pair<Int, Int> {
		graphics.font = latoBlack20
		graphics.drawText(loritta, title, x, y, 800 - 6)
		graphics.font = latoBold20
		graphics.drawText(loritta, subtext, x, y + 19, 800 - 6)
		return Pair(x, y + 19)
	}

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
		val profileWrapper = readImage(File(LorittaBot.ASSETS, "profile_wrapper_v4.png"))
		val profileWrapperOverlay = readImage(File(LorittaBot.ASSETS, "profile_wrapper_v4_overlay.png"))
		val base = BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB) // Base
		val graphics = base.graphics.enableFontAntiAliasing()

		val avatar = LorittaUtils.downloadImage(loritta, user.avatarUrl)!!.getScaledInstance(115, 115, BufferedImage.SCALE_SMOOTH)

		graphics.drawImage(background.getScaledInstance(800, 600, BufferedImage.SCALE_SMOOTH), 0, 0, null)

		graphics.drawImage(profileWrapper, 0, 0, null)
		graphics.drawImage(avatar.toBufferedImage().makeRoundedCorners(115), 6, 6, null)

		val latoRegular = loritta.graphicsFonts.latoRegular
		val latoBold = loritta.graphicsFonts.latoBold
		val latoBlack = loritta.graphicsFonts.latoBlack

		val latoBold38 = latoBold.deriveFont(38f)
		val latoRegular22 = latoBold.deriveFont(22f)
		val latoBlack20 = latoBlack.deriveFont(20f)
		val latoBold20 = latoBold.deriveFont(20f)

		graphics.font = latoBold38

		if (badges.isEmpty()) {
			graphics.drawText(loritta, user.name, 139, 71, 517 - 6)
		} else { // Caso exista badges, nós iremos alterar um pouquinho aonde o nome é desenhado
			graphics.drawText(loritta, user.name, 139, 61 - 4, 517 - 6)
			var x = 139
			var y = 70

			// E agora desenhar as badges
			for ((index, originalBadge) in badges.take(20).withIndex()) {
				val badge = originalBadge.getScaledInstance(27, 27, BufferedImage.SCALE_SMOOTH)
				graphics.drawImage(badge, x, y, null)
				x += 27 + 8

				if (index % 10 == 9) {
					x = 139
					y += 27
				}
			}
		}

		val globalPosition = ProfileUtils.getGlobalExperiencePosition(loritta, userProfile)
		if (globalPosition != null)
			drawSection(graphics, latoBlack20, latoBold20, "Global", "#$globalPosition / ${userProfile.xp} XP", 562, 21)
		else
			drawSection(graphics, latoBlack20, latoBold20, "Global", "${userProfile.xp} XP", 562, 21)

		if (guild != null) {
			val guildIcon = LorittaUtils.downloadImage(loritta, guild.iconUrl?.replace("jpg", "png") ?: "https://emojipedia-us.s3.amazonaws.com/thumbs/320/google/56/shrug_1f937.png")!!.getScaledInstance(38, 38, BufferedImage.SCALE_SMOOTH)

			val localProfile = ProfileUtils.getLocalProfile(loritta, guild, user)

			val localPosition = ProfileUtils.getLocalExperiencePosition(loritta, localProfile)

			val xpLocal = localProfile?.xp

			graphics.font = latoBlack20
			graphics.drawText(loritta, guild.name, 562, 61, 800 - 6)
			graphics.font = latoBold20
			if (xpLocal != null) {
				if (localPosition != null) {
					graphics.drawText(loritta, "#$localPosition / $xpLocal XP", 562, 78, 800 - 6)
				} else {
					graphics.drawText(loritta, "$xpLocal XP", 562, 78, 800 - 6)
				}
			} else {
				graphics.drawText(loritta, "???", 562, 78, 800 - 6)
			}

			graphics.drawImage(guildIcon.toBufferedImage().makeRoundedCorners(38), 520, 44, null)
		}

		val reputations = ProfileUtils.getReputationCount(loritta, user)

		drawSection(graphics, latoBlack20, latoBold20, "Reputação", "$reputations reps", 562, 102)

		val globalEconomyPosition = ProfileUtils.getGlobalEconomyPosition(loritta, userProfile)

		if (globalEconomyPosition != null)
			drawSection(graphics, latoBlack20, latoBold20, locale["economy.currency.name.plural"], "#$globalEconomyPosition / ${userProfile.money}", 562, 492)
		else
			drawSection(graphics, latoBlack20, latoBold20, locale["economy.currency.name.plural"], "${userProfile.money}", 562, 492)

		ProfileUtils.getMarriageInfo(loritta, userProfile)?.let { (marriage, marriedWith) ->
			val marrySection = readImage(File(LorittaBot.ASSETS, "profile/modern/marry.png"))
			graphics.drawImage(marrySection, 0, 0, null)

			drawSection(graphics, latoBlack20, latoBold20, locale["profile.marriedWith"], marriedWith.name + "#" + marriedWith.discriminator, 562, 533)
		}

		graphics.font = latoRegular22

		drawAboutMeWrapSpaces(graphics, graphics.fontMetrics, aboutMe, 6, 493, 517 - 6, 600, allowedDiscordEmojis)

		graphics.drawImage(profileWrapperOverlay, 0, 0, null)

		return base
	}
}