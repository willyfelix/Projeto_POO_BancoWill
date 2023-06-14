package org.projeto.poo.tsi.bancowill.model;

import java.util.ArrayList;
import java.util.Objects;


public class Conta {
	public class ContaBancaria {
		private int numeroConta;
		private double saldo;
		private String titular;
		private boolean status;

		public ContaBancaria(int numeroConta, double saldo) {
			this.numeroConta = numeroConta;
			this.saldo = saldo;
			this.status = true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
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
			ContaBancaria other = (ContaBancaria) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return numeroConta == other.numeroConta;
		}

		private Conta getEnclosingInstance() {
			return Conta.this;
		}
		
	}
}
