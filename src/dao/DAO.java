package dao;

import java.util.List;

public interface DAO<modelClass, dataType> {

    void insert(modelClass obj);

    void update(modelClass obj);

    void delete(modelClass obj);

    List<modelClass> getAll();

    modelClass geOne(dataType id);
}
