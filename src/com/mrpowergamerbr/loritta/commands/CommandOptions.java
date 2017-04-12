package com.mrpowergamerbr.loritta.commands;

import java.util.HashMap;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class CommandOptions {
	@Deprecated
	HashMap<String, Object> options = new HashMap<String, Object>();
	
	// Todas as pr�ximas op��es s�o "command overrides", isto permite fazer overrides nas op��es globais da Loritta
	boolean explainOnCommandRun; // Explicar quando rodar *comando*? (Ou quando usar *comando* :shrug:)
	boolean explainInPrivate; // Caso explainOnCommandRun estiver ativado, � para explicar APENAS no privado ou mandar no global?
	boolean commandOutputInPrivate; // � para mandar o output (ou seja, tudo do comando) no privado em vez de mandar no global?
	boolean mentionOnCommandOutput; // Caso esteja ativado, a Loritta ir� marcar quem executou na mensagem resposta
	boolean deleteMessageAfterCommand; // Deletar mensagem do comando ap�s executar ele?
	// Comandos podem extender a classe CommandOptions para colocar novas op��es
	
	@Deprecated
	public boolean getAsBoolean(String key) {
		if (options.containsKey(key)) {
			return (boolean) options.get(key);
		}
		return false;
	}
}
