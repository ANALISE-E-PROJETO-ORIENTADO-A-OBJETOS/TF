import Cliente.Cliente;
import Estado.Alugado;
import Estado.Disponivel;
import Locadora.Locadora;
import Midia.IMidia;
import Midia.Midia;

import java.util.Scanner;
void main() {
    int option = 1;
    Scanner scanner = new Scanner(System.in);
    Locadora locadora = Locadora.getInstance();

    while(option != 0) {
        System.out.println("\nMENU DA LOCADORA\n[1] - Adicionar cliente\n[2] - Adicionar mídia\n[3] - Realizar locação\n[4] - Realizar devolução\n");
        option = scanner.nextInt();
        scanner.nextLine();
        switch(option){
            case 1:
                System.out.println("Digite o nome do cliente: ");
                locadora.adicionarCliente(new Cliente(scanner.nextLine()));
                System.out.println("Cliente cadastrado.\n");
                break;
            case 2:
                System.out.println("[1] - Adicionar filme\n[2] - Adicionar série\n");
                option = scanner.nextInt();
                int genero = 0;
                List<String> generos = new ArrayList<>();
                while(genero < 6){
                    System.out.println("Adicione um gênero à mídia:\n[1] - Terror\n[2] - Drama\n[3] - Comédia\n[4] - Romance\n[5] - Fantasia\n[6] - Nenhum");
                    genero = scanner.nextInt();
                    scanner.nextLine();
                    switch(genero){
                        case 1:
                            generos.add("terror");
                            break;
                        case 2:
                            generos.add("drama");
                            break;
                        case 3:
                            generos.add("comedia");
                            break;
                        case 4:
                            generos.add("romance");
                            break;
                        case 5:
                            generos.add("fantasia");
                            break;
                    }
                }
                String nomeMidia;
                int anoMidia;
                System.out.println("Digite o título da mídia: ");
                nomeMidia = scanner.nextLine();
                System.out.println("Digite o ano da mídia " + nomeMidia + ": ");
                anoMidia = scanner.nextInt();
                scanner.nextLine();
                if(option == 1){
                    int duracaoFilme;
                    System.out.println("Digite o duracao do filme " + nomeMidia + ": ");
                    duracaoFilme = scanner.nextInt();
                    scanner.nextLine();
                    locadora.adicionarFilme(nomeMidia, anoMidia, duracaoFilme, generos);
                }
                else if(option == 2){
                    int temporadas;
                    int episodios;
                    System.out.println("Digite o número de temporadas da série " + nomeMidia + ": ");
                    temporadas = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Digite o número de episódios da série " + nomeMidia + ": ");
                    episodios = scanner.nextInt();
                    scanner.nextLine();
                    locadora.adicionarSerie(nomeMidia, anoMidia, temporadas, episodios, generos);
                }
                else{
                    break;
                }
                IMidia midia = locadora.procurarMidiaNoCatalogo(nomeMidia);
                if(midia != null){
                    System.out.println("Mídia adicionada:\n" + midia.getData());
                }
                else{
                    System.out.println("Erro na adição da mídia.\n");
                }
                break;
            case 3:
                int sair = 0;
                int valorDiaria = 4;
                String nomeCliente;
                System.out.println("Digite o nome do cliente: ");
                nomeCliente = scanner.nextLine();
                while(sair == 0){
                    System.out.println("Digite o nome da mídia: ");
                    nomeMidia = scanner.nextLine();
                    // Tenta locar
                    boolean locou = locadora.locarMidia(nomeCliente, nomeMidia,  valorDiaria, scanner);

                    if (locou) {
                        System.out.println("✅ Mídia adicionada ao carrinho.");
                    } else {
                        System.out.println("❌ Falha ao adicionar ao carrinho.");
                    }
                    System.out.println("[0] - Continuar adicionando\n[1] - Prosseguir para o pagamento\n");
                    sair = scanner.nextInt();
                    scanner.nextLine();
                }
                boolean pagou = locadora.finalizarPagamento(nomeCliente);

                if (pagou) {
                    System.out.println("✅ Pagamento processado.");
                } else {
                    System.out.println("❌ Erro no pagamento.");
                }
                System.out.println();
                break;
            case 4:
                System.out.println("Digite o nome do cliente: ");
                nomeCliente = scanner.nextLine();
                System.out.println("Digite o título da mídia: ");
                nomeMidia = scanner.nextLine();
                locadora.devolverMidia(nomeMidia, nomeCliente);
                System.out.println("✅ Mídia devolvida.");
                break;
        }
    }

}
