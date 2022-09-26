package net.perfectdreams.loritta.legacy.profile

import net.perfectdreams.loritta.legacy.dao.Profile
import net.perfectdreams.loritta.legacy.utils.loritta
import net.dv8tion.jda.api.entities.User
import net.perfectdreams.loritta.legacy.utils.config.FanArtArtist

class ArtistBadge : Badge("artist_badge.png", 25) {
	override fun checkIfUserDeservesBadge(user: User, profile: Profile, mutualGuilds: Set<Long>): Boolean {
		return loritta.fanArtArtists.any { it.socialNetworks?.filterIsInstance<FanArtArtist.SocialNetwork.DiscordSocialNetwork>()?.firstOrNull()?.id == user.id }
	}
}