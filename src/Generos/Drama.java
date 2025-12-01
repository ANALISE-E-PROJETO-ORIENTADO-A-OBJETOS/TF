package Generos;

import Midia.IMidia;

public class Drama extends Generos {

    public Drama(IMidia midia) {
        super(midia);
    }

    @Override
    public String getData() {
        return super.getData() + " [Drama]";
    }
}
