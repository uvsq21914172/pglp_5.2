package fr.uvsq21914172.pglp52.dao;

import fr.uvsq21914172.pglp52.Groupe;
import fr.uvsq21914172.pglp52.Personnel;

/**
 * Fabrique.
 * 
 * @author Dalil
 *
 */
public class FabriqueDao implements AbstractFabriqueDao {

  /**
   * Renvoie un objet Personnel DAO.
   * 
   * @return DAO.
   */
  public Dao<Personnel> getPersonnelDao() {
    return new PersonnelDao("./");
  }

  /**
   * Renvoie un objet Groupe DAO.
   * 
   * @return DAO.
   */
  public Dao<Groupe> getGroupeDao() {
    return new GroupeDao("./");
  }
}
