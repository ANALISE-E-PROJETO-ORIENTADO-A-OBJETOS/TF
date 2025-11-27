package Midia;

public class Serie extends Midia {
    private int temporadas;
    private int episodios;

    public int getTemporadas() { return temporadas; }
    public void setTemporadas(int temporadas) { this.temporadas = temporadas; }
    public int getEpisodios() { return episodios; }
    public void setEpisodios(int episodios) { this.episodios = episodios; }

    @Override
    public String getData() {
        return "A Série " + getNome() + "possui: "+ temporadas + " Temporadas e " + episodios + "EPs";
    }

    @Override
    public void doSomething() {
        notifyObservers();
    }

    
}
