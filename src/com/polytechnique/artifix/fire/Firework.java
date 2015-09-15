package com.polytechnique.artifix.fire;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


import com.polytechnique.artifix.listener.ObservableSynchro;
import com.polytechnique.artifix.listener.ObserverSynchro;

public class Firework extends ObservableSynchro<Firework> {

    private final List<Fire> fires = new ArrayList<Fire>();
    private final List<Table> tables = new ArrayList<Table>();

    private final ObserverSynchro<Table> tableObserver = new ObserverSynchro<Table>() {
        @Override
        public void onUpdate(Table obj) {
            notifyObservers();
        }

    };

    private final ObserverSynchro<Fire> fireObserver = new ObserverSynchro<Fire>() {
        @Override
        public void onUpdate(Fire obj) {
            notifyObservers();
        }
    };




    @Override
    public Firework getObservable() {
        return this;
    }


    /**
     * @param e
     * @return
     * @see java.util.List#add(java.lang.Object)
     */
    synchronized public boolean addFire(Fire e) {
        if(e==null) throw new NullPointerException();
        if(fires.contains(e))return false;
        notifyObservers();
        e.addObserver(fireObserver);
        return fires.add(e);
    }

    /**
     * @return
     * @see java.util.List#iterator()
     */
    public List<Fire> getFires() {
        return Collections.unmodifiableList(fires);
    }

    /**
     * @param e
     * @return
     * @see java.util.List#remove(java.lang.Object)
     */
    synchronized public boolean removeFire(Fire e) {
        if(e==null) throw new NullPointerException();
        if(!fires.contains(e))return false;
        notifyObservers();
        e.removeObserver(fireObserver);
        return fires.remove(e);
    }


    /**
     * @param e
     * @return
     * @see java.util.List#add(java.lang.Object)
     */
    synchronized public boolean addTable(Table e) {
        if(e==null) throw new NullPointerException();
        if(tables.contains(e)) return false;
        notifyObservers();
        e.addObserver(tableObserver);
        return tables.add(e);
    }
    /**
     * @return
     * @see java.util.List#iterator()
     */
    public List<Table> getTables() {
        return Collections.unmodifiableList(tables);
    }

    /**
     * @param o
     * @return
     * @see java.util.List#remove(java.lang.Object)
     */
    synchronized public boolean removeTable(Table t) {
        if(t==null) throw new NullPointerException();
        if(!tables.contains(t)) return false;
        notifyObservers();
        t.removeObserver(tableObserver);
        return tables.remove(t);
    }

    synchronized public void clear() {

        for (Fire f : fires) {
            f.removeObserver(fireObserver);
        }

        for (Table t : tables) {
            t.removeObserver(tableObserver);
        }

        fires.clear();
        tables.clear();
        notifyObservers();
    }

    /**
     * @param c
     * @return
     * @see java.util.List#addAll(java.util.Collection)
     */
    synchronized public void addAllTable(Collection<? extends Table> c) {
        for (Table t : c) {
            addTable(t);
        }
    }

    /**
     * @param c
     * @return
     * @see java.util.List#addAll(java.util.Collection)
     */
    synchronized public void addAllFire(Collection<? extends Fire> c) {
        for (Fire f : c) {
            addFire(f);
        }
    }



    synchronized public void set(Firework f) {
		clear();
		addAllFire(f.fires);
		addAllTable(f.tables);
		notifyObservers();
	}
}
