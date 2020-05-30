package dao;

import java.util.List;

public interface DAO<modelClass, dataType> {

    void insert(modelClass obj) throws DAOException;

    void update(modelClass obj) throws DAOException;

    void delete(modelClass obj) throws DAOException;

    List<modelClass> getAll() throws DAOException;

    modelClass geOne(dataType id) throws DAOException;
}
