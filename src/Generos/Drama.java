package trabalho_final_Ana_Proj_OO;

public class Drama extends Genero {

    public Drama(Midia midia) {
        super(midia);
    }

    @Override
    public String getDescricao() {
        return midia.getDescricao() + " [Gênero: Drama]";
    }
}