package com.mrpowergamerbr.loritta.commands.nashorn;

import com.mrpowergamerbr.loritta.commands.CommandContext;
import com.mrpowergamerbr.loritta.userdata.LorittaProfile;
import com.mrpowergamerbr.loritta.userdata.ServerConfig;
import com.mrpowergamerbr.loritta.utils.LorittaUtils;
import com.mrpowergamerbr.loritta.utils.TextUtilsKt;
import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bson.types.ObjectId;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Comandos usando a Nashorn Engine
 */
@Getter
@Setter
public class NashornCommand {
	public ObjectId id = new ObjectId(); // Object ID único para cada comando
	public String label; // label do comando
	public String javaScript; // código em JS do comando
	public List<String> aliases = new ArrayList<>(); // aliases
	public boolean isEnabled = true; // Se o comando está ativado
	public LocalDateTime createdDate = LocalDateTime.now(); // Data criada
	public LocalDateTime editedDate = LocalDateTime.now(); // Data editada
	public List<String> authors = new ArrayList<>(); // Autores do comando (ou seja, quem mexeu)
	public boolean isPublic = false; // Se o comando é público no repositório de comandos
	public boolean isForked = false; // Se é uma cópia de outro comando na repo de cmds
	public ObjectId upstreamId; // Caso seja forked, o upstreamId irá ter o Object ID original

	public NashornCommand() {}

	public NashornCommand(String label, String javaScript) {
		this.label = label;
		this.javaScript = javaScript;
	}

	@Deprecated
	public boolean handle(MessageReceivedEvent ev, String message, ServerConfig conf) {
		if (message.startsWith(conf.commandPrefix + label)) {
			ev.getChannel().sendTyping().complete();
			String cmd = label;
			String onlyArgs = message.substring(message.indexOf(cmd) + cmd.length()); // wow, such workaround, very bad
			String[] args = Arrays.asList(onlyArgs.split(" ")).stream().filter((str) -> !str.isEmpty())
					.collect(Collectors.toList()).toArray(new String[0]);
			String onlyArgsRaw = ev.getMessage().getRawContent().substring(message.indexOf(cmd) + cmd.length()); // wow, such workaround, very bad
			String[] rawArgs = Arrays.asList(onlyArgsRaw.split(" ")).stream().filter((str) -> !str.isEmpty()).collect(Collectors.toList()).toArray(new String[0]);
			String onlyArgsStripped = TextUtilsKt.stripCodeMarks(ev.getMessage().getStrippedContent()).substring(message.indexOf(cmd) + cmd.length()); // wow, such workaround, very bad
			String[] strippedArgs = Arrays.asList(onlyArgsStripped.split(" ")).stream().filter((str) -> !str.isEmpty()).collect(Collectors.toList()).toArray(new String[0]);
			CommandContext context = new CommandContext(conf, ev, null, args, rawArgs, strippedArgs);
			run(context, new NashornContext(context));
			return true;
		}
		return false;
	}

	public boolean handle(MessageReceivedEvent ev, ServerConfig conf, LorittaProfile profile) {
		String message = ev.getMessage().getContent();

		if (message.startsWith(conf.commandPrefix + label)) {
			ev.getChannel().sendTyping().complete();
			String cmd = label;
			String onlyArgs = TextUtilsKt.stripCodeMarks(message.substring(message.indexOf(cmd) + cmd.length())); // wow, such workaround, very bad
			String[] args = Arrays.asList(onlyArgs.split(" ")).stream().filter((str) -> !str.isEmpty())
					.collect(Collectors.toList()).toArray(new String[0]);
			String onlyArgsRaw = ev.getMessage().getRawContent().substring(message.indexOf(cmd) + cmd.length()); // wow, such workaround, very bad
			String[] rawArgs = Arrays.asList(onlyArgsRaw.split(" ")).stream().filter((str) -> !str.isEmpty()).collect(Collectors.toList()).toArray(new String[0]);
			String onlyArgsStripped = TextUtilsKt.stripCodeMarks(ev.getMessage().getStrippedContent()).substring(message.indexOf(cmd) + cmd.length()); // wow, such workaround, very bad
			String[] strippedArgs = Arrays.asList(onlyArgsStripped.split(" ")).stream().filter((str) -> !str.isEmpty()).collect(Collectors.toList()).toArray(new String[0]);
			CommandContext context = new CommandContext(conf, ev, null, args, rawArgs, strippedArgs);
			if (LorittaUtils.handleIfBanned(context, profile)) {
				return true;
			}
			run(context, new NashornContext(context));
			return true;
		}
		return false;
	}

