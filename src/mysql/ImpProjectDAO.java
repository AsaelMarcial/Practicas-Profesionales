package mysql;

import dao.DAOException;
import dao.ProjectDAO;
import models.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpProjectDAO implements ProjectDAO {
    final String INSERT = "INSERT INTO Proyecto(nombreProyecto, organizacionVinculada, dominio, descripcion, informacionProyecto) VALUES(?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE Proyecto SET nombreProyecto = ?, organizacionVinculada = ?, dominio = ?, descripcion = ?, informacionProyecto = ? WHERE idProyecto = ?";
    final String DELETE = "DELETE FROM Proyecto WHERE idProyecto = ?";
    final String GETALL = "SELECT * FROM Proyecto";
    final String GETONE = "SELECT * FROM Proyecto WHERE idProyecto = ?";
    public Connection connection;

    public ImpProjectDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Project obj) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(INSERT);
            statement.setString(1, obj.getProjectName());
            statement.setString(2, obj.getLinkedOrganization());
            statement.setString(3, obj.getDomain());
            statement.setString(4, obj.getDescription());
            statement.setInt(5, obj.getProjectInfo());
            if(statement.executeUpdate() == 0){
                throw new DAOException("Error al insertar en base de datos!.");
            }
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                obj.setIdProject(resultSet.getInt(1));
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
    public void update(Project obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, obj.getProjectName());
            statement.setString(2, obj.getLinkedOrganization());
            statement.setString(3, obj.getDomain());
            statement.setString(4, obj.getDescription());
            statement.setInt(5, obj.getProjectInfo());
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
    public void delete(Project obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, obj.getIdProject());
            if(statement.executeUpdate() == 0){
                throw new DAOException("Posible error al eliminar en la base de datos!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.", exception);
        } finally {
            utils.close(statement);
        }
    }

    private Project convert(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("nombreProyecto");
        String linkedOrg = resultSet.getString("organizacionVinculada");
        String domain = resultSet.getString("dominio");
        String description = resultSet.getString("descripcion");
        int projectInfo = resultSet.getInt("informacionProyecto");
        Project project = new Project(name, linkedOrg, domain, description, projectInfo);
        project.setIdProject(resultSet.getInt("idProyecto"));
        return project;
    }

    @Override
    public List<Project> getAll() throws DAOException {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            List<Project> projects = new ArrayList<>();
            try{
                statement = connection.prepareStatement(GETALL);
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    projects.add(convert(resultSet));
                }
            } catch (SQLException exception) {
                throw new DAOException("Error en SQL!.", exception);
            } finally {
                utils.close(statement, resultSet);
            }
            return projects;
    }

    @Override
    public Project geOne(Integer id) throws DAOException {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            Project project;
            try{
                statement = connection.prepareStatement(GETONE);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    project = convert(resultSet);
                } else {
                    throw new DAOException("Registro no encontrado!.");
                }
            } catch (SQLException exception) {
                throw new DAOException("Error en SQL!.", exception);
            } finally {
                utils.close(statement, resultSet);
            }
            return project;
    }
}
