package mysql;

import dao.DAOException;
import dao.ReportDAO;
import models.Project;
import models.Report;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImpReportDAO implements ReportDAO {
    final String INSERT = "INSERT INTO Reporte(autor, tipoReporte, fechaEntrega, calificacion, reporte) VALUES(?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE Reporte SET autor = ?, tipoReporte = ?, fechaEntrega = ?, calificacion = ?, reporte = ? WHERE idReporte = ?";
    final String DELETE = "DELETE FROM Reporte WHERE idReporte = ?";
    final String GETALL = "SELECT * FROM Reporte";
    final String GETONE = "SELECT * FROM Reporte WHERE idReporte = ?";
    public Connection connection;

    public ImpReportDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Report obj) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(INSERT);
            statement.setInt(1, obj.getAuthor());
            statement.setString(2, obj.getReportType());
            statement.setDate(3, (Date) obj.getUploadDate());
            statement.setInt(4, obj.getScore());
            statement.setBlob(5, (Blob) obj.getReport());
            if(statement.executeUpdate() == 0){
                throw new DAOException("Error al insertar en base de datos!.");
            }
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                obj.setIdReport(resultSet.getInt(1));
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
    public void update(Report obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE);
            statement.setInt(1, obj.getAuthor());
            statement.setString(2, obj.getReportType());
            statement.setDate(3, (Date) obj.getUploadDate());
            statement.setInt(4, obj.getScore());
            statement.setBlob(5, (Blob) obj.getReport());
            if(statement.executeUpdate() == 0){
                throw new DAOException("Es posible que no se hayan modificado los valores en la base de datos!.");
            }
        } catch (SQLException exception) {
            throw new DAOException("Error en SQL!.",exception);
        } finally {
            utils.close(statement);
        }
    }

    private Report convert(ResultSet resultSet) throws SQLException {
        int author = resultSet.getInt("autor");
        String reportType = resultSet.getString("tipoReporte");
        Date uploadDate = resultSet.getDate("fechaEntrega");
        Report report = new Report(author, reportType, uploadDate);
        report.setIdReport(resultSet.getInt("idReporte"));
        return report;
    }

    @Override
    public void delete(Report obj) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, obj.getIdReport());
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
    public List<Report> getAll() throws DAOException {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            List<Report> reports = new ArrayList<>();
            try{
                statement = connection.prepareStatement(GETALL);
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    reports.add(convert(resultSet));
                }
            } catch (SQLException exception) {
                throw new DAOException("Error en SQL!.", exception);
            } finally {
                utils.close(statement, resultSet);
            }
            return reports;
    }

    @Override
    public Report geOne(Integer id) throws DAOException {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            Report report;
            try{
                statement = connection.prepareStatement(GETONE);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    report = convert(resultSet);
                } else {
                    throw new DAOException("Registro no encontrado!.");
                }
            } catch (SQLException exception) {
                throw new DAOException("Error en SQL!.", exception);
            } finally {
                utils.close(statement, resultSet);
            }
            return report;
    }
}
