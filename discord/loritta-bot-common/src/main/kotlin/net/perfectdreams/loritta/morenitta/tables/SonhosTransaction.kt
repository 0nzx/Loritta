package net.perfectdreams.loritta.morenitta.tables

import net.perfectdreams.loritta.morenitta.utils.exposed.rawJsonb
import net.perfectdreams.loritta.morenitta.utils.gson
import net.perfectdreams.loritta.morenitta.utils.SonhosPaymentReason
import org.jetbrains.exposed.dao.id.LongIdTable

object SonhosTransaction : LongIdTable() {
    val reason = enumeration("source", SonhosPaymentReason::class)
    val givenBy = long("given_by").index().nullable()
    val receivedBy = long("received_by").index().nullable()
    val givenAt = long("given_at")
    val quantity = decimal("quantity", 12, 2)
    val metadata = rawJsonb("metadata", gson).nullable()
}