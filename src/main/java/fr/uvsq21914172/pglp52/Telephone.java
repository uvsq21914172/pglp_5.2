package fr.uvsq21914172.pglp52;

import java.io.Serializable;

/**
 * Classe Representant les numeros de telephone.
 * 
 * @author Dalil
 *
 */
public class Telephone implements Serializable {
  private static final long serialVersionUID = -2895466524153804072L;
  private String num;
  private String extension;

  /**
   * Constructeur.
   * 
   * @param extension Extension géographique.
   * @param num Numero de telephone.
   */
  public Telephone(String extension, String num) {
    this.extension = extension;
    this.num = num;
  }

  /**
   * Getter de l'attibut numero.
   * 
   * @return Numero.
   */
  public String getNum() {
    return num;
  }

  /**
   * Getter de l'attribut extension.
   * 
   * @return Extension.
   */
  public String getExtension() {
    return extension;
  }
}
