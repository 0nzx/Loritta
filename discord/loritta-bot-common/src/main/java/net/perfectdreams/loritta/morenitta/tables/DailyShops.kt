package net.perfectdreams.loritta.morenitta.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object DailyShops : LongIdTable() {
    val generatedAt = long("generated_at")
}