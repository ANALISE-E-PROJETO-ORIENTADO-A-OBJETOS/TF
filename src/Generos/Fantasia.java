package trabalho_final_Ana_Proj_OO;

public class Fantasia extends Genero {

    public Fantasia(Midia midia) {
        super(midia);
    }

    @Override
    public String getDescricao() {
        return midia.getDescricao() + " [Gênero: Fantasia]";
    }

}