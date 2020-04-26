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
import fr.uvsq21914172.pglp52.Personnel;

public class PersonnelDao extends Dao<Personnel> {
  /**
   * Creatioon.
   * 
   * @param obj Objet a sauvegarder.
   * @return Objet save ou null.
   */

  private Path path;

  public PersonnelDao(String pathGeneral) {
    Paths.get(pathGeneral, "Personnel");
    path = Paths.get(pathGeneral, "Personnel");
  }

  @Override
  public Personnel create(Personnel obj) {
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
  public Personnel find(String id) {
    Personnel obj = null;
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
  public Personnel update(Personnel obj) {
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
  public void delete(Personnel obj) {
    Path serpath = Paths.get(path.toString(), obj.getName() + ".dat");
    File serfile = serpath.toFile();
    if ((serfile.exists())) {
      if (!serfile.delete()) {
        return;
      }
    }
  }


  /**
   * Serialise.
   * 
   * @param obj Objet a rendre serial.
   * @param serfile Finle
   * @throws IOException Exception.
   * @throws FileNotFoundException Exception.
   */
  private void serialize(Personnel obj, File serfile) throws FileNotFoundException, IOException {
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serfile));

    oos.writeObject(obj);
    oos.close();
  }

  /**
   * Deserialise.
   * 
   * @param serfile Fichier a désérialiser.
   * @return Objet deserialisé.
   * @throws IOException Exception.
   * @throws ClassNotFoundException Exception.
   */
  private Personnel deserialize(File serfile) throws ClassNotFoundException, IOException {
    ObjectInputStream oin = new ObjectInputStream(new FileInputStream(serfile));

    Object obj = oin.readObject();
    oin.close();
    if (obj instanceof Personnel) {
      return (Personnel) obj;
    }
    return null;
  }
}
