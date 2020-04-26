package fr.uvsq21914172.pglp52.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import fr.uvsq21914172.pglp52.Groupe;

/**
 * DAO du groupe.
 * 
 * @author Dalil
 * 
 */

public class GroupeDao extends Dao<Groupe> {

  /**
   * Creatioon.
   * 
   * @param obj Objet a sauvegarder.
   * @return Objet save ou null.
   */

  private Path path;

  public GroupeDao(String pathGeneral) {
    Paths.get(pathGeneral, "Groupe");
    path = Paths.get(pathGeneral, "Groupe");
  }

  @Override
  public Groupe create(Groupe obj) {
    File f = path.toFile();
    if (!(f.exists())) {
      if (!f.mkdirs()) {
        return null;
      }
    }

    Path serpath = Paths.get(path.toString(), obj.getName() + ".dat");
    File serfile = serpath.toFile();
    if (!(serfile.exists())) {
      try {
        serialize(obj, serfile);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        obj = null;
      }
    } else {
      obj = null;
    }
    return obj;
  }

  /**
   * Recherche.
   * 
   * @param id Objet a sauvegarder.
   * @return Objet trouv ou null.
   */
  @Override
  public Groupe find(String id) {
    Groupe obj = null;
    Path serpath = Paths.get(path.toString(), id + ".dat");
    File serfile = serpath.toFile();
    if ((serfile.exists())) {
      try {
        obj = deserialize(serfile);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        obj = null;
      }
    }
    return obj;
  }

  /**
   * Mise a jour.
   * 
   * @param obj Objet a mertre a jour.
   * @return Objet mis a jour ou null.
   */
  @Override
  public Groupe update(Groupe obj) {
    Path serpath = Paths.get(path.toString(), obj.getName() + ".dat");
    File serfile = serpath.toFile();
    if ((serfile.exists())) {
      if (!serfile.delete()) {
        return null;
      }
    }
    return create(obj);
  }

  /**
   * Supression.
   * 
   * @param obj Oojet a suprime.
   */
  @Override
  public void delete(Groupe obj) {
    Path serpath = Paths.get(path.toString(), obj.getName() + ".dat");
    File serfile = serpath.toFile();
    if ((serfile.exists())) {
      if (!serfile.delete()) {
        return;
      }
    }
  }


  /**
   * sSerialise.
   * 
   * @param obj Serialisable.
   * @param serfile Fichier.
   * @throws IOException Exception.
   * @throws FileNotFoundException Exception.
   */
  private void serialize(Groupe obj, File serfile) throws FileNotFoundException, IOException {
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serfile));

    oos.writeObject(obj);
    oos.close();
  }

  /**
   * Deserialize.
   * 
   * @param serfile Fichier de sotire.
   * @return Oobjet deserializé.
   * @throws IOException Exception.
   * @throws ClassNotFoundException Exception.
   */
  private Groupe deserialize(File serfile) throws ClassNotFoundException, IOException {
    ObjectInputStream oin = new ObjectInputStream(new FileInputStream(serfile));

    Object obj = oin.readObject();
    oin.close();
    if (obj instanceof Groupe) {
      return (Groupe) obj;
    }
    return null;
  }
}
