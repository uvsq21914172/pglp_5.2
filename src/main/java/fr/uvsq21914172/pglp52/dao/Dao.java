package fr.uvsq21914172.pglp52.dao;

/**
 * Dao.
 * 
 * @author Dalil
 *
 * @param <T> Classe géré.
 */
public abstract class Dao<T> {

  /**
   * Creatioon.
   * 
   * @param obj Objet a sauvegarder.
   * @return Objet save ou null.
   */
  public abstract T create(T obj);

  /**
   * Recherche.
   * 
   * @param id Objet a sauvegarder.
   * @return Objet trouv ou null.
   */
  public abstract T find(String id);

  /**
   * Mise a jour.
   * 
   * @param obj Objet a mertre a jour.
   * @return Objet mis a jour ou null.
   */
  public abstract T update(T obj);

  /**
   * Supression.
   * 
   * @param obj Oojet a suprime.
   */
  public abstract void delete(T obj);
}
