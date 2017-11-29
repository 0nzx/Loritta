package com.mrpowergamerbr.loritta.commands.vanilla.utils

import com.github.kevinsawicki.http.HttpRequest
import com.github.salomonbrys.kotson.obj
import com.google.gson.stream.JsonReader
import com.mrpowergamerbr.loritta.Loritta
import com.mrpowergamerbr.loritta.commands.CommandBase
import com.mrpowergamerbr.loritta.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.utils.Constants
import com.mrpowergamerbr.loritta.utils.JSON_PARSER
import com.mrpowergamerbr.loritta.utils.locale.BaseLocale
import com.mrpowergamerbr.loritta.utils.msgFormat
import net.dv8tion.jda.core.EmbedBuilder
import java.awt.Color
import java.io.StringReader
import java.net.URLEncoder


class TempoCommand : CommandBase("tempo") {
	override fun getUsage(): String {
		return "cidade"
	}

	override fun getAliases(): List<String> {
		return listOf("previsão", "previsao")
	}

	override fun getDescription(locale: BaseLocale): String {
		return locale.TEMPO_DESCRIPTION
	}

	override fun getExample(): List<String> {
		return listOf("São Paulo");
	}

	override fun getCategory(): CommandCategory {
		return CommandCategory.UTILS;
	}

	override fun run(context: CommandContext, locale: BaseLocale) {
		if (context.args.isNotEmpty()) {
			var cidade = context.args.joinToString(separator = " ");

			var cidadeResponse = HttpRequest.get("http://api.openweathermap.org/data/2.5/forecast?q=" + URLEncoder.encode(cidade, "UTF-8") + "&units=metric&lang=pt&APPID=" + Loritta.config.openWeatherMapKey).body()
			val reader = StringReader(cidadeResponse)
			val jsonReader = JsonReader(reader)
			val cidadeJsonResponse = JSON_PARSER.parse(jsonReader).asJsonObject // Base

			if (cidadeJsonResponse.get("cod").asString == "200") { // Nós encontramos alguma coisa?
				var status = cidadeJsonResponse.get("list").asJsonArray.get(0).asJsonObject;

				var now = status.getAsJsonObject("main").get("temp").asDouble;
				var max = status.getAsJsonObject("main").get("temp_max").asDouble;
				var min = status.getAsJsonObject("main").get("temp_min").asDouble;
				var pressure = status.getAsJsonObject("main").get("pressure").asDouble;
				var humidity = status.getAsJsonObject("main").get("humidity").asDouble;
				var windSpeed = status.getAsJsonObject("wind").get("speed").asDouble;
				var realCityName = cidadeJsonResponse.get("city").asJsonObject.get("name").asString;
				var countryShort = if (cidadeJsonResponse["city"].obj.has("country")) cidadeJsonResponse.get("city").asJsonObject.get("country").asString else realCityName;
				var icon = "";

				var embed = EmbedBuilder();

				var description = status.get("weather").asJsonArray.get(0).asJsonObject.get("description").asString
				var abbr = status.get("weather").asJsonArray.get(0).asJsonObject.get("icon").asString

				if (abbr.startsWith("01")) {
					icon = "☀ ";
				}
				if (abbr.startsWith("02")) {
					icon = "⛅ ";
				}
				if (abbr.startsWith("03")) {
					icon = "☁ ";
				}
				if (abbr.startsWith("04")) {
					icon = "☁ ";
				}
				if (abbr.startsWith("09")) {
					icon = "\uD83D\uDEBF ";
				}
				if (abbr.startsWith("10")) {
					icon = "\uD83C\uDF27 ";
				}
				if (abbr.startsWith("11")) {
					icon = "⛈ ";
				}
				if (abbr.startsWith("13")) {
					icon = "\uD83C\uDF28 ";
				}
				if (abbr.startsWith("50")) {
					icon = "\uD83C\uDF2B ";
				}

				embed.setTitle(context.locale.TEMPO_PREVISAO_PARA.msgFormat(realCityName, countryShort))
				embed.setDescription(icon + description);
				embed.setColor(Color(0, 210, 255));
				embed.addField("🌡 ${context.locale.TEMPO_TEMPERATURA}", "**${context.locale.TEMPO_ATUAL}: **$now ºC\n**${context.locale.TEMPO_MAX}: **$max ºC\n**${context.locale.TEMPO_MIN}: **$min ºC", true);
				embed.addField("💦 ${context.locale.TEMPO_UMIDADE}", "$humidity%", true);
				embed.addField("🌬 ${context.locale.TEMPO_VELOCIDADE_VENTO}", "$windSpeed km/h", true);
				embed.addField("🏋 ${context.locale.TEMPO_PRESSAO_AR}", "$pressure kPA", true);

				context.sendMessage(embed.build());
			} else {
				// Cidade inexistente!
				context.sendMessage(Constants.ERROR + " **|** " + context.getAsMention(true) + context.locale.TEMPO_COULDNT_FIND.msgFormat(cidade))
			}
		} else {
			this.explain(context);
		}
	}
}