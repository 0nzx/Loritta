package com.mrpowergamerbr.loritta.commands;

import lombok.Getter;

@Getter
public enum CommandCategory {
	MINECRAFT("Minecraft"),
	UNDERTALE("Undertale"),
	DISCORD("Discord"),
	MISC("Miscel�nea"),
	FUN("Divers�o"),
	ADMIN("Administra��o"),
	MAGIC("M�gica"); // Esta categoria � usada para comandos APENAS para o dono do bot (no caso, MrPowerGamerBR#4185)
	
	String fancyTitle;
	
	CommandCategory(String fancyTitle) {
		this.fancyTitle = fancyTitle;
	}
}
