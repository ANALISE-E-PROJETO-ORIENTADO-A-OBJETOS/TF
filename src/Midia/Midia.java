package Midia;

import java.util.ArrayList;
import java.util.List;

import Estado.Disponivel;
import Estado.IEstado;
import Observer.IObserver;

public abstract class Midia implements IMidia {
    private List<IObserver> observers = new ArrayList<>();
    private String nome;
    private IEstado estado;
    private int ano;
    private int duracaoAluguel;
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public int getDuracaoAluguel() { return duracaoAluguel; }
    public void setDuracaoAluguel(int duracaoAluguel) {}

    protected Midia(){
        this.estado = new Disponivel();
        this.nome = "Não informado";
        this.ano = 0;
        this.duracaoAluguel = 3;
    }
    protected Midia(String nome, int ano) {
        this.estado = new Disponivel();
        this.nome = nome;
        this.ano = ano;
        this.duracaoAluguel = 3;
    }

    public IEstado getEstado() { return estado; }
    public void setEstado(IEstado estado) { 
        this.estado = estado;
        notifyObservers();
    }

    public void alugar() {
        estado.alugar(this);
    }
    
    public void devolver() {
        estado.devolver(this);
    }
    
    public void renovar() {
        estado.renovar(this);
    }
    
    public String getStatus() {
        return estado.getStatus(this);
    }
    
    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }
    
    @Override
    public void unsubscribe(IObserver observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update("A midia "+ nome + " mudou de estado, ela agora está: " + getStatus());
        }
    }
}
