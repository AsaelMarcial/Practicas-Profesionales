package mysql;

import dao.DAOException;
import dao.RubricDAO;
import models.Rubric;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImpRubricDAO implements RubricDAO {
    final String INSERT = "INSERT INTO Rubrica(experienciaEducativa, archivo) VALUES(?, ?)";
    final String UPDATE = "UPDATE Rubrica SET experienciaEducativa, archivo WHERE idRubrica = ?";
    final String DELETE = "DELETE FROM Rubrica WHERE idRubrica = ?";
    final String GETALL = "SELECT * FROM Rubrica";
    final String GETONE = "SELECT * FROM Rubrica WHERE idRubrica = ?";
    public Connection connection;

    public ImpRubricDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Rubric obj) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(INSERT);
            statement.setInt(1,obj.getEducationalExperience());
            statement.setBlob(2, (Blob) obj.getFile());
            if(statement.executeUpdate() == 0){
                throw new DAOException("Error al insertar en base de datos!.");
            }
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                obj.setIdRubric(resultSet.getInt(1));
            } else {
                throw new DAOException("No se pudo asignar el ID!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL.", exception);
        } finally {
            utils.close(statement, resultSet);
        }
    }

    @Override
    public void update(Rubric obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE);
            statement.setInt(1,obj.getEducationalExperience());
            statement.setBlob(2, (Blob) obj.getFile());
            if(statement.executeUpdate() == 0){
                throw new DAOException("Es posible que no se hayan modificado los valores en la base de datos!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.",exception);
        } finally {
            utils.close(statement);
        }
    }

    @Override
    public void delete(Rubric obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, obj.getIdRubric());
            if(statement.executeUpdate() == 0){
                throw new DAOException("Posible error al eliminar en la base de datos!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement);
        }
    }

    private Rubric convert(ResultSet resultSet) throws SQLException {
        int educationalExperience = resultSet.getInt("experienciaEducativa");
        Rubric rubric = new Rubric(educationalExperience);
        rubric.setIdRubric(resultSet.getInt("idRubrica"));
        return rubric;
    }

    @Override
    public List<Rubric> getAll() throws DAOException {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            List<Rubric> rubrics = new ArrayList<>();
            try{
                statement = connection.prepareStatement(GETALL);
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    rubrics.add(convert(resultSet));
                }
            } catch (SQLException exception) {
                throw new DAOException("Error en SQL!.", exception);
            } finally {
                utils.close(statement, resultSet);
            }
            return rubrics;
    }

    @Override
    public Rubric geOne(Integer id) throws DAOException {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            Rubric rubric;
            try{
                statement = connection.prepareStatement(GETONE);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    rubric = convert(resultSet);
                } else {
                    throw new DAOException("Registro no encontrado!.");
                }
            } catch (SQLException exception) {
                throw new DAOException("Error en SQL!.", exception);
            } finally {
                utils.close(statement, resultSet);
            }
            return rubric;
    }
}
