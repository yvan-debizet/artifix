package com.polytechnique.artifix.fire;

import com.polytechnique.artifix.listener.ObservableSynchro;

class Fire extends ObservableSynchro<Fire> {
    private TableLine line;



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
        if(this.line!=null) this.line.remove(this);
        this.line = line;
        if(line!=null) line.add(this);
    }

}
