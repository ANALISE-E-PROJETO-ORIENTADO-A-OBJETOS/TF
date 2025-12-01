package teste;

import Midia.Filme;
import Midia.IMidia;
import Generos.Comedia;
import Generos.Drama;
import Generos.Fantasia;
import Generos.Terror;
import Generos.Romance;

public class TesteGeneros {
    public static void main(String[] args) {

        System.out.println("===== TESTANDO DECORATOR DE GÊNEROS =====\n");

        IMidia filme = new Filme(120, "Interestelar", 2014);

        System.out.println("Sem gêneros:");
        System.out.println(filme.getData());
        System.out.println();

        filme = new Drama(filme);
        System.out.println("Com Drama:");
        System.out.println(filme.getData());
        System.out.println();

        filme = new Fantasia(filme);
        System.out.println("Com Drama + Fantasia:");
        System.out.println(filme.getData());
        System.out.println();

        filme = new Comedia(filme);
        filme = new Terror(filme);
        filme = new Romance(filme);

        System.out.println("Com todos os gêneros:");
        System.out.println(filme.getData());
        System.out.println();

        System.out.println("===== FIM DO TESTE =====");
    }
}
