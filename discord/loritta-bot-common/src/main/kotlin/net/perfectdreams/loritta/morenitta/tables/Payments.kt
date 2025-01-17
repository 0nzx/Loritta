package net.perfectdreams.loritta.morenitta.tables

import net.perfectdreams.loritta.morenitta.utils.exposed.rawJsonb
import net.perfectdreams.loritta.morenitta.utils.gson
import net.perfectdreams.loritta.morenitta.utils.payments.PaymentGateway
import net.perfectdreams.loritta.morenitta.utils.payments.PaymentReason
import org.jetbrains.exposed.dao.id.LongIdTable

object Payments : LongIdTable() {
    val userId = long("user").index()
    val gateway = enumeration("gateway", PaymentGateway::class)
    val reason = enumeration("reason", PaymentReason::class)
    val money = decimal("money", 12, 2).index()
    val createdAt = long("created_at")
    val paidAt = long("paid_at").nullable()
    val expiresAt = long("expires_at").nullable()
    val discount = double("discount").nullable()
    val referenceId = uuid("reference_id").nullable()
    val metadata = rawJsonb("metadata", gson).nullable()
}