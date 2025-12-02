package Midia;

public class Filme extends Midia {
    private int duracao;

    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }

    public Filme(){
        super();
        this.duracao = 0;
    }
    public Filme(int duracao, String nome, int ano) {
        super(nome, ano);
        this.duracao = duracao; }

    @Override
    public String getData() {
        return "O Filme " + getNome() + " possui: "+ duracao + " minutos de duração";
    }
}