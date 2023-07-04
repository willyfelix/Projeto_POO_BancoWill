package org.projeto.poo.tsi.bancowill.view;

import java.math.BigDecimal;

import org.projeto.poo.tsi.bancowill.dao.ClienteDAO;
import org.projeto.poo.tsi.bancowill.model.Cliente;
import org.projeto.poo.tsi.bancowill.model.Conta;
import org.projeto.poo.tsi.bancowill.model.IConta;

import java.util.Scanner;

public class Aplicacao {

	public static void main(String[] args) {
		ClienteDAO DAO = ClienteDAO.getInstance();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Bem-vindo ao Banco Will! O que você gostaria de fazer?");
			System.out.println("1. Cadastrar novo cliente");
			System.out.println("2. Selecionar cliente existente");
			System.out.println("3. Listar clientes");
			System.out.println("4. Sair");

			int opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
			case 1:
				System.out.println("Digite o nome do cliente:");
				String nome = scanner.nextLine();
				System.out.println("Digite o CPF do cliente:");
				String cpf = scanner.nextLine();
				Cliente novoCliente = new Cliente(cpf, nome);
				DAO.salvarCliente(novoCliente);
				break;

			case 2:
				System.out.println("Digite o CPF do cliente:");
				cpf = scanner.next();

				Cliente cliente = DAO.localizarClientePorCPF(cpf);
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
				System.out.println("6. Remover Conta");
				System.out.println("7. Remover Cliente");
				System.out.println("8. Exibir Balanço das Contas");
				System.out.println("9. Exibir Extrato");



				opcao = scanner.nextInt();
				scanner.nextLine();

				switch (opcao) {
				case 1:
					Conta conta = new Conta();
					cliente.adicionarConta(conta);
					DAO.atualizar(cliente);
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
					System.out.println("Em qual conta deseja realizar o deposito?");
					int opcaoContaDeposito = scanner.nextInt();
					IConta contaLocalizada = cliente.localizarContaNumero(opcaoContaDeposito);
					if (contaLocalizada != null) {
						System.out.println("Insira o valor da quantia a ser depositada: ");
						BigDecimal quantia = scanner.nextBigDecimal();
						contaLocalizada.depositar(quantia);
						DAO.atualizar(cliente);
					}
					break;
				case 4:
					System.out.println("Em qual conta deseja realizar o saque?");
					int opcaoContaSaque = scanner.nextInt();
					IConta contaSaque = cliente.localizarContaNumero(opcaoContaSaque);
					if (contaSaque != null) {
						System.out.println("Insira o valor da quantia a ser depositada: ");
						BigDecimal quantia = scanner.nextBigDecimal();
						contaSaque.sacar(quantia);
						DAO.atualizar(cliente);
					}
					break;
				
				case 5:
					System.out.println("De qual conta deseja realizar a transferência?");
					int numeroDaContaSaida = scanner.nextInt();
					IConta contaSaida = cliente.localizarContaNumero(numeroDaContaSaida);
					System.out.println("Qual conta deseja receber a transferência?");
					int numeroDaContaDestino = scanner.nextInt();
					IConta contaDestino = cliente.localizarContaNumero(numeroDaContaDestino);
					if (contaSaida != null && contaDestino != null) {
						System.out.println("Insira o valor da quantia a ser transferido: ");
						BigDecimal quantia = scanner.nextBigDecimal();
						contaSaida.transferir(contaDestino, quantia);
						DAO.atualizar(cliente);
					}
					
					break;
					
				case 6:
					System.out.println("Qual o numero da conta que deseja remover?");
					int numeroContaRemover = scanner.nextInt();
					IConta contaRemover = cliente.localizarContaNumero(numeroContaRemover);
					if (contaRemover != null) {
						cliente.removerConta(contaRemover);				
					}
					
					break;
					
				case 7:
					DAO.removerClientePorCPF(cliente.getCpf());
					System.out.println("Cliente removido com sucesso!");					
					break;
				case 8:
					cliente.balancoEntreContas();
					break;
				case 9:
					System.out.println("Qual conta deseja exibir extrato?");
					int numeroContaExtrato = scanner.nextInt();
					IConta contaExtrato = cliente.localizarContaNumero(numeroContaExtrato);
					if (contaExtrato != null) {
						contaExtrato.imprimirExtratoConta();
					}		
					break;
					
					
				default:
					System.out.println("Opção inválida");
					break;
				}
				break;
				
			case 3:
				DAO.listarClientes();
				break;
			case 4:
				System.out.println("Até logo!");
				System.exit(0);

			default:
				System.out.println("Opção inválida");
				break;
			}
		}
	}

}
