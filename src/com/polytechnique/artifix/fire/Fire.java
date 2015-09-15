package com.polytechnique.artifix.fire;

import com.polytechnique.artifix.listener.ObservableSynchro;

public class Fire extends ObservableSynchro<Fire> {
    private TableLine line;
    private FireItem item;

    @Override
    public Fire getObservable() {
        return this;
    }

    /**
     * @return the line
     */
    synchronized public TableLine getLine() {
        return line;
    }

    /**
     * @param line the line to set
     */
    synchronized public void setLine(TableLine line) {
        if(line==this.line) return;
        notifyObservers();
        if(this.line!=null) this.line.removeFire(this);
        this.line = line;
        if(line!=null) line.addFire(this);
    }

    synchronized public void setFireItem(FireItem item) {
        if(item==this.item) return;
        notifyObservers();
        this.item = item;
    }
}
