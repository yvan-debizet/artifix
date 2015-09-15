package com.polytechnique.artifix.fire;


import com.polytechnique.artifix.listener.ObserverSynchro;
import com.polytechnique.artifix.listener.ObservableSynchro;

public class Table extends ObservableSynchro<Table> {
    private String id;

    final private TableLine[] lines;

    final private ObserverSynchro<TableLine> listener = new ObserverSynchro<TableLine>() {
            @Override
            public void onUpdate(TableLine obj) {
                Table.this.notifyObservers();
            }
        };


    public Table(int nbLine) {
        if(nbLine<0)throw new IllegalArgumentException();
        lines = new TableLine[nbLine];
        for (int i = 0; i < nbLine; i++) {
            lines[i] = new TableLine(i);
            lines[i].addObserver(listener);
        }
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    synchronized public void setId(String id) {
        this.id = id;
        notifyObservers();
    }

    public TableLine getLine(int numero) {
        if(numero < 0 || numero >= lines.length)throw new IllegalArgumentException();
        return lines[numero];
    }
    public int getNumberLine() {
        return lines.length;
    }

    @Override
    public Table getObservable() {
        return this;
    }
}
