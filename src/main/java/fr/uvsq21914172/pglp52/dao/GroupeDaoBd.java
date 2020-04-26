package fr.uvsq21914172.pglp52.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.uvsq21914172.pglp52.Groupe;

/**
 * DAO bd de la classe groupe.
 * 
 * @author Dalil
 */
public class GroupeDaoBd extends Dao<Groupe> {
  private Connection co;


  /**
   * Constructeur
   */
  public GroupeDaoBd() {
    String dbUrl = "jdbc:derby:D:BDTEST;create=true";
    try {
      co = DriverManager.getConnection(dbUrl);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Insertion
   * 
   * @param grp Groupe.
   * @param co Connection a la bd.
   * @throws SQLException Erreur bd.
   */
  static void insertGroupe(final Groupe grp, final Connection co) throws SQLException {
    PreparedStatement prepare = co.prepareStatement("INSERT INTO GROUPE VALUES (?)");
    prepare.setString(1, grp.getName());
    prepare.execute();

    PreparedStatement contient = co.prepareStatement("INSERT INTO CONTIENT VALUES (?, ?)");
    PreparedStatement compose = co.prepareStatement("INSERT INTO COMPOSE VALUES (?, ?)");
    contient.setString(2, grp.getName());
    compose.setString(1, grp.getName());

    for (int i = 0; i < grp.getSousgroupes().size(); i++) {
      insertGroupe(grp.getSousgroupes().get(i), co);
      compose.setString(2, grp.getSousgroupes().get(i).getName());
      compose.execute();
    }

    for (int i = 0; i < grp.getMembres().size(); i++) {
      PersonnelDaoBd.insertPersonnel(grp.getMembres().get(i), co);
      contient.setString(1, grp.getMembres().get(i).getName());
      contient.execute();
    }

  }

  static Groupe searchGroupe(String name, Connection co) throws SQLException {
    PreparedStatement grpBd = co.prepareStatement("SELECT * FROM GROUPE WHERE name = ?");
    PreparedStatement persBd;
    grpBd.setString(1, name);
    ResultSet rs1 = grpBd.executeQuery();
    Groupe grp = null;
    if (rs1.first()) {
      grp = new Groupe(name);
      persBd = co.prepareStatement("SELECT * FROM CONTIENT WHERE nameGroupe = ?");
      persBd.setString(1, name);
      ResultSet rs2 = persBd.executeQuery();
      if (rs2.first()) {
        while (!rs2.isAfterLast()) {
          grp.add(PersonnelDaoBd.searchPersonnel(rs2.getString("namePersonnel"), co));
          rs2.next();
        }
      }
      persBd = co.prepareStatement("SELECT * FROM COMPOSE WHERE nameGroupe = ?");
      persBd.setString(1, name);
      rs2 = persBd.executeQuery();
      if (rs2.first()) {
        while (!rs2.isAfterLast()) {
          grp.add(searchGroupe(rs2.getString("nameGroupe"), co));
          rs2.next();
        }
      }
    }

    return grp;
  }

  static void removeGroupe(String name, Connection co) throws SQLException {
    PreparedStatement grpBd = co.prepareStatement("REMOVE * FROM GROUPE WHERE name = ?");
    grpBd.setString(1, name);
    grpBd.executeQuery();
    grpBd = co.prepareStatement("REMOVE * FROM CONTIENT WHERE nameGroupe = ?");
    grpBd.setString(1, name);
    grpBd.executeQuery();
    grpBd = co.prepareStatement("REMOVE * FROM COMPOSE WHERE nameGroupe = ?");
    grpBd.setString(1, name);
    grpBd.executeQuery();
  }

  /**
   *
   */
  @Override
  public Groupe create(Groupe grp) {
    try {
      FabriqueDaoBd.createTables(co);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    try {
      co.setAutoCommit(false);
      insertGroupe(grp, co);
      co.commit();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      try {
        co.rollback();
      } catch (SQLException e1) {
        System.err.println(e1.getMessage());
      }
    } finally {
      try {
        co.setAutoCommit(true);
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }

    return grp;
  }

  @Override
  public Groupe find(String name) {
    try {
      FabriqueDaoBd.createTables(co);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    try {
      return searchGroupe(name, co);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Groupe update(Groupe grp) {
    try {
      FabriqueDaoBd.createTables(co);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    try {
      removeGroupe(grp.getName(), co);
      insertGroupe(grp, co);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
    return grp;
  }

  @Override
  public void delete(Groupe grp) {
    try {
      FabriqueDaoBd.createTables(co);
    } catch (SQLException e) {
      e.printStackTrace();
      return;
    }
    try {
      removeGroupe(grp.getName(), co);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
