package com.polytechnique.artifix.fire;

import java.util.ArrayList;
import java.util.Iterator;

import com.polytechnique.artifix.listener.ObservableSynchro;

public class TableLine extends ObservableSynchro<TableLine>{

    private final int line;
    private ArrayList<Fire> fires = new ArrayList<Fire>();

    public TableLine(int line) {
        this.line = line;
    }


    /**
     * @param e
     * @return
     * @see java.util.ArrayList#add(java.lang.Object)
     */
    synchronized public void add(Fire e) {
        if(fires.contains(e))return;
        notifyObservers();
        fires.add(e);
        e.setLine(this);
    }

    /**
     * @return
     * @see java.util.ArrayList#iterator()
     */
    public Iterator<Fire> iterator() {
        return fires.iterator();
    }

    /**
     * @param o
     * @return
     * @see java.util.ArrayList#remove(java.lang.Object)
     */
    synchronized public void remove(Fire e) {
        if(!fires.contains(e))return;
        notifyObservers();
        fires.remove(e);
        e.setLine(null);
    }

    @Override
    public TableLine getObservable() {
        return this;
    }

    /**
     * @return the line
     */
    public int getLine() {
        return line;
    }

}
