package com.mrpowergamerbr.loritta.whistlers;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Whistler {
	private String whistlerName = "Meu Primeiro Whistler";
	// Cont�m todos os c�digos references a esse whistler
	
	// Normalmente apenas cont�m um CodeBlock
	public List<ICode> codes = new ArrayList<ICode>();
	
	public Whistler(String whistlerName) {
		this.whistlerName = whistlerName;
	}
}
