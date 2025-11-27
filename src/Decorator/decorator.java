package trabalho_final_Ana_Proj_OO;

public abstract class Genero implements Midia {
    
    protected Midia midia;

    public Genero(Midia midia) {
        this.midia = midia;
    }

    @Override
    public String getTitulo() {
        return midia.getTitulo();
    }

    @Override
    public double getPreco() {
        return midia.getPreco();
    }

    @Override
    public String getDescricao() {
        return midia.getDescricao();
    }
}