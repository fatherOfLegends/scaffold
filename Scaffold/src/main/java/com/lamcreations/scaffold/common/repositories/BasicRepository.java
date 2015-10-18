package com.lamcreations.scaffold.common.repositories;


import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BasicRepository<T> {

    public enum State {
        IDLE,
        LOADING,
        ERROR
    }

    protected final Set<RepositoryCrudListener<T>> mRepositoryCrudListeners =
            Collections.synchronizedSet(
                    Collections.newSetFromMap(
                            new ConcurrentHashMap<RepositoryCrudListener<T>, Boolean>()));

    protected final Set<RepositoryStateListener> mRepositoryStateListeners =
            Collections.synchronizedSet(
                    Collections.newSetFromMap(
                            new ConcurrentHashMap<RepositoryStateListener, Boolean>()));

    public interface RepositoryStateListener {

        void onRepositoryStateChange(State newState);
    }

    protected BasicRepository (){

    }

    public void addRepositoryCrudListener(RepositoryCrudListener<T> listener){
        mRepositoryCrudListeners.add(listener);
    }

    public void removeRepositoryCrudListener(RepositoryCrudListener<T> listener){
        mRepositoryCrudListeners.remove(listener);
    }

    public void addRepositoryStateListener(RepositoryStateListener listener){
        mRepositoryStateListeners.add(listener);
    }

    public void removeRepositoryStateListener(RepositoryStateListener listener){
        mRepositoryStateListeners.remove(listener);
    }

    protected void notifyItemCreated(T item){
        synchronized (mRepositoryCrudListeners){
            for (RepositoryCrudListener<T> listener : mRepositoryCrudListeners) {
                listener.onItemCreated(item);
            }
        }
    }

    protected void notifyItemRetrieved(T item){
        synchronized (mRepositoryCrudListeners){
            for (RepositoryCrudListener<T> listener : mRepositoryCrudListeners) {
                listener.onItemRetrieved(item);
            }
        }
    }

    protected void notifyItemUpdated(T item){
        synchronized (mRepositoryCrudListeners){
            for (RepositoryCrudListener<T> listener : mRepositoryCrudListeners) {
                listener.onItemUpdated(item);
            }
        }
    }

    protected void notifyItemDeleted(T item){
        synchronized (mRepositoryCrudListeners){
            for (RepositoryCrudListener<T> listener : mRepositoryCrudListeners) {
                listener.onItemDeleted(item);
            }
        }
    }

    protected void notifyRepositoryStateChanged(State newState){
        synchronized (mRepositoryStateListeners){
            for (RepositoryStateListener listener : mRepositoryStateListeners) {
                listener.onRepositoryStateChange(newState);
            }
        }
    }
}
