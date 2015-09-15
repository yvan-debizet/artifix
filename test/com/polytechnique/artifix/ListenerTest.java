package com.polytechnique.artifix;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.polytechnique.artifix.listener.ObservableSynchro;
import com.polytechnique.artifix.listener.ObserverSynchro;

public class ListenerTest {
    private final static int NB_OBSERVER = 1000;
    private static final int NB_NOTIFY = 40;
    private static final long TIME = 40;
    private static final int TIME_LARGE = 200;

    private Observable obs;
    private List<Observer> array;
    private long t;

    @Before
    public void init() {
        obs = new Observable();
        array = listen(NB_OBSERVER, obs);
        clear(array);
    }


    @Test
    public void addObserver() {
        for (Observer ob : array) {
            assertFalse("PROBLEME", ob.isNotify);
        }
        obs.set();
        sleep(TIME_LARGE);

        for (Observer ob : array) {
            assertTrue("non notification d'un element", ob.isNotify);
        }
    }

    @Test
    public void removeObserver() {
        Observer rm = array.remove(0);
        obs.removeObserver(rm);

        for (int i = 0; i < NB_NOTIFY; i++) {
            obs.set();
        }
        sleep(TIME_LARGE);
        for (Observer ob : array) {
            assertTrue("notification manquante", ob.isNotify);
        }
        assertFalse("remove inutile",rm.isNotify);
    }


    @Test
    public void notifyObservers() {
        obs.set();
        sleep(TIME);
        for (Observer ob : array) {
            assertTrue("vitesse de notification trop lent", ob.isNotify);
        }

        /*
        clear(array);
        obs.set();
        while (t + TIME < System.currentTimeMillis());
        t = System.currentTimeMillis();

        for (Observer ob : array) {
            assertTrue("notification bloqué par un autre processus", ob.isNotify);
        }
        */
        clear(array);
        for (int i = 0; i < NB_NOTIFY; i++) {
            obs.set();
        }
        sleep(TIME_LARGE);
        for (Observer ob : array) {
            assertEquals("notification au mauvais moment", ob.nbOther, 0);
        }

    }

    @Test
    public void joinObservers() throws InterruptedException {
        t = System.currentTimeMillis();
        for (int i = 0; i < NB_NOTIFY; i++) {
            obs.notifyObservers();
            obs.joinObservers();
        }
        assertTrue("pas suffisament rapide", System.currentTimeMillis() - t < NB_NOTIFY * TIME);
        for (Observer ob : array) {
            assertEquals("notification not join", ob.nb0, NB_NOTIFY);
        }

        clear(array);
        for (int i = 0; i < NB_NOTIFY; i++) {
            synchronized(obs) {
                obs.notifyObservers();
                obs.notifyObservers();
                obs.notifyObservers();

                obs.joinObservers();
            }
        }
        for (Observer ob : array) {
            assertEquals("notification not synchronized", ob.nb0, NB_NOTIFY);
        }


    }



    private void sleep(long time2) {
        try {
            Thread.sleep(time2);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }



    private void clear(List<Observer> array) {
        for (Observer o : array) {
            o.nb0=0;
            o.nbOther=0;
            o.isNotify=false;
        }
    }



    private List<Observer> listen(int nb, Observable obs) {
        ArrayList<Observer> array = new ArrayList<>();
        for (int i = 0; i < nb; i++) {
            Observer o = new Observer();
            array.add(o);
            obs.addObserver(o);
        }
        return array;
    }




    private class Observable extends ObservableSynchro<Observable> {
        public int i=0;

        public void set() {
            synchronized(this) {
                i++;
                notifyObservers();
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                i--;
                notifyObservers();
            }
        }

        @Override
        public Observable getObservable() {
            return this;
        }

    }
    private class Observer implements ObserverSynchro<Observable>  {
        public int nb0 = 0, nbOther = 0;
        public boolean isNotify = false;

        @Override
        public void onUpdate(Observable obj) {
            isNotify=true;
            if(obj.i == 0) {
                nb0++;
            }
            else {
                nbOther++;
            }
        }
    }
}
