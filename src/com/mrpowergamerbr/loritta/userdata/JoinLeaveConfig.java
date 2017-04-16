package com.mrpowergamerbr.loritta.userdata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinLeaveConfig {
	private boolean isEnabled = false;
	private boolean tellOnJoin = true;
	private boolean tellOnLeave = true;
	private String joinMessage = "👉 %UserMention% entrou no servidor!";
	private String leaveMessage = "👈 %UserMention% saiu do servidor!";
	private String canalJoin = "bate-papo";
	private String canalLeave = "bate-papo";
}