	public void run(CommandContext ogContext, NashornContext context) {
		NashornScriptEngineFactory factory = new NashornScriptEngineFactory();

		ScriptEngine engine = factory.getScriptEngine(new NashornClassFilter());
		Invocable invocable = (Invocable) engine;
		// Funções que jamais poderão ser usadas em comandos
		String blacklisted = "var quit=function(){throw 'Operação não suportada: quit';};var exit=function(){throw 'Operação não suportada: exit';};var print=function(){throw 'Operação não suportada: print';};var echo=function(){throw 'Operação não suportada: echo';};var readLine=function(){throw 'Operação não suportada: readLine';};var readFully=function(){throw 'Operação não suportada: readFully';};var load=function(){throw 'Operação não suportada: load';};var loadWithNewGlobal=function(){throw 'Operação não suportada: loadWithNewGlobal';};";
		// Funções inline para facilitar a programação de comandos
		String inlineMethods = "var nashornUtils = Java.type(\"com.mrpowergamerbr.loritta.commands.nashorn.NashornUtils\");\n"
				+ "var loritta=function(){ return nashornUtils.loritta(); };\n"
				+ "var mensagem=function(){ return contexto.getMensagem(); };\n"
				+ "var quemEnviou=function(){ return contexto.getSender(); };\n"
				+ "var pegarMensagem=function(){ return contexto.getMensagem(); };\n"
				+ "var pegarConteúdoDeUmaURL=function(url){ return nashornUtils.getURL(url); };\n"
				+ "var responder=function(mensagem){ return contexto.responder(mensagem); };\n"
				+ "var enviarMensagem=function(mensagem){ return contexto.enviarMensagem(mensagem); };\n"
				+ "var enviarImagem=function(imagem, mensagem){ return contexto.enviarImagem(imagem, mensagem || \" \"); };\n"
				+ "var pegarArgumento=function(index){ return contexto.pegarArgumento(index); };\n"
				+ "var argumento=function(index, mensagem){ return contexto.argumento(index, mensagem); };\n"
				+ "var juntarArgumentos=function(delimitador){ return contexto.juntarArgumentos(delimitador || \" \"); };\n"
				+ "var criarImagem=function(x, y){ return contexto.criarImagem(x, y); };\n"
				+ "var baixarImagem=function(url){ return nashornUtils.downloadImage(url); };\n"
				+ "var cor=function(r, g, b) { return nashornUtils.createColor(r, g, b); };\n"
				+ "var pegarImagemDoContexto=function(argumento) { return contexto.pegarImagemDoContexto(argumento); };";
		try {
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
			Future<Void> future = executor.submit(new NashornTask(engine, blacklisted + " function nashornCommand(contexto) {" + inlineMethods + javaScript + "}", ogContext, context));
			future.get(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.setTitle("❌ Ih Serjão Sujou! 🤦", "https://youtu.be/G2u8QGY25eU");
			String description = "Irineu, você não sabe e nem eu!";
			if (e instanceof ExecutionException) {
				description = "A thread que executava este comando agora está nos céus... *+angel* (Provavelmente seu script atingiu o limite máximo de memória utilizada!)";
			} else {
				if (e != null && e.getCause() != null && e.getCause().getMessage() != null) {
					description = e.getCause().getMessage().trim();
				} else if (e != null) {
					description = ExceptionUtils.getStackTrace(e).substring(0, Math.min(1000, ExceptionUtils.getStackTrace(e).length()));
				}
			}
			builder.setDescription("```" + description + "```");
			builder.setFooter(
					"Aprender a programar seria bom antes de me forçar a executar códigos que não funcionam 😢", null);
			builder.setColor(Color.RED);
			ogContext.sendMessage(builder.build());
		}
	}

	static final class NashornClassFilter implements ClassFilter {
		@Override
		public boolean exposeToScripts(String s) {
			if (s.compareTo("com.mrpowergamerbr.loritta.commands.nashorn.NashornUtils") == 0) { return true; }
			return false;
		}
	}
}