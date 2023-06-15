package org.projeto.poo.tsi.bancowill.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.projeto.poo.tsi.bancowill.enumerator.TipoTransacao;
import org.projeto.poo.tsi.bancowill.exception.ContaDesativadaExcecao;
import org.projeto.poo.tsi.bancowill.exception.QuantiaInvalidaExcecao;

public class Conta implements Serializable, IConta  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4281663695189772633L;
	private Integer numeroConta;
	private BigDecimal saldo;
	private String tipo;
	private boolean status;
	private List<RegistroTransacao> transacoes;

	public Conta() {
		Random random = new Random();

		this.numeroConta = random.nextInt(1000)+1;
		this.saldo = new BigDecimal(0);
		this.status = true;
		transacoes = new ArrayList<>();
	}
	
	public String toString() {
		return "Conta [Numero=" + numeroConta + ", Saldo=" + saldo + ", Tipo=" + tipo + ", Status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Conta.this.hashCode();
		result = prime * result + Objects.hash(numeroConta);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return numeroConta == other.numeroConta;
	}

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(int numeroConta) {
		this.numeroConta = numeroConta;
	}


	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	

	@Override
	public void depositar(BigDecimal quantia) throws QuantiaInvalidaExcecao {
		if (this.status == false) {
		       throw new ContaDesativadaExcecao("Operação não permitida. Conta desativada.");
		}
		
		this.saldo = this.saldo.add(quantia);
		transacoes.add(new RegistroTransacao(quantia, TipoTransacao.CREDITO, LocalDateTime.now()));
	}

	@Override
	public void transferir(IConta c, BigDecimal quantia) {
		if (this.status == false) {
		       throw new ContaDesativadaExcecao("Operação não permitida. Conta desativada.");
		}
		if (c.isStatus() == false) {
		       throw new ContaDesativadaExcecao("Operação não permitida. Conta desativada.");
		}
		
		this.sacar(quantia);
		c.depositar(quantia);	
		transacoes.add(new RegistroTransacao(quantia, TipoTransacao.DEBITO, LocalDateTime.now()));
		c.getTransacoes().add(new RegistroTransacao(quantia, TipoTransacao.CREDITO, LocalDateTime.now()));
	}

	@Override
	public void sacar(BigDecimal quantia) {
		if (this.status == false) {
		       throw new ContaDesativadaExcecao("Operação não permitida. Conta desativada.");
		}
		
		if(this.saldo.compareTo(quantia) >= 0) {
			this.saldo = this.saldo.subtract(quantia);
			transacoes.add(new RegistroTransacao(quantia, TipoTransacao.DEBITO, LocalDateTime.now()));
		} else {
			throw new QuantiaInvalidaExcecao("Não foi possível sacar essa quantia!");
		}
	}

	@Override
	public List<RegistroTransacao> getTransacoes() {
		return this.transacoes;
	}

	@Override
	public BigDecimal getSaldo() {
		return this.saldo;
	}
	
	public void imprimirExtratoConta() {
		for (RegistroTransacao transacao : transacoes) {
			System.out.println(transacao);
		}
	}

}
