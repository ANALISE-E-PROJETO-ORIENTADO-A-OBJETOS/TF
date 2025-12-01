package teste;

import Cliente.Cliente;
import Locadora.Locadora;
import Midia.Midia;
import Estado.Alugado;
import java.util.Scanner; // Import necessário

public class Main {

	public static void main(String[] args) {

		// Criação do Scanner UMA ÚNICA VEZ para evitar fechar System.in
		Scanner scanner = new Scanner(System.in);
		
		Locadora locadora = Locadora.getInstance();
		
		
		locadora.adicionarCliente(new Cliente("Joao Silva"));
		locadora.adicionarCliente(new Cliente("Maria Souza"));

		
		locadora.adicionarFilme("Matrix", 1999, 120);
		locadora.adicionarSerie("Game of Thrones", 2011, 8, 73, 7);
		locadora.adicionarFilme("Interstellar", 2014, 169); // Nova midia para teste de carrinho
		
	
		Midia matrix = locadora.procurarMidiaNoCatalogo("Matrix");
		Midia interstellar = locadora.procurarMidiaNoCatalogo("Interstellar");
		Cliente maria = locadora.procurarCliente("Maria Souza");
		
		System.out.println("\n");
		
	
		System.out.println("Teste 1 - locação Joao (adiciona ao carrinho, cria locacao e paga)");
	

		locadora.locarMidia("Joao Silva", "Matrix", 20, scanner);	
		
		System.out.println("Estado do filme Matrix antes do pagamento -> " + matrix.getStatus()); 
		
		System.out.println("\n--- FINALIZANDO PAGAMENTO JOAO (Total esperado: R$ 20) ---");
		System.out.println("Valor no Carrinho de Joao: R$ " + maria.getCarrinho().getValorTotalAPagar() + ",00");
		locadora.finalizarPagamento("Joao Silva"); 
		System.out.println("--- PAGAMENTO CONCLUÍDO ---\n");
		
		System.out.println("Estado do filme Matrix APÓS PAGAMENTO -> " + matrix.getStatus()); 
		
		
		System.out.println("\n");
		
		System.out.println("=========================================================");
		System.out.println("Teste 4 - Carrinho com múltiplos itens e Soma Total (Maria)");
		System.out.println("=========================================================");
		
		// Maria adiciona 1º item: Game of Thrones (R$ 50)
		locadora.locarMidia("Maria Souza", "Game of Thrones", 50, scanner);
		System.out.println("Total atual no Carrinho de Maria: R$ " + maria.getCarrinho().getValorTotalAPagar() + ",00"); // Esperado: 50
		
		// Maria adiciona 2º item: Interstellar (R$ 25)
		locadora.locarMidia("Maria Souza", "Interstellar", 25, scanner);
		System.out.println("Total final no Carrinho de Maria: R$ " + maria.getCarrinho().getValorTotalAPagar() + ",00"); // Esperado: 75
		
		System.out.println("\n--- FINALIZANDO PAGAMENTO MARIA (Total esperado: R$ 75) ---");
		locadora.finalizarPagamento("Maria Souza");
		System.out.println("--- PAGAMENTO CONCLUÍDO ---\n");
		
		System.out.println("Estado do filme Interstellar APÓS PAGAMENTO -> " + interstellar.getStatus());
		System.out.println("Valor final no Carrinho de Maria após pagamento: R$ " + maria.getCarrinho().getValorTotalAPagar() + ",00"); // Esperado: 0
		
		System.out.println("\n");
		System.out.println("\n");
		
		System.out.println("Teste 2 - Atraso e bloqueio (Joao)");
		
		// O filme Matrix já está alugado por João do Teste 1. Força o atraso:
		if (matrix.getEstado() instanceof Alugado) {
			((Alugado) matrix.getEstado()).marcarAtraso(matrix);	
		}

		
		System.out.println("Estado da midia Matrix -> " + matrix.getStatus());
		System.out.println("Status do Joao (checarCliente) -> " + locadora.checarCliente("Joao Silva"));

		
		System.out.println("Joao tenta alugar 'Game of Thrones' (DEVE FALHAR pois está bloqueado):");
		locadora.locarMidia("Joao Silva", "Game of Thrones", 50, scanner);	
		
		

		System.out.println("\n");
		System.out.println("\n");
		System.out.println("teste 3: devolução e desbloqueio (Joao)");
		
		
		locadora.devolverMidia("Matrix", "Joao Silva");	
		
		System.out.println("Estado da midia Matrix após devolução -> " + matrix.getStatus());
		System.out.println("Status do Joao (checarCliente) -> " + locadora.checarCliente("Joao Silva"));
		
		
		System.out.println("Joao tenta alugar 'Game of Thrones' novamente (deve funcionar):");
		locadora.locarMidia("Joao Silva", "Game of Thrones", 50, scanner);	
		
		System.out.println("\n--- FINALIZANDO SEGUNDO PAGAMENTO JOAO ---");
		locadora.finalizarPagamento("Joao Silva"); 
		System.out.println("--- PAGAMENTO CONCLUÍDO ---");
		
		// Fecha o Scanner no final do main
		scanner.close();
	}
}