package Creators;

import Midia.Filme;
import Midia.IMidia;

public class CreatorFilme implements ICreator {
    @Override
    public IMidia createMidia() {
        return new Filme();
    }
    
    public IMidia createMidia(String nome, int ano, int duracao) {
        return new Filme(duracao, nome, ano);
    }
}
