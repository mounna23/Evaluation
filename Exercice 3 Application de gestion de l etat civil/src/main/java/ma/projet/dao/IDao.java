package ma.projet.dao;

import java.util.List;

public interface IDao<T> {
    T create(T o);
    T update(T o);
    boolean delete(T o);
    List<T> findAll();
    T findById(int id);
}