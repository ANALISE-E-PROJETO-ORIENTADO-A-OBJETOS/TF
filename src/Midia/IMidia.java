package Midia;

import Observer.IObservable;

public interface IMidia extends IObservable {
    String getData();
    String getStatus();
    
    void alugar();
    void devolver();
    void renovar();
}
