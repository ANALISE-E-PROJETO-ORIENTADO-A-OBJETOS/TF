package trabalho_final_Ana_Proj_OO; 
public abstract class Generos {
    
   
    protected String descricao = "Gênero Desconhecido";
  
    
    public String getDescricao() {
        return descricao;
    }
  
   
    public abstract double getValor();
}