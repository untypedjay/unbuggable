package io.untypedjay.unbuggable.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T, ID extends Serializable> {
  T findById(ID id);
  List<T> findAll();
  void insert(T entity);
  T merge(T entity);
}
