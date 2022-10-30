package net.perfectdreams.loritta.morenitta.website.routes.api.v1.economy

import com.github.salomonbrys.kotson.double
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.jsonObject
import com.github.salomonbrys.kotson.long
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonParser
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import net.perfectdreams.loritta.morenitta.LorittaBot
import net.perfectdreams.loritta.morenitta.utils.SonhosPaymentReason
import net.perfectdreams.loritta.morenitta.website.routes.api.v1.RequiresAPIAuthenticationRoute
import net.perfectdreams.loritta.morenitta.website.utils.extensions.respondJson

class PostTransferBalanceExternalRoute(loritta: LorittaBot) :
    RequiresAPIAuthenticationRoute(loritta, "/api/v1/economy/transfer/garticos") {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override suspend fun onAuthenticatedRequest(call: ApplicationCall) {
        val body = withContext(Dispatchers.IO) { call.receiveText() }
        val json = JsonParser.parseString(body)
        val receiverId = json["receiverId"].string
        val garticos = json["garticos"].long
        val transferRate = json["transferRate"].double

        val profile = loritta.getOrCreateLorittaProfile(receiverId)

        logger.info { "$receiverId (has ${profile.money} dreams) is transferring $garticos garticos to Loritta with transfer rate is $transferRate" }
        val finalMoney = (garticos * transferRate)

        loritta.newSuspendedTransaction {
            profile.addSonhosAndAddToTransactionLogNested(
                finalMoney.toLong(),
                SonhosPaymentReason.GARTICOS_TRANSFER
            )
        }

        logger.info { "$receiverId (now has ${profile.money} dreams) transferred $garticos garticos to Loritta with transfer rate is $transferRate" }

        call.respondJson(
            jsonObject(
                "balance" to profile.money
            )
        )
    }
}