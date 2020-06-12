package mysql;

import dao.DAOException;
import dao.PracticingDAO;
import models.Practicing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpPracticingDAO implements PracticingDAO {
    final String INSERT = "INSERT INTO Usuario(contraseña, nombre, apellido, direccion correo, tipoUsuario) VALUES(?, ?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE Usuario SET contraseña = ?, nombre = ?, apellido = ?, direccionCorreo = ?, tipoUsuario = ? WHERE idPersonal = ?";
    final String DELETE = "DELETE FROM Usuario WHERE idPersonal = ?";
    final String GETALL = "SELECT * FROM Usuario";
    final String GETONE = "SELECT * FROM Usuario WHERE idPersonal = ?";
    public Connection connection;

    public ImpPracticingDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Practicing obj) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            statement = connection.prepareStatement(INSERT);
            statement.setString(1, obj.getPassword());
            statement.setString(2, obj.getName());
            statement.setString(3, obj.getLastName());
            statement.setString(4, obj.getEmail());
            statement.setInt(5, 4);
            if (statement.executeUpdate() == 0){
                throw new DAOException("Error al insertar en la base de datos!.");
            }
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                obj.setIdPersonal(resultSet.getInt(1));
            } else {
                throw new DAOException("No se pudo asignar el ID!:");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL.", exception);
        } finally {
            utils.close(statement, resultSet);
        }
    }

    @Override
    public void update(Practicing obj) throws DAOException {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, obj.getPassword());
            statement.setString(2, obj.getName());
            statement.setString(1, obj.getLastName());
            statement.setString(4, obj.getEmail());
            statement.setInt(5, obj.getUserType());
            if(statement.executeUpdate() == 0) {
                throw new DAOException("Es posible que no se hayan modificado los valores en la base de datos!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        }finally {
            utils.close(statement);
        }
    }

    @Override
    public void delete(Practicing obj) throws DAOException {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, obj.getIdPersonal());
            if(statement.executeUpdate() == 0){
                throw new DAOException("Posible error al eliminar en la base de datos!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement);
        }
    }

    public Practicing convert(ResultSet resultSet) throws SQLException {
        String password = resultSet.getString("contraseña");
        String name = resultSet.getString("nombre");
        String lastName = resultSet.getString("apellido");
        String email = resultSet.getString("direccionCorreo");
        Practicing pract = new Practicing(password, name, lastName, email);
        pract.setIdPersonal(resultSet.getInt("idPersonal"));
        return pract;
    }

    @Override
    public List<Practicing> getAll() throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Practicing> practicings = new ArrayList<>();
        try{
            statement = connection.prepareStatement(GETALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                practicings.add(convert(resultSet));
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement, resultSet);
        }
        return practicings;
    }

    @Override
    public Practicing geOne(Integer id) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Practicing pract;
        try{
            statement = connection.prepareStatement(GETONE);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                pract = convert(resultSet);
            } else {
                throw new DAOException("Registro no encontrado!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement, resultSet);
        }
        return pract;
    }
}
