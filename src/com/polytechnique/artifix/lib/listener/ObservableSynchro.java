package com.polytechnique.artifix.lib.listener;

import java.util.ArrayList;
import java.util.List;


public abstract class ObservableSynchro<T>{
    private List<ObserverSynchro<T>> mObservers = new ArrayList<>();
    private Thread mThread;


    final public void addObserver(ObserverSynchro<T> obs) {
        synchronized (getObservable()) {
            mObservers.add(obs);
        }
    }

    final public void removeObserver(ObserverSynchro<T> obs) {
        synchronized (getObservable()) {
            mObservers.remove(obs);
        }
    }

    final public void notifyObservers() {
        synchronized (getObservable()) {
            if(mThread == null) {

                mThread = new Thread(){
                    public void run() {
                        synchronized(getObservable()) {
                            for (ObserverSynchro<T> obs : mObservers) {
                                obs.onUpdate(getObservable());
                            }
                            mThread=null;
                            getObservable().notifyAll();
                        }
                    }
                };
                mThread.start();
            }
        }
    }
    abstract public T getObservable();

    final public void joinObservers() throws InterruptedException {
        synchronized(getObservable()) {
            while(mThread!=null) {
                getObservable().wait();
            }
        }
    }
}

