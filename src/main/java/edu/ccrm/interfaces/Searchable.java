package edu.ccrm.interfaces;

import java.util.List;
import java.util.Optional;

//custom interface for searching
public interface Searchable<T, ID>{
    Optional<T> findById(ID id);
    List<T> findAll();
}