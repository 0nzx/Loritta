package com.mrpowergamerbr.loritta.commands.vanilla.utils

import com.github.kevinsawicki.http.HttpRequest
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import com.mrpowergamerbr.loritta.commands.CommandBase
import com.mrpowergamerbr.loritta.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.utils.Constants
import com.mrpowergamerbr.loritta.utils.locale.BaseLocale
import com.mrpowergamerbr.loritta.utils.msgFormat
import java.io.StringReader
import kotlin.concurrent.fixedRateTimer


class YoutubeMp3Command : CommandBase() {
	override fun getLabel(): String {
		return "ytmp3"
	}

	override fun getUsage(): String {
		return "link"
	}

	override fun getAliases(): List<String> {
		return listOf("youtube2mp3", "youtubemp3")
	}

	override fun getDescription(locale: BaseLocale): String {
		return locale.get("YOUTUBEMP3_DESCRIPTION")
	}

	override fun getExample(): List<String> {
		return listOf("https://youtu.be/BaUwnmncsrc");
	}

	override fun getCategory(): CommandCategory {
		return CommandCategory.UTILS;
	}

	override fun run(context: CommandContext) {
		if (context.args.size == 1) {
			var mensagem = context.sendMessage("💭 **|** " + context.getAsMention(true) + "${context.locale.PROCESSING}...");

			var link = context.args[0]
			link = link.replace("https://www.youtube.com/watch?v=", "");
			link = link.replace("https://youtu.be/", "");

			var videoId = link;
			var callbackId = "lorittaCallback";

			var checkResponse = HttpRequest.get("https://d.ymcdn.cc/check.php?callback=$callbackId&v=$videoId&f=mp3&_=1498314662109")
					.userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0")
					.body()
					.replace(callbackId, "")
			checkResponse = checkResponse.removePrefix("(").removeSuffix(")");

			val reader = StringReader(checkResponse)
			val jsonReader = JsonReader(reader)
			val checkJsonResponse = JsonParser().parse(jsonReader).asJsonObject // Base

			var hash = checkJsonResponse.get("hash").asString
			var title = checkJsonResponse.get("title").asString

			if (title == "none") {
				mensagem.editMessage(Constants.ERROR + " **|** " + context.getAsMention(true) + context.locale.YOUTUBEMP3_INVALID_LINK).complete();
				return;
			}

			var lastProgress = "0";
			// create a fixed rate timer that prints hello world every 100ms
			// after a 100ms delay
			val fixedRateTimer = fixedRateTimer(name = "YTMP3 Progress Check Thread",
					initialDelay = 0, period = 1000) {
				var progressResponse = HttpRequest.get("https://d.ymcdn.cc/progress.php?callback=$callbackId&id=$hash&_=1498315402819")
						.userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0")
						.body()
						.replace(callbackId, "")
				progressResponse = progressResponse.removePrefix("(").removeSuffix(")");

				val readerProgress = StringReader(progressResponse)
				val progressJsonResponse = JsonParser().parse(readerProgress).asJsonObject // Base

				val progress = progressJsonResponse.get("progress").asString;
				if (progressJsonResponse.has("error") && progressJsonResponse["error"].string.isNotEmpty()) {
					mensagem.editMessage(Constants.ERROR + " **|** " + context.getAsMention(true) + context.locale.YOUTUBEMP3_ERROR_WHEN_CONVERTING).complete()
					this.cancel()
					return@fixedRateTimer
				}
				if (progress == "1" && lastProgress != progress) {
					mensagem.editMessage("💭 **|** " + context.getAsMention(true) + context.locale.YOUTUBEMP3_DOWNLOADING_VIDEO).complete()
				}
				if (progress == "2" && lastProgress != progress) {
					mensagem.editMessage("💭 **|** " + context.getAsMention(true) + context.locale.YOUTUBEMP3_CONVERTING_VIDEO).complete()
				}
				if (progress == "3") {
					var serverId = progressJsonResponse.get("sid").asString;
					var serverName = "";
					when (serverId) {
						"1" -> serverName = "odg"
						"2" -> serverName = "ado"
						"3" -> serverName = "jld"
						"4" -> serverName = "tzg"
						"5" -> serverName = "uuj"
						"6" -> serverName = "bkl"
						"7" -> serverName = "fnw"
						"8" -> serverName = "eeq"
						"9" -> serverName = "ebr"
						"10" -> serverName = "asx"
						"11" -> serverName = "ghn"
						"12" -> serverName = "eal"
						"13" -> serverName = "hrh"
						"14" -> serverName = "quq"
						"15" -> serverName = "zki"
						"16" -> serverName = "tff"
						"17" -> serverName = "aol"
						"18" -> serverName = "eeu"
						"19" -> serverName = "kkr"
						"20" -> serverName = "yui"
						"21" -> serverName = "yyd"
						"22" -> serverName = "hdi"
						"23" -> serverName = "ddb"
						"24" -> serverName = "iir"
						"25" -> serverName = "ihi"
						"26" -> serverName = "heh"
						"27" -> serverName = "xaa"
						"28" -> serverName = "nim"
						"29" -> serverName = "omp"
						"30" -> serverName = "eez"
					}
					mensagem.editMessage("📥 **|** " + context.getAsMention(true) + context.locale.YOUTUBEMP3_FINISHED.msgFormat("https://$serverName.ymcdn.cc/download.php?id=$hash")).complete()
					this.cancel()
				}
				lastProgress = progress;
			}
		} else {
			this.explain(context);
		}
	}
}