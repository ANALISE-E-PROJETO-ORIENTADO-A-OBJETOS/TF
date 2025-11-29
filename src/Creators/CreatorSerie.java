package Creators;

import Midia.IMidia;
import Midia.Serie;

public class CreatorSerie implements ICreator {
    @Override
    public IMidia createMidia() {
        return new Serie();
    }
    
    public IMidia createMidia(String nome, int ano, int temporadas, int episodios, int duracaoAluguel) {
        return new Serie(temporadas,  episodios, nome, ano, duracaoAluguel);
    }
}
