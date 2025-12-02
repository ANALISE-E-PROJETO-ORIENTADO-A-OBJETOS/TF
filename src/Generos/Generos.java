package Generos;

import Midia.IMidia;
import Observer.IObserver;

public abstract class Generos implements IMidia {

    protected IMidia wrappee;

    public Generos(IMidia midia) {
        this.wrappee = midia;
    }

    @Override
    public String getData() {
        return wrappee.getData();
    }

    @Override
    public void notifyObs() {
        wrappee.notifyObs();
    }

    @Override
    public void subscribe(IObserver observer) {
        wrappee.subscribe(observer);
    }

    @Override
    public void unsubscribe(IObserver observer) {
        wrappee.unsubscribe(observer);
    }

    @Override
    public void notifyObservers() {
        wrappee.notifyObservers();
    }
}
