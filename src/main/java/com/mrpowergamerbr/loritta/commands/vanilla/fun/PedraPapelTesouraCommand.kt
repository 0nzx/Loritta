package com.mrpowergamerbr.loritta.commands.vanilla.`fun`

import com.mrpowergamerbr.loritta.Loritta
import com.mrpowergamerbr.loritta.commands.CommandBase
import com.mrpowergamerbr.loritta.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.utils.Jankenpon
import com.mrpowergamerbr.loritta.utils.Jankenpon.JankenponStatus
import com.mrpowergamerbr.loritta.utils.f
import com.mrpowergamerbr.loritta.utils.locale.BaseLocale
import java.io.File
import java.io.IOException

class PedraPapelTesouraCommand : CommandBase() {
	override fun getLabel(): String {
		return "ppt"
	}

	override fun getDescription(locale: BaseLocale): String {
		return locale.PPT_DESCRIPTION.f()
	}

	override fun getUsage(): String {
		return "sua escolha"
	}

	override fun getExample(): List<String> {
		return listOf("pedra", "papel", "tesoura")
	}

	override fun getDetailedUsage(): Map<String, String> {
		return mapOf("sua escolha" to "Pedra, Papel ou Tesoura")
	}

	override fun getCategory(): CommandCategory {
		return CommandCategory.FUN
	}

	override fun run(context: CommandContext) {
		if (context.args.isNotEmpty()) {
			val playerValue = context.args[0]

			val janken = Jankenpon.getFromLangString(playerValue.toLowerCase())

			if (janken != null) {
				val opponent = Jankenpon.values()[Loritta.random.nextInt(Jankenpon.values().size)]

				val status = janken.getStatus(opponent)

				var fancy: String? = null
				if (status == JankenponStatus.WIN) {
					fancy = "**${context.locale.PPT_WIN.f()} \uD83D\uDE0A**"
				}
				if (status == JankenponStatus.LOSE) {
					fancy = "**${context.locale.PPT_LOSE.f()} \uD83D\uDE42**"
				}
				if (status == JankenponStatus.DRAW) {
					fancy = "**${context.locale.PPT_DRAW.f()} \uD83D\uDE0A**"
				}
				context.sendMessage(context.getAsMention(true) + context.locale.PPT_CHOSEN.f(janken.emoji, opponent.emoji) + "\n" + fancy)
			} else {
				if (playerValue.equals("jesus", ignoreCase = true)) {
					val fancy = "**${context.locale.PPT_MAYBE_DRAW.f()} 🤔 🤷**"
					val jesus = "🙇 *${context.locale.PPT_JESUS_CHRIST.f()}* 🙇"
					context.sendMessage(context.getAsMention(true) + context.locale.PPT_CHOSEN.f(jesus, jesus) + "\n" + fancy)
				} else if (playerValue.equals("velberan", ignoreCase = true)) {
					val opponent = Jankenpon.values()[Loritta.random.nextInt(Jankenpon.values().size)]

					val fancy = "🕹🕹🕹"
					context.sendMessage(context.getAsMention(true) + "Você escolheu 🕹 *VELBERAN*🕹, eu escolhi " + opponent.emoji + "\n" + fancy)
					try {
						context.sendFile(File(Loritta.FOLDER + "velberan.gif"), "velberan.gif", " ")
					} catch (e: IOException) {
						e.printStackTrace()
					}
				} else {
					val fancy = "**${context.locale.PPT_INVALID.f()} \uD83D\uDE09**"
					val jesus = "🙇 *${context.locale.PPT_JESUS_CHRIST.f()}* 🙇"
					context.sendMessage(context.getAsMention(true) + context.locale.PPT_CHOSEN.f("💩", jesus) + "\n" + fancy)
				}
			}
		} else {
			context.explain()
		}
	}
}