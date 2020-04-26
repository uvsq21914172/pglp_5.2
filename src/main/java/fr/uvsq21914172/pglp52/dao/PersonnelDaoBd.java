package fr.uvsq21914172.pglp52.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import fr.uvsq21914172.pglp52.Personnel;
import fr.uvsq21914172.pglp52.Telephone;

public class PersonnelDaoBd extends Dao<Personnel> {
  private Connection co;

  public PersonnelDaoBd() {
    String dbUrl = "jdbc:derby:D:BDTEST;create=true";
    try {
      co = DriverManager.getConnection(dbUrl);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void insertPersonnel(Personnel personnel, Connection co) throws SQLException {
    PreparedStatement prepare = co.prepareStatement("INSERT INTO GROUPE VALUES (?, ?, ?, ?)");
    prepare.setString(1, personnel.getName());
    prepare.setString(1, personnel.getNom());
    prepare.setString(1, personnel.getPrenom());
    prepare.setString(1, personnel.getDateDeNaissance().toString());
    prepare.execute();

    PreparedStatement numeros = co.prepareStatement("INSERT INTO TELEPHONE VALUES (?, ?, ?)");
    PreparedStatement titres = co.prepareStatement("INSERT INTO TITLE VALUES (?, ?)");
    numeros.setString(1, personnel.getName());
    titres.setString(1, personnel.getName());

    for (int i = 0; i < personnel.getTitres().size(); i++) {
      titres.setString(2, personnel.getTitres().get(i));
      titres.execute();
    }

    for (int i = 0; i < personnel.getNumbers().size(); i++) {
      numeros.setString(2, personnel.getNumbers().get(i).getExtension());
      numeros.setString(3, personnel.getNumbers().get(i).getNum());
      numeros.execute();
    }
  }

  public static Personnel searchPersonnel(String name, Connection co) throws SQLException {
    PreparedStatement prepare = co.prepareStatement("SELECT * FROM PERSONNEL WHERE name = ?");
    prepare.setString(1, name);
    ResultSet rs = prepare.executeQuery();

    Personnel.Builder pers = null;

    PreparedStatement numeros = co.prepareStatement("SELECT * FROM TELEPHONE WHERE name = ?");
    PreparedStatement titres = co.prepareStatement("SELECT * FROM TITLE WHERE name = ?");
    numeros.setString(1, name);
    titres.setString(1, name);

    if (rs.first()) {
      pers = new Personnel.Builder(rs.getString("lastName"), rs.getString("firstName"),
          LocalDate.parse(rs.getString("birthDay")));
      rs = numeros.executeQuery();
      if (rs.first()) {
        while (!rs.isAfterLast())
          pers.addNumber(new Telephone(rs.getString("extension"), rs.getString("telephone")));
        rs.next();
      }
      rs = titres.executeQuery();
      if (rs.first()) {
        while (!rs.isAfterLast())
          pers.addTitres(rs.getString("title"));
        rs.next();
      }
      return pers.build();
    }


    return null;
  }

  static void removePersonnel(String name, Connection co) throws SQLException {
    PreparedStatement persbd = co.prepareStatement("REMOVE * FROM PERSONNEL WHERE name = ?");
    persbd.setString(1, name);
    persbd.executeQuery();
    persbd = co.prepareStatement("REMOVE * FROM CONTIENT WHERE namePersonnel = ?");
    persbd.setString(1, name);
    persbd.executeQuery();
    persbd = co.prepareStatement("REMOVE * FROM COMPOSE WHERE namePersonnel = ?");
    persbd.setString(1, name);
    persbd.executeQuery();
  }

  @Override
  public Personnel create(Personnel grp) {
    try {
      FabriqueDaoBd.createTables(co);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    try {
      co.setAutoCommit(false);
      insertPersonnel(grp, co);
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
  public Personnel find(String name) {
    try {
      FabriqueDaoBd.createTables(co);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    try {
      return searchPersonnel(name, co);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Personnel update(Personnel grp) {
    try {
      FabriqueDaoBd.createTables(co);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    try {
      co.setAutoCommit(false);
      removePersonnel(grp.getName(), co);
      insertPersonnel(grp, co);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      try {
        co.rollback();
      } catch (SQLException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }
    try {
      co.setAutoCommit(true);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return grp;
  }

  @Override
  public void delete(Personnel grp) {
    try {
      FabriqueDaoBd.createTables(co);
    } catch (SQLException e) {
      e.printStackTrace();
      return;
    }
    try {
      removePersonnel(grp.getName(), co);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
