package net.perfectdreams.loritta.plugin.lorittabirthday2020

import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonObject
import com.mrpowergamerbr.loritta.network.Databases
import kotlinx.coroutines.channels.Channel
import net.perfectdreams.loritta.plugin.lorittabirthday2020.tables.CollectedBirthday2020Points
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.ConcurrentHashMap

object LorittaBirthday2020 {
	val pantufaRewards = listOf(
			BackgroundReward(100, "birthday2020TeamPantufa"),

			SonhosReward(200, 7_000),

			BackgroundReward(300, "birthday2020Brabas"),

			SonhosReward(400, 7_000),

			BackgroundReward(500, "birthday2020PantufaAllouette"),

			SonhosReward(600, 7_000),

			BackgroundReward(700, "birthday2020PantufaSonikaSan"),

			SonhosReward(800, 7_000),

			BackgroundReward(900, "birthday2020PantufaLaurenha"),

			SonhosReward(1_000, 7_000),

			BackgroundReward(1_100, "birthday2020PantufaDelly"),

			SonhosReward(1_200, 7_000),

			BackgroundReward(1_300, "birthday2020PantufaHugoo")
	)
	val gabrielaRewards = listOf(
			BackgroundReward(100, "birthday2020TeamGabriela"),

			SonhosReward(200, 7_000),

			BackgroundReward(300, "birthday2020Brabas"),

			SonhosReward(400, 7_000),

			BackgroundReward(500, "birthday2020PantufaAllouette"),

			SonhosReward(600, 7_000),

			BackgroundReward(700, "birthday2020GabrielaCoffee"),

			SonhosReward(800, 7_000),

			BackgroundReward(900, "birthday2020GabrielaInnerDesu"),

			SonhosReward(1_000, 7_000),

			BackgroundReward(1_100, "birthday2020GabrielaStar"),

			SonhosReward(1_200, 7_000),

			BackgroundReward(1_300, "birthday2020GabrielaItsGabi")
	)

	val openChannels = ConcurrentHashMap<Long, Channel<JsonObject>>()
	val detectedBotGuilds = ConcurrentHashMap<Long, MutableList<DetectedInfractions>>()
	val blacklistedGuilds = listOf(
			623204625251827724L,
			546027106895790081L,
			673036837324980238L
	)

	val emojis = listOf(
			"happy_birthday:692338660611457035",
			"loritta_morenitta:692338660577771590",
			"para_mais_um_ano:692338660762320926",
			"com_muita_alegria:692338660548411405",
			"e_diversao:692338660632428574",
			"continuando_a_alegrar:692338660712120341",
			"mais_de_300k_guilds:692338660615389194",
			"desde_2017:692338660393353226",
			"tentando_transformar:692338660397416448",
			"o_mundo_em_um_lugar_melhor:692338660414324756",
			"obrigada_por_tudo:692338660263329793",
			"voces_sao_incriveis:692338660254941254",
			"ah_e_claro:692339142058573825",
			"obrigada_a_pantufa:692339142209568848",
			"e_a_gabriela:692339141995790436",
			"por_me_ajudarem:692339142087933962",
			"elas_tambem_sao_incriveis:692339142033539142",
			"carinha_feliz:692339141790400603"
	)

	fun isEventActive(): Boolean {
		// val calendar = Calendar.getInstance()
		// return calendar.get(Calendar.YEAR) == 2020
		val endOfEvent = LocalDateTime.of(2020, 3, 30, 15, 0)
				.atZone(ZoneId.of("America/Sao_Paulo"))
		val now = Instant.now().atZone(ZoneId.of("America/Sao_Paulo"))

		return now.isBefore(endOfEvent)
	}

	fun sendPresentCount(m: LorittaBirthday2020Event, id: Long, type: String = "collectedPoint") {
		val channel = openChannels[id] ?: return

		val points = transaction(Databases.loritta) {
			CollectedBirthday2020Points.select {
				CollectedBirthday2020Points.user eq id
			}.count()
		}

		m.launch {
			channel.send(
					jsonObject(
							"type" to type,
							"total" to points
					)
			)
		}
	}

	open class Reward(val requiredPoints: Int)
	class BackgroundReward(requiredPoints: Int, val internalName: String) : Reward(requiredPoints)
	class SonhosReward(requiredPoints: Int, val sonhosReward: Int) : Reward(requiredPoints)
	data class DetectedInfractions(
			val guildId: Long,
			val messageId: Long,
			val detectedAt: Long
	)
}