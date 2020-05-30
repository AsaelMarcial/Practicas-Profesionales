package mysql;

import dao.DAOException;
import dao.ProfessorDAO;
import models.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpProfessorDAO implements ProfessorDAO {
    final String INSERT = "INSERT INTO Usuario(contraseña, nombre, apellido, direccionCorreo, tipoUsuario) VALUES(?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE Usuario SET contraseña = ?, nombre = ?, apellido =?, direccionCorreo = ?, tipoUsuario =? WHERE idPersonal = ?";
    final String DELETE = "DELETE FROM Usuario WHERE idPersonal = ?";
    final String GETALL = "SELECT * FROM Usuario";
    final String GETONE = "SELECT * FROM Usuario WHERE idPersonal = ?";
    public Connection connection;

    public ImpProfessorDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Professor obj) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            statement = connection.prepareStatement(INSERT);
            statement.setString(1, obj.getPassword());
            statement.setString(2, obj.getName());
            statement.setString(3, obj.getLastName());
            statement.setString(4, obj.getEmail());
            statement.setInt(5, 3);
            if (statement.executeUpdate() == 0) {
                throw new DAOException("Error al insertar en base de datos!.");
            }
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                obj.setIdPersonal(resultSet.getInt(1));
            } else {
                throw new DAOException("No se pudo asignar el ID!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement, resultSet);
        }
    }

    @Override
    public void update(Professor obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, obj.getPassword());
            statement.setString(2, obj.getName());
            statement.setString(3, obj.getLastName());
            statement.setString(4, obj.getEmail());
            statement.setInt(5, obj.getIdPersonal());
            if (statement.executeUpdate() == 0) {
                throw new DAOException("Es posibler que no se hayan modificado los valores en la base de datos!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement);
        }
    }

    @Override
    public void delete(Professor obj) throws DAOException {
        PreparedStatement statement = null;
        try {
          statement = connection.prepareStatement(DELETE);
          statement.setInt(1, obj.getIdPersonal());
          if (statement.executeUpdate() == 0) {
              throw new DAOException("Posible error al eliminar en la base de datos!.");
          }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement);
        }
    }

    public Professor convert (ResultSet resultSet) throws SQLException {
        String password = resultSet.getString("contraseña");
        String name = resultSet.getString("nombre");
        String lastName = resultSet.getString("apellido");
        String email = resultSet.getString("direccionCorreo");
        Professor professor = new Professor(password, name, lastName, email);
        professor.setIdPersonal(resultSet.getInt("idPersonal"));
        return professor;
    }

    @Override
    public List<Professor> getAll() throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Professor> professors = new ArrayList<>();
        try {
            statement = connection.prepareStatement(GETALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                professors.add(convert(resultSet));
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement, resultSet);
        }
        return professors;
    }

    @Override
    public Professor geOne(Integer id) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Professor prof;
        try{
            statement = connection.prepareStatement(GETONE);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                prof = convert(resultSet);
            } else {
                throw new DAOException("Registro no encontrado!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement, resultSet);
        }
        return prof;
    }
}
