package test;

import dao.AdminDAO;
import dao.DAOException;
import models.Admin;
import mysql.ImpAdminDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ImpAdminDAOTest {

    @Test
    void insert() {
        Connection conn = null;
        Admin admin = admin = new Admin("contraseñaPrueba", "NombrePrueba", "apellidoPrueba", "correoPrueba");
        AdminDAO adminDAO = adminDAO = new ImpAdminDAO(conn);
        try {
            adminDAO.insert(admin);
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }

    @Test
    void geOne() {
    }

}
