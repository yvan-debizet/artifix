package com.polytechnique.artifix.fire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    synchronized public void addFire(Fire e) {
        if(e==null)throw new NullPointerException();
        notifyObservers();
        if(fires.contains(e))return;
        fires.add(e);
        e.setLine(this);
    }

    public List<Fire> getFires() {
        return Collections.unmodifiableList(fires);
    }

    /**
     * @param o
     * @return
     * @see java.util.ArrayList#remove(java.lang.Object)
     */
    synchronized public void removeFire(Fire e) {
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
