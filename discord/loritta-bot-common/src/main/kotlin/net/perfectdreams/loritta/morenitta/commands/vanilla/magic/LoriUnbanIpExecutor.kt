package net.perfectdreams.loritta.morenitta.commands.vanilla.magic

import net.perfectdreams.loritta.morenitta.network.Databases
import net.perfectdreams.loritta.morenitta.api.commands.CommandContext
import net.perfectdreams.loritta.morenitta.messages.LorittaReply
import net.perfectdreams.loritta.morenitta.tables.BannedIps
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

object LoriUnbanIpExecutor : LoriToolsCommand.LoriToolsExecutor {
	override val args = "unban ip <ip> <reason>"

	override fun executes(): suspend CommandContext.() -> Boolean = task@{
		if (args.getOrNull(0) != "unban")
			return@task false
		if (args.getOrNull(1) != "ip")
			return@task false
		val ip = args[2]

		transaction(Databases.loritta) {
			BannedIps.deleteWhere { BannedIps.ip eq ip }
		}

		reply(
				LorittaReply(
						"IP desbanido!"
				)
		)
		return@task true
	}
}