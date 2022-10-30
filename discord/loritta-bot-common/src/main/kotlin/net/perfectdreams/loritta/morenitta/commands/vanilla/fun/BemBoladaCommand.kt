package net.perfectdreams.loritta.morenitta.commands.vanilla.`fun`

import club.minnced.discord.webhook.send.WebhookMessageBuilder
import net.perfectdreams.loritta.morenitta.LorittaBot.Companion.RANDOM
import net.perfectdreams.loritta.morenitta.commands.AbstractCommand
import net.perfectdreams.loritta.morenitta.commands.CommandContext
import net.perfectdreams.loritta.morenitta.utils.WebhookUtils
import net.perfectdreams.loritta.common.locale.BaseLocale
import net.perfectdreams.loritta.common.locale.LocaleKeyData
import net.perfectdreams.loritta.morenitta.utils.OutdatedCommandUtils
import net.perfectdreams.loritta.morenitta.LorittaBot

class BemBoladaCommand(loritta: LorittaBot) : AbstractCommand(
    loritta,
    "bembolada",
    listOf("kenji"),
    net.perfectdreams.loritta.common.commands.CommandCategory.FUN
) {
    override fun getDescriptionKey() = LocaleKeyData("commands.command.bembolada.description")

    override suspend fun run(context: CommandContext, locale: BaseLocale) {
        OutdatedCommandUtils.sendOutdatedCommandMessage(context, locale, "summon kenji")

        val bemBoladas = listOf(
            "Eu morava em uma ilha e me mudei para outra.\n\nNão é um trocadilho, é uma troca de ilha.",
            "Quem é o X-Men que está sempre no trabalho?\n\nNo turno.",
            "Por que dois oculistas estavam brigando?\n\nPorque eles estavam defendendo seu ponto de vista.",
            "Como se faz para piorar?\n\nVocê leva o 3,14 para a igreja.",
            "Qual é a corrida dos certinhos?\n\nO Rally dos Certões.",
            "Qual o nome do pai do Magneto?\n\nMagfilho.",
            "Por que Napoleão vai sempre nas festas?\n\nPor que ele é Bom na Party.",
            "Como é que os músicos andam?\n\nNo compasso.",
            "O que acontece com a pessoa quando ela fica 5 horas na piscina?\n\nNada demais.",
            "Como você faz Bisnaguinha?\n\nPegue um copo d’água e jogue um Bis dentro.",
            "Por que eu mil torneiras?\n\nPorque eu tenho miopia.",
            "Por que a minha amiga adora assistir comerciais de remédio?\n\nPorque o comercial começa com: Ministério da Saúde aDIVERTE.",
            "Qual é o espírito que desbloqueia o Face ID?\n\nA sua alma gêmea.",
            "De onde vieram os Power Rangers?\n\nDos espermatozords.",
            "Qual é o crime que só acontece em casa?\n\nO Homercídio. (Ou lartrocínio)",
            "Por que as batatas caem?\n\nPor causa da Lay’s da gravidade.",
            "Qual é a parte do corpo humano que ajuda o pedreiro?\n\nA coluna serve cal.",
            "A Ana me perguntou se eu gostava de video-game. O que eu respondi?\n\nAna, lógico",
            "Qual é o móvel mais longe?\n\nSofar.",
            "O que você faz quando fica triste?\n\nAbraçar um sapato, porque o sapato com sola.",
            "Por que os caipiras foram presos?\n\nPor formação de quadrilha.",
            "O que são dois pontos cinzas no meio do mar?\n\nTwobarões.",
            "Uma pedra cumprimenta uma tora de madeira.\n\nQue horas são?\n\nOi tora.",
            "Qual a série favorita dos pães?\n\nAssa pão a time.",
            "Qual é o remédio que fala que você está doente?\n\nParacetamol.",
            "Onde é que o tempo passa rápido?\n\nNo escorregador, que os ânus passam rápido.",
            "O que são cinco cores no jardim?\n\nFlower Rangers.",
            "Para onde os gatos vão quando morrem?\n\nPara o pur-GATÓ-rio.",
            "Por que o PC tomou um analgésico?\n\nPorque ele estava com puta dor.",
            "Por que o computador foi preso?\n\nFoi preso porque ele executou uma operação ilegal.",
            "Por que o Papai Noel é descolado?\n\nPorque ele tem uma tatuagem de henna.",
            "Qual é o personagem que tira cópia de documentos em casa?\n\nXerox Holmes.",
            "Por que o sal é educado com você?\n\nPorque o sal é refinado.",
            "Por que o sal manda você calar a boca?\n\nPorque é um sal grosso.",
            "Qual é a fórmula da água benta?\n\nHDeusO",
            "Por que você não pode empinar pipa depois de 89?\n\nPorque se não nãoventa.",
            "Qual é o cachorro que é entendido por todos?\n\nÉ o cãomunicação.",
            "Por que tem 3 pulas-pulas no navio?\n\nPorque tem tripulação.",
            "Por onde andam as dúvidas?\n\nPor via das dúvidas.",
            "O que são cochichos embaixo d’água?\n\nSão fofocas.",
            "Por que o polígono regular foi ao psicólogo?\n\nPorque ele é isolado.",
            "Como é que eu sei que a Lua contou uma bem bolada?\n\nQuando o sol acorda rachando.",
            "Por que a mulher que revende cosméticos começou a roubar bancos?\n\nPorque o creme não compensa.",
            "Como curar queimadura de segundo grau?\n\nCom a pomada de Bhaskara.",
            "Eu estou medindo a paciência de vocês… https://pic.twitter.com/S02WxLCJji",
            "Por que o Capitão América odeia um creme?\n\nPorque um creme hidratante.",
            "Por que as aves não batem em outros animais?\n\nPorque elas têm pena.",
            "O que aconteceria se chovesse achocolatado?\n\nEu ía me melhorar Toddynho.",
            "Qual é o nome da filha do criador do Photoshop?\n\nEdite.",
            "O que a Xuxa fala quando ela termina um show na China?\n\nBeijing Beijing xau xau.",
            "Qual a resistência do sabre de luz?\n\nUm Ohm.",
            "Soldados movidos a bateria sobem o morro. Qual o nome do filme?\n\nTropas de elítio.",
            "O que o tomate foi fazer no banco?\n\nTirar o extrato.",
            "Por que você não pode ligar um rádio?\n\nPorque se você ligar você vai ficar radioativo.",
            "Mussum teve um ano muito ruim. Qual o nome do ator?\n\nKeano horrives.",
            "Por que as aves estão sempre impressionadas?\n\nPorque elas nasceram chocadas.",
            "É melhor parar por aqui porque isso daqui está uma Thortura.",
            "Qual é o chocolate favorito do Thor?\n\nThortuguita.",
            "Eu tenho um amigo que foi vestir o Thor. Qual o nome dele?\n\nVithor.",
            "Qual é o inseto que vive de comércio?\n\nA mosca varejeira.",
            "Qual a roupa que uma pessoa supersticiosa não usa no avião?\n\nTomara que caia.",
            "Qual que é o nome do aplicativo para arrumar namorada para o Kenji?\n\nO Kenji Crush.",
            "O que não tem sabor na língua portuguesa?\n\nA oração sabordinada.",
            "O que acontece quando o Mario e o Luigi pegam a mesma quantidade de moedas?\n\nUma coin-cidência.",
            "Eu vou abrir o jogo da bem bolada… https://pic.twitter.com/xBhK98KpTs",
            "Onde moram os elétrons?\n\nNa eletricidade.",
            "Você pode não ter gostado da Bem bolada. Mas eu sei quem gostou. O mecânico, porque ele sempre tem uma graxa.",
            "Por que Roberto Carlos é chamado de Rei?\n\nPorque no show dele só vai coroa.",
            "O que acontece quando o Flash veste preto?\n\nEle vira o Flash Black.",
            "Por que alguns números trabalham em postos de gasolina?\n\nPorque são números compostos.",
            "Por que o feijão está triste?\n\nPorque ele entrou em uma panela depressão.",
            "Por que o português grita com o carro quando quebra?\n\nPorque ele descobriu que o som é uma energia mecânica.",
            "Como você faz um suco virar uma cobra?\n\nVocê conta uma piada pra ele, porque sucuri.",
            "Qual o contrário de Tron - O Legário?\n\nTroff - O desligário.",
            "Por que a gripe é mal vista pelos outros vírus?\n\nPorque é uma má influenza.",
            "Por que o policial pulou na piscina?\n\nPara prender a respiração.",
            "Por que o Batman dorme pelado?\n\nPorque ele dorme de Bruce.",
            "Qual é o santo que tem poderes telepáticos?\n\nSão quelemente.",
            "Se vidente fosse fruta, qual seria?\n\nLimão.",
            "Qual é a mulher que queria ser um transformer?\n\nCarmen.",
            "Qual é o carro que acabou de sair do forno?\n\nKia Soul.",
            "Por que o papai noel não pode ter filhos?\n\nPorque o saco dele é de brinquedo.",
            "Por que o Luke Skywalker guarda todos os livros?\n\nPra não deixar que a princesa Leia.",
            "Por que os surfistas gostam de comer comida fria?\n\nPorque eles não gostam de micro-ondas.",
            "Por que o elefante colocou a esposa dele na geladeira?\n\nPorque elafanta.",
            "Por que o atrito foi ao psiquiatra?\n\nPorque ele sempre foi desprezado.",
            "Qual a comida mais lenta do mundo?\n\nÉ a polenta.",
            "Qual é o chá para evitar a queda de cabelo?\n\nO chapéu.",
            "Qual é a pomba que separa as pessoas?\n\nA pomba de efeito moral.",
            "Qual é a diferença entre o carpinteiro e o bebê?\n\nO carpinteiro procura uma boa madeira e o bebê uma mamadeira.",
            "Por que os mamíferos que voam não enxergam bem?\n\nPorque ele é um mor-cego.",
            "Por que o Robin comprou um videogame com dois controles?\n\nPra jogar Mortal com Batman.",
            "Qual é a banda que gosta do Faustão?\n\nO Guns errou! https://pic.twitter.com/DhKT0EGTZ6",
            "Por que a vaca foi pro espaço?\n\nPara encontrar com o vácuo.",
            "Por que o pão não pode ter filho?\n\nPorque trigo-no-metria.",
            "Eu levei nove amigos para a capital dos Estados Unidos. Qual o nome do ator?\n\nDez em Washington.",
            "O que o Batman disse pra mim hoje de manhã?\n\nJa-passou da noite.",
            "Para onde foi o homem bomba?\n\nPara todos os lados.",
            "O astronauta matou um cara no espaço e não foi preso. Por quê?\n\nPorque foi um crime sem gravidade.",
            "Por que o jacaré pegou o dinheiro da minha mão?\n\nPorque comprei muita coisa e mandei o Jacarepaguá.",
            "Por que uma senhora não se preocupa com o horário?\n\nPorque ela tá \"senhora\".",
            "Uma cobra queria participar dos Transformers?\n\nComo ela conseguiu entrar?\n\nCom um mega bote.",
            "A gente gosta de iOS, já o Carlos Drummond de Android.",
            "Qual a bebida favorita do Bruce Lee?\n\nUataaa! (Water)",
            "Por que a princesa Isabel usa cosméticos?\n\nPra tirar os escravos.",
            "Qual é o grito mais doce que existe?\n\nIce cream.",
            "Qual é o estado americano que não cai duas vezes no mesmo lugar?\n\nOhio.",
            "Por que o Agente 007 se atrasou para o evento?\n\nPorque ele perdeu o bonde.",
            "Por que o pé de feijão não responde?\n\nPorque é uma mudinha.",
            "O que acontece quando você deixa um ferro de passar cair no chão?\n\nEle passa mal.",
            "Por que o chuveiro não entrou para o exército?\n\nPorque ele não passou no teste de resistência.",
            "Qual é a dança que as aeromoças adoram?\n\nTango.",
            "Qual é a banda que os nerds adoram?\n\nA banda larga.",
            "Sabe quem tem o trabalho mais duro?\n\nÉ o motorista, porque ele trabalha nas horas vagas.",
            "Por que o rádio não pode dar filho?\n\nPorque ele é stereo.",
            "O que o Zeca Pagodinho foi fazer na igreja?\n\nFoi cantar pagode.",
            "Qual é a pedra que dança?\n\nArrocha.",
            "Onde acaba o Pi?\n\nParanapiacaba.",
            "Qual é a bactéria que tem na Bíblia?\n\nSalmonela.",
            "Por que o Albert Einstein parece mais forte quando ele viaja?\n\nPorque ele é um físico turista.",
            "Como é que você faz para um passarinho rir?\n\nVocê joga ele na parede, ele vai rachar o bico.",
            "O que o Harry Potter lê quando ele fica doente?\n\nJosé Saramago.",
            "Um grupo de proctologistas foi fazer uma excursão. Para onde eles foram?\n\nVancouver.",
            "O que o instrutor de carro foi fazer no forró?\n\nEle foi ensinar o Frank a guiar.",
            "Inri Cristo foi para os Estados Unidos. Qual o nome do filme?\n\nAmerican Pai.",
            "Aonde o programador passa o carnaval?\n\nNo bloco de notas.",
            "O que você faz quando você não tem mais sinal no celular?\n\nVocê vai na sorveteria, lá tem cobertura.",
            "Como você transforma um giz em uma cobra?\n\nVocê joga o giz na água, porque giz bóia.",
            "Por que quando os turistas vem pro Brasil eles falam que aqui cheira bem?\n\nPorque o Brasil foi colônia.",
            "Uma fita isolante e uma fita crepe brigaram. Quem ganhou?\n\nA isolante, porque ela é faixa preta.",
            "Quem é o manda chuva no reino do queijo?\n\nO Requeijão.",
            "Qual é o cachorro que a gente tem no corpo?\n\nUm beagle.",
            "O que que dá água na boca?\n\nO copo.",
            "Dois homens peidam ao mesmo tempo. Qual o nome do filme?\n\nGasparzinho.",
            "Existem 10 tipos de pessoas: as que sabem número binário ou não.",
            "O que o sapo falou quando viu o Bob Marley?\n\nReggae.",
            "Um médico descobriu um remédio que cura a dor do paciente antes de saber da dor. Qual o nome do filme?\n\nO Exterminador do Futuro.",
            "O homem sentou em um cachorro. Qual o nome do filme?\n\n101 Dálmata.",
            "Qual o molho matador de bonecas?\n\nBarbecue.",
            "Por que as estrelas não sabem imitar um gato?\n\nPorque astronomia.",
            "Do que o diabo morreu?\n\nEle morreu de diabete.",
            "Por que o português coloca pastel no leite?\n\nPra ter leite pasteurizado.",
            "Qual é o vinho que não tem álcool?\n\nÉ o ovinho de codorna.",
            "Estava lá o Jesse Pinkman e perguntou pro Walter White como faz pra conquistar as minas. – Metafetamina.",
            "Quanto vale um Mamzkei?\n\nTwo Ralf.",
            "O que o átomo foi fazer com uma câmera?\n\nFoi tirar uma fótom.",
            "Por que o Lex Luthor está sempre de preto?\n\nPorque ele está de Luthor.",
            "Estava o Obama jogando Street Fighter, chegou a filha dele e perguntou se podia jogar: – Sure, you can.",
            "Por que a água foi presa?\n\nPorque ela matou a sede.",
            "Na Alemanha tem tanto craque, mas tanto craque que tem um jogador chamado Neuer.",
            "Qual é o cúmulo da ironia?\n\nÉ a Volkswagen ser alemã e fazer Gol no Brasil.",
            "Eu quero que refri tá comigo. https://pic.twitter.com/8urgoFQAev",
            "O que você faz quando você quebra um celular?\n\nVocê chama uma bactéria, pois ela unicelular. (Une celular)",
            "O que é isso?\n\nUm A saltante. https://pic.twitter.com/C4XOuyw4Sq",
            "Quando foi a primeira que os americanos comeram carne?\n\nFoi quando veio Cristóvão com Lombo.",
            "Eu tinha um gato chamado Tido, ele adorava dormir em um cesto. Qual o nome do filme?\n\nCesto sem Tido. (Sexto Sentido)",
            "Qual é o galã das abelhas?\n\nMel Gibson.",
            "Você sabia que a cada 3 homens um é gay?\n\n– Eu não sou.\n– Nem eu.\n\nhttps://pic.twitter.com/QS2Ow9qw8z",
            "O que acontece se você colocar uma lâmpada mágica dentro d'água?\n\nSai um hidro-gênio.",
            "O pai mandou o filho estourar uma bexiga. Qual o nome do filme?\n\nTó estoure. (Toy Story)",
            "O que aconteceu quando o dono da Faber-Castell morreu?\n\nTodos os lápis ficaram desapontados.",
            "O que o maestro foi fazer na farmácia?\n\nFoi pedir um Ré médio.",
            "Por que a vaca dá leite?\n\nPorque ela não sabe vender.",
            "O Ralf casa com um homem e tem um filho, qual o nome da série?\n\nTwo and a Ralf Mamzkei.",
            "Só enchendo o saco. https://pic.twitter.com/wbvZHa3Jwo",
            "O que tem acima do céu?\n\nUm assento(acento). https://pic.twitter.com/4cXXs4vmk4",
            "Você sabe a diferença entre do Goku e uma pessoa gripada?\n\nO Goku é um Saiyajin e uma pessoa gripada solta atchim.",
            "Por que o bombeiro não gosta de andar?\n\nPorque ele socorre.",
            "Por que a aranha precisa de você?\n\nPorque é uma \"araqui need you\".",
            "Quantos graus tem um leão morto?\n\n360 graus, porque é uma ex-fera.",
            "Qual a maior palavra que existe no vocabulário português?\n\nArroz, porque começa com A e termina com Z.",
            "No avião havia 4 romanos e 1 americano. Qual o nome da aeromoça?\n\nIvone.",
            "Por que a cobra queria uma escova?\n\nPorque ela cansou de Serpente.",
            "Qual a diferença entre o pastor… (Essa vai dar bosta)",
            "Perguntei para o papel noel se ele roía as unhas, e ele: – Rou, rou, rou.",
            "Hoje o dia está tão nublado que uma nuvem chegou pra outra e falou: – Num vem que não tem.",
            "O que é um fumo de maconha em cima do jornal?\n\nUm baseado em fatos reais.",
            "Qual a diferença do lago com a padaria?\n\nNo lago há sapinho, na padaria assa pão.",
            "Por que o médico que trabalha de noite usa verde?\n\nÉ porque ele está de plantão.",
            "O que o tomate foi fazer no banco?\n\nFoi tirar o extrato."
        )

        val temmie = WebhookUtils.getOrCreateWebhook(loritta, context.event.channel, "Kenji do Loop Infinito")

        context.sendMessage(
            temmie, WebhookMessageBuilder()
                .setUsername("Kenji do Loop Infinito")
                .setContent(context.getAsMention(true) + bemBoladas[RANDOM.nextInt(bemBoladas.size)])
                .setAvatarUrl("${loritta.config.loritta.website.url}assets/img/kenji.jpg")
                .build()
        )
    }
}