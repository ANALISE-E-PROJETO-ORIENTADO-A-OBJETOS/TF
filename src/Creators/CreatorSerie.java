package Creators;

import Midia.IMidia;
import Midia.Serie;

public class CreatorSerie implements ICreator {
    @Override
    public IMidia createMidia() {
        return new Serie();
    }
    
    public IMidia createMidia(String nome, int temporadas, int episodios) {
        Serie serie = new Serie();
        serie.setNome(nome);
        serie.setTemporadas(temporadas);
        serie.setEpisodios(episodios);
        return serie;
    }
}
