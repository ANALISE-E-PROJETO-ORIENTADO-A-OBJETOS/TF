package trabalho_final_Ana_Proj_OO;

public class Terror extends Genero {

    public Terror(Midia midia) {
        super(midia);
    }

    @Override
    public String getDescricao() {
        // Adiciona a tag do gênero à descrição existente
        return midia.getDescricao() + " [Gênero: Terror]";
    }

    @Override
    public double getPreco() {
        // Exemplo: Filmes de terror podem ter um acréscimo ou preço padrão
        return midia.getPreco() + 1.50; 
    }
}