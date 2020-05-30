package mysql;

import dao.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class utils {

    static void close(PreparedStatement statement) throws DAOException {
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException exception) {
                throw new DAOException("Error al cerrar conexión con SQL", exception);
            }
        }
    }

    static void close(PreparedStatement statement, ResultSet resultSet) throws DAOException {
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException exception) {
                throw new DAOException("Error al cerrar conexión con SQL", exception);
            }
        }
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException exception) {
                throw new DAOException("Error al cerrar conexión con SQL", exception);
            }
        }
    }
}
