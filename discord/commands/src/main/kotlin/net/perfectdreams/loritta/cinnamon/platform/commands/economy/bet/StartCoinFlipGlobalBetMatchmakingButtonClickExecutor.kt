package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.economy.bet

import net.perfectdreams.discordinteraktions.common.entities.User
import net.perfectdreams.loritta.cinnamon.discord.LorittaCinnamon
import net.perfectdreams.loritta.cinnamon.discord.utils.ComponentExecutorIds
import net.perfectdreams.loritta.cinnamon.discord.interactions.components.ButtonExecutorDeclaration
import net.perfectdreams.loritta.cinnamon.discord.interactions.components.CinnamonButtonExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.components.ComponentContext

class StartCoinFlipGlobalBetMatchmakingButtonClickExecutor(
    loritta: LorittaCinnamon
) : CinnamonButtonExecutor(loritta) {
    companion object : ButtonExecutorDeclaration(ComponentExecutorIds.START_MATCHMAKING_BUTTON_EXECUTOR)

    override suspend fun onClick(user: User, context: ComponentContext) {
        context.deferUpdateMessage()

        val decoded = context.decodeDataFromComponentAndRequireUserToMatch<CoinFlipBetGlobalStartMatchmakingData>()

        CoinFlipBetGlobalExecutor.addToMatchmakingQueue(
            context,
            decoded.quantity
        )
    }
}