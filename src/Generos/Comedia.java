package Generos;

import Midia.IMidia;

public class Comedia extends Generos {

    public Comedia(IMidia midia) {
        super(midia);
    }

    @Override
    public String getData() {
        return super.getData() + " [Comédia]";
    }
}
