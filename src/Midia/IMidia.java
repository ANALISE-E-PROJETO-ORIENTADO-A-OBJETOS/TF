package Midia;

import Observer.IObservable;

public interface IMidia extends IObservable {
    String getData();

    void notifyObs();
    
    void alugar();
    void devolver();
    void renovar();
    String getStatus();

}
