package fr.uvsq21914172.pglp52.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import fr.uvsq21914172.pglp52.Groupe;
import fr.uvsq21914172.pglp52.Personnel;

public class FabriqueDaoBd implements AbstractFabriqueDao {

  /**
   * Renvoie un objet Personnel DAO.
   * 
   * @return DAO.
   */
  public Dao<Personnel> getPersonnelDao() {
    return new PersonnelDaoBd();
  }

  /**
   * Renvoie un objet Groupe DAO.
   * 
   * @return DAO.
   */
  public Dao<Groupe> getGroupeDao() {
    return new GroupeDaoBd();
  }

  /**
   * Verifie si un table existe.
   * 
   * @param co Connection a la BD.
   * @param table Table.
   * @return Si la table existe.
   * @throws SQLException
   */
  private static boolean existable(Connection co, String table) throws SQLException {
    DatabaseMetaData dbmd = co.getMetaData();
    ResultSet rs = dbmd.getTables(null, null, table, null);
    if (rs.next()) {
      return true;
    }
    return false;
  }

  /**
   * Cree toute les tables.
   * 
   * @param co Connection a la BD.
   * @throws SQLException Erreur bd.
   */
  public static void createTables(Connection co) throws SQLException {
    Statement stmt = co.createStatement();
    if (!existable(co, "GROUPE")) {
      stmt.executeUpdate("CREATE TABLE GROUPE (name VARCHAR(50) PRIMARY KEY)");
    }

    if (!existable(co, "CONTIENT")) {
      stmt.executeUpdate("CREATE TABLE CONTIENT (namePersonnel VARCHAR(50) NOT NULL,"
          + "nameGroupe VARCHAR(50) NOT NULL,PRIMARY KEY (namePersonnel, nameGroupe), "
          + "FOREIGN KEY (namePersonnel) REFERENCES PERSONNEL(name), "
          + "FOREIGN KEY (nameGroupe) REFERENCES GROUPE(name))");
    }

    if (!existable(co, "COMPOSE")) {
      stmt.executeUpdate("CREATE TABLE COMPOSE (nameGroupe VARCHAR(50) NOT NULL, "
          + "nameGroupeIn VARCHAR(50) NOT NULL, PRIMARY KEY (nameGroupe,"
          + " nameGroupeIn), FOREIGN KEY (nameGroupe)"
          + " REFERENCES GROUPE(name), FOREIGN KEY (nameGroupeIn)" + " REFERENCES GROUPE(name))");
    }
    if (!existable(co, "PERSONNEL")) {
      stmt.executeUpdate("CREATE TABLE PERSONNEL (name VARCHAR(50) PRIMARY KEY, "
          + "firstName VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL,"
          + " birthDay DATE NOT NULL)");
    }
    if (!existable(co, "TELEPHONE")) {
      stmt.executeUpdate("CREATE TABLE TELEPHONE (namePersonnel VARCHAR(50) NOT NULL, "
          + "extension VARCHAR(5) NOT NULL, telephone VARCHAR(12) NOT NULL,"
          + " PRIMARY KEY (namePersonnel, extension, telephone),"
          + " FOREIGN KEY (namePersonnel) REFERENCES PERSONNEL(name))");
    }
    if (!existable(co, "TITLE")) {
      stmt.executeUpdate("CREATE TABLE TITLE (namePersonnel VARCHAR(50) NOT NULL, "
          + "title VARCHAR(50) NOT NULL, PRIMARY KEY (namePersonnel, title),"
          + " FOREIGN KEY (namePersonnel) REFERENCES PERSONNEL(name))");
    }
  }
}
