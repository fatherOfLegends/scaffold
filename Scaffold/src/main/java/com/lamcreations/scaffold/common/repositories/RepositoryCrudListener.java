package com.lamcreations.scaffold.common.repositories;


public interface RepositoryCrudListener<T> {

    void onItemCreated(T item);

    void onItemRetrieved(T item);

    void onItemUpdated(T item);

    void onItemDeleted(T item);
}
