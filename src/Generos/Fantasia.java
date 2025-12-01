package Generos;

import Midia.IMidia;

public class Fantasia extends Generos {

    public Fantasia(IMidia midia) {
        super(midia);
    }

    @Override
    public String getData() {
        return super.getData() + " [Fantasia]";
    }
}
