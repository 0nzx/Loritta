package com.mrpowergamerbr.loritta.whistlers;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

// Code Block = Trecho de c�digo
@NoArgsConstructor
public class CodeBlock implements ICode {
	public List<IPrecondition> preconditions = new ArrayList<IPrecondition>(); // Preconditions desse CodeBlock
	// S� ser� executado os c�digos desse CodeBlock caso todos os preconditions retornarem OK
	
	public List<ICode> codes = new ArrayList<ICode>(); // Mais c�digo...
}
