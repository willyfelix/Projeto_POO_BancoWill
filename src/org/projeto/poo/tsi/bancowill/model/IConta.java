package org.projeto.poo.tsi.bancowill.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.projeto.poo.tsi.bancowill.exception.QuantiaInvalidaExcecao;

public abstract interface IConta {
	
	public abstract void depositar(BigDecimal quantia) throws QuantiaInvalidaExcecao;
	
	public abstract void transferir(IConta c, BigDecimal quantia);
	
	public abstract void sacar(BigDecimal quantia);
	
	public BigDecimal getSaldo();
	
	public boolean isStatus();
	
	public List<RegistroTransacao> getTransacoes();
	
	public void setSaldo(BigDecimal saldo);

	public Integer getNumeroConta();
	
	public void imprimirExtratoConta();

	
}

