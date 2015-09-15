package com.polytechnique.artifix;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.polytechnique.artifix.fire.Fire;
import com.polytechnique.artifix.fire.FireItem;
import com.polytechnique.artifix.fire.Firework;
import com.polytechnique.artifix.fire.Table;
import com.polytechnique.artifix.fire.TableLine;
import com.polytechnique.artifix.listener.ObserverSynchro;

public class FiresTest {
    private Firework f;

    private Fire o1;
    private Fire o2;
    private Fire o3;


    private Table t1;
    private Table t2;

    private Observer<Firework> of;
    private Observer<Table> ot;
    private Observer<Fire> oo;

    @Before
    public void init() {
        f= new Firework();
        t1 = new Table(16);
        t2 = new Table(16);
        o1 = new Fire();
        o2 = new Fire();
        o3 = new Fire();
        of = new Observer<Firework>();
        ot = new Observer<Table>();
        oo = new Observer<Fire>();

    }

    @Test
    public void connectTest(){

        assertTrue(!contains(t1) && !contains(t2));
        f.addTable(t1);
        assertTrue(contains(t1) && !contains(t2));
        f.addTable(t2);
        assertTrue(contains(t1) && contains(t2));

        assertTrue(!contains(o1) && !contains(o2) && !contains(o2));
        f.addFire(o1);
        assertTrue(contains(o1) && !contains(o2) && !contains(o2));
        f.addFire(o2);
        assertTrue(contains(o1) && contains(o2) && !contains(o3));
        f.addFire(o3);
        assertTrue(contains(o1) && contains(o2) && contains(o3));
        f.removeFire(o2);
        assertTrue(contains(o1) && !contains(o2) && contains(o3));


        o1.setLine(t1.getLine(10));
        assertTrue(connect(t1.getLine(10), o1));

        o2.setLine(t1.getLine(10));
        assertTrue(connect(t1.getLine(10), o1));
        assertTrue(connect(t1.getLine(10), o2));

        o1.setLine(null);
        assertTrue(!t1.getLine(10).getFires().contains(o1) && o1.getLine()==null);
        assertTrue(connect(t1.getLine(10), o2));


        t1.getLine(10).addFire(o1);
        assertTrue(connect(t1.getLine(10), o1));
        assertTrue(connect(t1.getLine(10), o2));

        t1.getLine(10).removeFire(o1);
        assertTrue(!t1.getLine(10).getFires().contains(o1) && o1.getLine()==null);
        assertTrue(connect(t1.getLine(10), o2));

    }
    @Test
    public void ListenFireTest() throws InterruptedException {
        synchronized(o1) {
            oo.reset();
            o1.addObserver(oo);
            o1.joinObservers();
            oo.isGood(1);
            o1.setLine(t1.getLine(3));
            o1.joinObservers();
            oo.isGood(2);
            o1.setFireItem(new FireItem());
            o1.joinObservers();
            oo.isGood(3);
        }
    }

    @Test
    public void ListenTableTest() throws InterruptedException {
        synchronized(t1) {
            ot.reset();
            t1.addObserver(ot);
            t1.joinObservers();
            ot.isGood(1);
            t1.getLine(6).addFire(o1);
            t1.joinObservers();
            ot.isGood(2);
            t1.setId("toto");
            t1.joinObservers();
            ot.isGood(3);


        }
    }

    @Test
    public void listenFireworkTest() throws InterruptedException {
        synchronized(f){
            //notifi
            of.reset();
            f.addObserver(of);
            f.joinObservers();
            of.isGood(0);
            f.joinObservers();
            f.joinObservers();
            of.isGood(1);

            //fire add
            of.reset();
            f.addFire(o1);
            f.joinObservers();
            of.isGood(1);
            f.addFire(o2);
            of.isGood(1);
            f.joinObservers();
            f.addFire(o3);
            f.joinObservers();
            of.isGood(2);
            f.addFire(o1);
            o1.setFireItem(new FireItem());
            f.joinObservers();
            of.isGood(3);

            f.removeFire(o3);
            f.removeFire(o2);
            f.joinObservers();
            of.isGood(4);

            //Table
            of.reset();
            f.removeTable(t1);
            f.addTable(t1);
            f.addTable(t2);
            of.isGood(0);
            f.joinObservers();
            of.isGood(1);

            t1.setId("T1");
            f.joinObservers();
            of.isGood(2);

            f.addTable(t2);
            f.joinObservers();
            of.isGood(3);

        }

    }
    private boolean connect(TableLine line, Fire o12) {
        return line.getFires().contains(o12) && o12.getLine()==line;
    }

    private boolean contains(Table t){
        return f.getTables().contains(t);
    }
    private boolean contains(Fire t){
        return f.getFires().contains(t);
    }

    private class Observer<T> implements ObserverSynchro<T> {
        int i =0;
        public void reset() {
             i=0;
        }
        public void isGood(int i){
            assert(this.i == i);
        }

        @Override
        public void onUpdate(T obj) {
            i++;
        }
    }
}
