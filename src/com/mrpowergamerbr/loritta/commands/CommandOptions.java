package com.mrpowergamerbr.loritta.commands;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class CommandOptions {
	// Todas as pr�ximas op��es s�o "command overrides", isto permite fazer overrides nas op��es globais da Loritta
	public boolean override; // Os comandos a seguir s� ser�o ativados CASO override esteja ativo!
	public boolean explainOnCommandRun = true; // Explicar quando rodar *comando*? (Ou quando usar *comando* :shrug:)
	public boolean explainInPrivate = false; // Caso explainOnCommandRun estiver ativado, � para explicar APENAS no privado ou mandar no global?
	public boolean commandOutputInPrivate = false; // � para mandar o output (ou seja, tudo do comando) no privado em vez de mandar no global?
	public boolean mentionOnCommandOutput = true; // Caso esteja ativado, a Loritta ir� marcar quem executou na mensagem resposta
	public boolean deleteMessageAfterCommand = false; // Deletar mensagem do comando ap�s executar ele?
	// Comandos podem extender a classe CommandOptions para colocar novas op��es

	// TODO: Remover
	@Deprecated
	public boolean getAsBoolean(String key) {
		return false;
	}
}
