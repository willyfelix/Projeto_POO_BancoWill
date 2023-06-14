package org.projeto.poo.tsi.bancowill.view;

import java.math.BigDecimal;
import org.projeto.poo.tsi.bancowill.model.Cliente;
import java.util.Scanner;

public class Aplicacao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Bem-vindo ao Banco Will! O que você gostaria de fazer?");
			System.out.println("1. Cadastrar novo cliente");
			System.out.println("2. Selecionar cliente existente");
			System.out.println("3. Sair");

			int opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
			case 1:
				System.out.println("Digite o nome do cliente:");
				String nome = scanner.nextLine();
				System.out.println("Digite o CPF do cliente:");
				String cpf = scanner.nextLine();
				Cliente cliente = new Cliente(cpf, nome);
				break;

			case 2:
				System.out.println("Digite o CPF do cliente:");
				cpf = scanner.next();

				if (cliente == null) {
					System.out.println("Cliente não encontrado");
					break;
				}

				System.out.println("Cliente selecionado: " + cliente.getNome());
				System.out.println("\nO que você gostaria de fazer?\n");
				System.out.println("1. Criar nova conta");
				System.out.println("2. Ver informações das contas");
				System.out.println("3. Realizar Deposito");
				System.out.println("4. Realizar Saque");
				System.out.println("5. Realizar Transferencia");

				opcao = scanner.nextInt();
				scanner.nextLine();

				switch (opcao) {
				case 1:
					ContaPoupanca conta = new ContaPoupanca();
					cliente.adicionarConta(conta);
					PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
					System.out.println("Conta criada com sucesso!");
					break;

				case 2:
					if (cliente.getContas().size() == 0) {
						System.err.println("Nao ha contas associada a este cliente.");
					} else {
						for (IConta c : cliente.getContas()) {
							System.out.println(c);
						}
					}
					break;
				case 3:
					if (cliente.getContas().size() == 0) {
						System.err.println("Nao ha contas associada a este cliente.");
					} else {
						for (IConta c : cliente.getContas()) {
							System.out.println(c);
						}
					}
					System.out.println("Em qual conta deseja realizar o deposito?");
					int opcaoContaDeposito = 0;
					double quantia = 0.0;
					opcaoContaDeposito = scanner.nextInt();
					System.out.println("Insira o valor da quantia a ser depositada: ");
					quantia = scanner.nextDouble();
					IConta temp = cliente.localizarContaNumero(opcaoContaDeposito);
					if (temp != null) {
						temp.depositar(new BigDecimal(quantia));					}
					break;
				default:
					System.out.println("Opção inválida");
					break;
				}
				break;
			case 3:
				System.out.println("Até logo!");
				System.exit(0);

			default:
				System.out.println("Opção inválida");
				break;
			}
		}
	}

}
