package mysql;

/*import dao.DAOException;
import dao.MailboxDAO;
import models.Mailbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpMailboxDAO implements MailboxDAO {
    final String INSERT = "INSERT INTO Buzon() VALUES(?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE Buzon SET ? WHERE idPersonal = ?";
    final String DELETE = "DELETE FROM Buzon WHERE idPersonal = ?";
    final String GETALL = "SELECT * FROM Buzon";
    final String GETONE = "SELECT * FROM Buzon WHERE idPersonal = ?";
    public Connection connection;

    public ImpMailboxDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Mailbox obj) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(INSERT);
            statement.setString();
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
    public void update(Mailbox obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE);
            statement.setString();
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
    public void delete(Mailbox obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE);
            statement.setInt();
            if(statement.executeUpdate() == 0){
                throw new DAOException("Posible error al eliminar en la base de datos!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement);
        }
    }

    @Override
    public List<Mailbox> getAll() throws DAOException {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            List<Mailbox> mailboxes = new ArrayList<>();
            try{
                statement = connection.prepareStatement(GETALL);
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    mailboxes.add(convert(resultSet));
                }
            } catch (SQLException exception) {
                throw new DAOException("Error en SQL!.", exception);
            } finally {
                utils.close(statement, resultSet);
            }
            return mailboxes;
    }

    @Override
    public Mailbox geOne(Integer id) throws DAOException {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            Mailbox mailbox;
            try{
                statement = connection.prepareStatement(GETONE);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    mailbox = convert(resultSet);
                } else {
                    throw new DAOException("Registro no encontrado!.");
                }
            } catch (SQLException exception) {
                throw new DAOException("Error en SQL!.", exception);
            } finally {
                utils.close(statement, resultSet);
            }
            return mailbox;
    }
}
*/