package com.mrpowergamerbr.loritta.commands.vanilla.social

import com.github.kevinsawicki.http.HttpRequest
import com.google.gson.JsonParser
import com.mrpowergamerbr.loritta.commands.CommandBase
import com.mrpowergamerbr.loritta.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import net.dv8tion.jda.core.EmbedBuilder
import java.awt.Color

class McStatusCommand : CommandBase() {
    override fun getLabel(): String {
        return "mcstatus";
    }

    override fun getDescription(): String {
        return "Verifica se os servidores da Mojang estão online";
    }

    override fun getCategory(): CommandCategory {
        return CommandCategory.MINECRAFT;
    }

    override fun run(context: CommandContext) {
        var body = HttpRequest.get("https://mcapi.ca/mcstatus").body();

        var builder = EmbedBuilder()
                .setTitle("📡 Status da Mojang", "https://help.mojang.com/")
                .setColor(Color.GREEN);

        var json = JsonParser().parse(body);
        for (section in json.asJsonObject.entrySet()) {
            var status = section.value.asJsonObject.get("status").asString;
            var prefix = if (section.key.contains("minecraft")) "<:grass:330435576392318978> " else "<:mojang:330436377831538689>";
            var emoji = if (status == "Online") "✅" else "❌";
            builder.addField(prefix + section.key, "${emoji} ${status}", true)
        }

        context.sendMessage(builder.build());
    }
}