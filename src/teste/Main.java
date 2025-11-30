package teste;

import Cliente.Cliente;
import Locadora.Locadora;
import Midia.Midia;
import Estado.Alugado; 

public class Main {

    public static void main(String[] args) {
    
       
        Locadora locadora = Locadora.getInstance();
        
       
        locadora.adicionarCliente(new Cliente("Joao Silva"));
        locadora.adicionarCliente(new Cliente("Maria Souza"));

       
        locadora.adicionarFilme("Matrix", 1999, 120);
        locadora.adicionarSerie("Game of Thrones", 2011, 8, 73, 7);
        
     
        Midia matrix = locadora.procurarMidiaNoCatalogo("Matrix");
        
        System.out.println("\n");
        
  
        System.out.println("Teste 1 - locação ok");
      
        locadora.locarMidia("Joao Silva", "Matrix", 20); 
        
        System.out.println("estado inicial do filme -> " + matrix.getStatus());
        System.out.println("Status Joao -> " + locadora.checarCliente("Joao Silva"));
        
        
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("Teste 2 - Atraso e bloqueio");
        
        
        // força atraso
        if (matrix.getEstado() instanceof Alugado) {
            ((Alugado) matrix.getEstado()).marcarAtraso(matrix); 
        }

        
        System.out.println("estado da midia -> " + matrix.getStatus());
        System.out.println("Status do Joao -> " + locadora.checarCliente("Joao Silva"));

        
        System.out.println("Joao tenta alugar 'Game of Thrones' (DEVE FALHAR):");
        locadora.locarMidia("Joao Silva", "Game of Thrones", 50); 
        
       

        System.out.println("\n");
        System.out.println("\n");
        System.out.println("teste 3: devolução e desbloqueio");
        
       
        locadora.devolverMidia("Matrix", "Joao Silva"); 
        
        System.out.println("estado da midia após devolução -> " + matrix.getStatus());
        System.out.println("status do Joao -> " + locadora.checarCliente("Joao Silva"));
        
        
        System.out.println("Joao tenta alugar 'Game of Thrones' novamente (deve funcionar):");
        locadora.locarMidia("Joao Silva", "Game of Thrones", 50); 

    }
}