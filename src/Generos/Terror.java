package Generos;

import Midia.IMidia;

public class Terror extends Generos {

    public Terror(IMidia midia) {
        super(midia);
    }

    @Override
    public String getData() {
        return super.getData() + " [Terror]";
    }

}
