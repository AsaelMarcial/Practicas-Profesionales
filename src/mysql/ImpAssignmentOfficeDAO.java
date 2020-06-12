package mysql;

import dao.AssignmentOfficeDAO;
import dao.DAOException;
import models.AssignmentOffice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpAssignmentOfficeDAO implements AssignmentOfficeDAO {
    final String INSERT = "INSERT INTO OficioAsignacion(creador, practicante, proyecto) VALUES(?, ?, ?)";
    final String UPDATE = "UPDATE OficioAsignacion SET creador = ?, practicante = ?, proyecto = ? WHERE idOficio = ?";
    final String DELETE = "DELETE * FROM OficioAsignacion WHERE idOficio = ?";
    final String GETALL = "SELECT * FROM OficioAsignacion";
    final String GETONE = "SELECT * FROM OficioAsignacion WHERE idOficio = ?";
    public Connection connection;

    public ImpAssignmentOfficeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(AssignmentOffice obj) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(INSERT);
            statement.setInt(1, obj.getCreator());
            statement.setInt(2, obj.getPracticing());
            statement.setInt(3, obj.getProject());
            if(statement.executeUpdate() == 0) {
                throw new DAOException("Errr al insertar en la base de datos!:");
            }
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                obj.setIdOffice(resultSet.getInt(1));
            } else {
                throw new DAOException("No se pudo asignar ID!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement, resultSet);
        }
    }

    @Override
    public void update(AssignmentOffice obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE);
            statement.setInt(1, obj.getCreator());
            statement.setInt(2, obj.getPracticing());
            statement.setInt(3, obj.getProject());
            if(statement.executeUpdate() == 0) {
                throw new DAOException("Es posible que no se hayn modificado los valores en la base de datos!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement);
        }
    }

    @Override
    public void delete(AssignmentOffice obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, obj.getIdOffice());
            if(statement.executeUpdate() == 0) {
                throw new DAOException("Posible error al eliminar en la base de datos!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement);
        }
    }

    private AssignmentOffice convert(ResultSet resultSet) throws SQLException {
        int creator = resultSet.getInt("creador");
        int practicing = resultSet.getInt("practicante");
        int project = resultSet.getInt("proyecto");
        AssignmentOffice assignmentOffice = new AssignmentOffice(creator, practicing, project);
        assignmentOffice.setIdOffice(resultSet.getInt("idOficio"));
        return assignmentOffice;
    }

    @Override
    public List<AssignmentOffice> getAll() throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<AssignmentOffice> assignmentOffice = new ArrayList<>();
        try {
            statement = connection.prepareStatement(GETALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                assignmentOffice.add(convert(resultSet));
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        }finally {
            utils.close(statement, resultSet);
        }
        return assignmentOffice;
    }

    @Override
    public AssignmentOffice geOne(Integer id) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        AssignmentOffice assignmentOffice;
        try {
            statement = connection.prepareStatement(GETONE);
            statement.setInt(1, id);
            if(resultSet.next()){
                assignmentOffice = convert(resultSet);
            } else {
                throw new DAOException("Registro no encontrado!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement, resultSet);
        }
        return assignmentOffice;
    }

}
