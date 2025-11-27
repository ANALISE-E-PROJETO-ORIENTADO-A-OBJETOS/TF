package Creators;

import Midia.Filme;
import Midia.IMidia;

public class CreatorFilme implements ICreator {
    @Override
    public IMidia createMidia() {
        return new Filme();
    }
    
    public IMidia createMidia(String nome, int duracao) {
        Filme filme = new Filme();
        filme.setNome(nome);
        filme.setDuracao(duracao);
        return filme;
    }
}
