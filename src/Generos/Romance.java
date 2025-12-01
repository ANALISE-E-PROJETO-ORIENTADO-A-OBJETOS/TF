package Generos;

import Midia.IMidia;

public class Romance extends Generos {

    public Romance(IMidia midia) {
        super(midia);
    }

    @Override
    public String getData() {
        return super.getData() + " [Romance]";
    }
}
