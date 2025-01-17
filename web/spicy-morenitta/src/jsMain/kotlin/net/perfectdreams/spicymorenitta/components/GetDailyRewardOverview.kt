package net.perfectdreams.spicymorenitta.components

import androidx.compose.runtime.Composable
import net.perfectdreams.loritta.serializable.responses.*
import net.perfectdreams.spicymorenitta.i18nContext
import net.perfectdreams.spicymorenitta.routes.DailyScreen
import net.perfectdreams.spicymorenitta.utils.CloudflareTurnstileUtils
import net.perfectdreams.spicymorenitta.utils.State
import net.perfectdreams.spicymorenitta.utils.TurnstileOptions
import net.perfectdreams.spicymorenitta.utils.jsObject
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun GetDailyRewardOverview(
    screen: DailyScreen.GetDailyRewardScreen
) {
    Div(
        attrs = {
            classes("daily-overview")
        }
    ) {
        Div {
            when (val currentState = screen.responseState) {
                is State.Loading -> Text("Carregando...")
                is State.Failure -> Text("Falhou!")
                is State.Success -> {
                    when (val response = currentState.value) {
                        is GetDailyRewardStatusResponse.Success -> {
                            // TODO: Show a pop-up if the user has already received daily with the same IP today
                            val receivedDailyWithSameIp = response.receivedDailyWithSameIp
                            Text("Responda corretamente para receber um bônus no seu daily!")

                            Div(
                                attrs = {
                                    ref {
                                        CloudflareTurnstileUtils
                                            .render(
                                                it,
                                                jsObject<TurnstileOptions> {
                                                    this.sitekey = response.captchaSiteKey
                                                    this.callback = {
                                                        screen.captchaToken = it
                                                    }
                                                }
                                            )

                                        onDispose {}
                                    }
                                }
                            )

                            Div(
                                attrs = {
                                    classes("daily-question-wrapper")
                                }
                            ) {
                                Div(
                                    attrs = {
                                        classes("daily-question")
                                    }
                                ) {
                                    Text(i18nContext.get(response.question.question))
                                }

                                Div(
                                    attrs = {
                                        classes("daily-question-buttons")
                                    }
                                ) {
                                    Button(
                                        attrs = {
                                            classes("button", "primary")
                                            val captchaToken = screen.captchaToken

                                            if (screen.executingRequest || captchaToken == null)
                                                disabled()
                                            else {
                                                onClick {
                                                    screen.launch {
                                                        screen.sendDailyRewardRequest(captchaToken, response.question.id, true)
                                                    }
                                                }
                                            }
                                        }
                                    ) {
                                        Text("Sim")
                                    }

                                    Button(
                                        attrs = {
                                            classes("button", "primary")
                                            val captchaToken = screen.captchaToken

                                            if (screen.executingRequest || captchaToken == null)
                                                disabled()
                                            else {
                                                onClick {
                                                    screen.launch {
                                                        screen.sendDailyRewardRequest(captchaToken, response.question.id, false)
                                                    }
                                                }
                                            }
                                        }
                                    ) {
                                        Text("Não")
                                    }
                                }
                            }

                            if (receivedDailyWithSameIp) {
                                Div(attrs = { classes("daily-warning") }) {
                                    Text( "Parece que você já recebeu o prêmio diário hoje, se você não pegou... isto pode significar que existem pessoas com o mesmo IP que também pegaram o prêmio! Se você prometer para mim que você não está criando contas alternativas/fakes para coletar o prêmio, vá em frente, pegue o prêmio! Se não, sai daqui, se você não sair... coisas ruins irão acontecer, então nem tente transferir sonhos.")
                                }
                            }
                        }

                        is DiscordAccountError.InvalidDiscordAuthorization -> {
                            Div(attrs = { classes("daily-warning") }) {
                                Text( "Você precisa entrar na sua conta do Discord antes de receber o seu prêmio!")
                            }
                        }

                        is DiscordAccountError.UserIsLorittaBanned -> {
                            Div(attrs = { classes("daily-warning") }) {
                                Text("Você está banido de usar a Loritta!")
                            }
                        }

                        is UserVerificationError.BlockedEmail -> {
                            Div(attrs = { classes("daily-warning") }) {
                                Text("Você está usando um endereço de email potencialmente malicioso!")
                            }
                        }

                        is UserVerificationError.BlockedIp -> {
                            Div(attrs = { classes("daily-warning") }) {
                                Text("Você está usando um IP potencialmente malicioso! Caso você esteja usando VPNs ou proxies, desative antes de pegar o seu prêmio!")
                            }
                        }

                        is UserVerificationError.DiscordAccountNotVerified -> {
                            Div(attrs = { classes("daily-warning") }) {
                                Text("Você ainda não verificou a sua conta do Discord! Por favor, verifique o email da sua conta antes de pegar o seu prêmio!")
                            }
                        }

                        is DailyPayoutError.AlreadyGotTheDailyRewardSameAccount -> {
                            Div(attrs = { classes("daily-warning") }) {
                                Text("Você já recebeu o seu prêmio diário hoje!")
                            }
                        }
                        is DailyPayoutError.AlreadyGotTheDailyRewardSameIp -> {
                            Div(attrs = { classes("daily-warning") }) {
                                Text("Você já recebeu o seu prêmio diário hoje!")
                            }
                        }
                        is DailyPayoutError.AlreadyGotTheDailyRewardSameIpRequiresMFA -> {
                            Div(attrs = { classes("daily-warning") }) {
                                Text("Para pegar o prêmio, você precisa ativar autenticação em duas etapas na sua conta no Discord. Para ativar, vá nas configurações da sua conta no Discord! (Recomendamos utilizar o Authy para autenticação, caso o Discord esteja rejeitando o código de autenticação, verifique se o horário do seu celular está correto)")
                            }
                        }
                    }
                }
            }
        }
    }
}