package mysql;

import dao.AdminDAO;
import dao.DAOException;
import models.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImpAdminDAO implements AdminDAO {
    final String INSERT = "INSERT INTO Usuario(contraseña, nombre, apellido, direccionCorreo, tipoUsuario) VALUES(?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE Usuario SET contraseña = ?, nombre = ?, apellido = ?, direccionCorreo = ?, tipoUsuario = ? WHERE idPersonal = ?";
    final String DELETE = "DELETE FROM Usuario WHERE idPersonal = ?";
    final String GETALL = "SELECT * FROM Usuario";
    final String GETONE = "SELECT * FROM Usuario WHERE idPersonal = ?";
    public Connection connection;

    public ImpAdminDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Admin obj) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(INSERT);
            statement.setString(1, obj.getPassword());
            statement.setString(2, obj.getName());
            statement.setString(3, obj.getLastName());
            statement.setString(4, obj.getEmail());
            statement.setInt(5, 1);
            if(statement.executeUpdate() == 0){
                throw new DAOException("Error al insertar en base de datos!.");
            }
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                obj.setIdPersonal(resultSet.getInt(1));
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
    public void update(Admin obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, obj.getPassword());
            statement.setString(2, obj.getName());
            statement.setString(3, obj.getLastName());
            statement.setString(4, obj.getEmail());
            statement.setInt(5, obj.getUserType());
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
    public void delete(Admin obj) throws DAOException {
        PreparedStatement statement = null;
        try {
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

    private Admin convert(ResultSet resultSet) throws SQLException {
        String password = resultSet.getString("contraseña");
        String name = resultSet.getString("nombre");
        String lastName = resultSet.getString("apellido");
        String email = resultSet.getString("direccionCorreo");
        Admin admin = new Admin(password, name, lastName, email);
        admin.setIdPersonal(resultSet.getInt("idPersonal"));
        return admin;
    }

    @Override
    public List<Admin> getAll() throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Admin> admins = new ArrayList<>();
        try{
            statement = connection.prepareStatement(GETALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                admins.add(convert(resultSet));
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement, resultSet);
        }
        return admins;
    }

    @Override
    public Admin geOne(Integer id) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Admin admn;
        try{
            statement = connection.prepareStatement(GETONE);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                admn = convert(resultSet);
            } else {
                throw new DAOException("Registro no encontrado!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement, resultSet);
        }
        return admn;
    }

    public static void main(String[] args) throws SQLException, DAOException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://185.201.10.129/u997594570_SPP","u997594570_threesome", "Megaman1;");
            AdminDAO dao = new ImpAdminDAO(conn);
                List<Admin> admins = dao.getAll();
                for(Admin a: admins){
                    System.out.println(a.toString());
                }
        } finally {
            if(conn != null){
                conn.close();
            }
        }
    }
}
