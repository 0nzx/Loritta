package net.perfectdreams.loritta.cinnamon.dashboard.backend.routes.api.v1.users

import io.ktor.server.application.*
import net.perfectdreams.loritta.common.utils.UserPremiumPlans
import net.perfectdreams.loritta.cinnamon.dashboard.backend.LorittaDashboardBackend
import net.perfectdreams.loritta.cinnamon.dashboard.backend.routes.api.v1.RequiresAPIDiscordLoginRoute
import net.perfectdreams.loritta.cinnamon.dashboard.backend.utils.respondLoritta
import net.perfectdreams.loritta.cinnamon.dashboard.backend.utils.LorittaJsonWebSession
import net.perfectdreams.loritta.cinnamon.dashboard.backend.utils.TemmieDiscordAuth
import net.perfectdreams.loritta.cinnamon.dashboard.common.responses.GetUserIdentificationResponse
import net.perfectdreams.loritta.cinnamon.pudding.data.UserId

class GetSelfUserInfoRoute(m: LorittaDashboardBackend) : RequiresAPIDiscordLoginRoute(m, "/api/v1/users/@me") {
    override suspend fun onAuthenticatedRequest(
        call: ApplicationCall,
        discordAuth: TemmieDiscordAuth,
        userIdentification: LorittaJsonWebSession.UserIdentification
    ) {
        val userId = UserId(userIdentification.id.toLong())

        val sonhos = m.pudding.users.getUserProfile(userId)
        val premiumPlan = UserPremiumPlans.getPlanFromValue(m.pudding.payments.getActiveMoneyFromDonations(userId))

        call.respondLoritta(
            GetUserIdentificationResponse(
                UserId(userIdentification.id.toLong()),
                userIdentification.username,
                userIdentification.discriminator,
                userIdentification.avatar,
                userIdentification.verified,
                sonhos?.money ?: 0L,
                premiumPlan.displayAds
            )
        )
    }
}