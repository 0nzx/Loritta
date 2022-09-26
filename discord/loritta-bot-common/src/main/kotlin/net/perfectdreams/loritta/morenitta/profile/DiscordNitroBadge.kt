package net.perfectdreams.loritta.morenitta.profile

import net.perfectdreams.loritta.morenitta.dao.Profile
import net.perfectdreams.loritta.morenitta.network.Databases
import net.dv8tion.jda.api.entities.User
import org.jetbrains.exposed.sql.transactions.transaction

class DiscordNitroBadge : Badge("badges/discord_nitro.png", 50) {
	override fun checkIfUserDeservesBadge(user: User, profile: Profile, mutualGuilds: Set<Long>): Boolean {
		return transaction(Databases.loritta) {
			profile.settings.discordPremiumType != null && profile.settings.discordPremiumType != 0
		}
	}
}