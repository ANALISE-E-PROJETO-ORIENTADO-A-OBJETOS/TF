package Midia;

public class Filme extends Midia {
    private int duracao;

    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }

    @Override
    public String getData() {
        return "O Filme " + getNome() + "possui: "+ duracao + " minutos de duração";
    }
    
    @Override
    public void doSomething() {
        notifyObservers();
    }
}