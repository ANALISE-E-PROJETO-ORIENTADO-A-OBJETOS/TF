package trabalho_final_Ana_Proj_OO;

public class Comedia extends Genero {

    public Comedia(Midia midia) {
        super(midia);
    }

    @Override
    public String getDescricao() {
        return midia.getDescricao() + " [Gênero: Comédia]";
    }
}