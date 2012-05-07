package edu.uci.ics.crawler4j.crawler;

import java.util.concurrent.TimeUnit;

import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

public class IdleConnectionMonitorThread extends Thread {
    
    private final ThreadSafeClientConnManager connMgr;
    private volatile boolean shutdown;
    
    public IdleConnectionMonitorThread(ThreadSafeClientConnManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(15000);
                    // Close expired connections
                    connMgr.closeExpiredConnections();
                    // Optionally, close connections
                    // that have been idle longer than 30 sec
                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException ex) {
            // terminate
        }
    }
    
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
    
}

