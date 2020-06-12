package mysql;

import dao.DAOException;
import dao.EducationalExperienceDAO;
import models.EducationalExperience;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImpEducationalExperienceDAO implements EducationalExperienceDAO {
    final String INSERT = "INSERT INTO ExperienciaEducativa(nombreExperienciaEducativa, profesor, listaPracticantes) VALUES(?, ?, ?)";
    final String UPDATE = "UPDATE ExperienciaEducativa SET nombreExperienciaEducativa = ?, profesor = ?, idGrupoAsignado = ? WHERE NRC = ?";
    final String DELETE = "DELETE FROM ExperienciaEducativa WHERE NRC = ?";
    final String GETALL = "SELECT * FROM ExperienciaEducativa";
    final String GETONE = "SELECT * FROM ExperienciaEducativa WHERE NRC = ?";
    public Connection connection;

    public ImpEducationalExperienceDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(EducationalExperience obj) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(INSERT);
            statement.setString(1, obj.getName());
            statement.setInt(2, obj.getProfessor());
            statement.setBlob(3, (Blob) obj.getEnlistedPractitioners());
            if(statement.executeUpdate() == 0){
                throw new DAOException("Error al insertar en base de datos!.");
            }
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                obj.setNRC(resultSet.getInt(1));
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
    public void update(EducationalExperience obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, obj.getName());
            statement.setInt(2, obj.getProfessor());
            statement.setBlob(3, (Blob) obj.getEnlistedPractitioners());
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
    public void delete(EducationalExperience obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, obj.getNRC());
            if(statement.executeUpdate() == 0){
                throw new DAOException("Posible error al eliminar en la base de datos!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement);
        }
    }

    private EducationalExperience convert(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("nombreExperienciaEducativa");
        int professor = resultSet.getInt("profesor");
        File list = (File) resultSet.getBlob("listaPracticantes");
        EducationalExperience educationalExperience = new EducationalExperience(name, professor, list);
        educationalExperience.setNRC(resultSet.getInt("NRC"));
        return educationalExperience;
    }

    @Override
    public List<EducationalExperience> getAll() throws DAOException {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            List<EducationalExperience> educationalExperiences = new ArrayList<>();
            try{
                statement = connection.prepareStatement(GETALL);
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    educationalExperiences.add(convert(resultSet));
                }
            } catch (SQLException exception) {
                throw new DAOException("Error en SQL!.", exception);
            } finally {
                utils.close(statement, resultSet);
            }
            return educationalExperiences;
    }

    @Override
    public EducationalExperience geOne(Integer id) throws DAOException {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            EducationalExperience educationalExperience;
            try{
                statement = connection.prepareStatement(GETONE);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    educationalExperience = convert(resultSet);
                } else {
                    throw new DAOException("Registro no encontrado!.");
                }
            } catch (SQLException exception) {
                throw new DAOException("Error en SQL!.", exception);
            } finally {
                utils.close(statement, resultSet);
            }
            return educationalExperience;
    }
}
