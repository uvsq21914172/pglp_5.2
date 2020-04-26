package fr.uvsq21914172.pglp52;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe representant les membres du personnel.
 * 
 * @author Dalil
 *
 */
public class Personnel implements Groupable, Serializable {
  private static final long serialVersionUID = -4239138336583680478L;
  private String nom;
  private String prenom;
  private LocalDate dateDeNaissance;

  private List<String> titres;

  private List<Telephone> numbers;

  /**
   * Classe builder de personnel.
   * 
   * @author Dalil
   *
   */
  public static class Builder {
    private String nom;
    private String prenom;
    private LocalDate dateDeNaissance;

    private List<String> titres = new ArrayList<String>();

    private List<Telephone> numbers = new ArrayList<Telephone>();

    /**
     * Builder.
     * 
     * @param nom Nom.
     * @param prenom Prenom.
     * @param dateDeNaissance Date de Naissance.
     */
    public Builder(String nom, String prenom, LocalDate dateDeNaissance) {
      this.nom = nom;
      this.prenom = prenom;
      this.dateDeNaissance = dateDeNaissance;
    }

    /**
     * Add a number.
     * 
     * @param number Nouveau numero.
     * @return Renvoie le builder.
     */
    public Builder addNumber(Telephone number) {
      numbers.add(number);
      return this;
    }

    /**
     * Ajoute titre.
     * 
     * @param titre Nouveau titre.
     * @return renvoie le builder.
     */
    public Builder addTitres(String titre) {
      titres.add(titre);
      return this;
    }

    /**
     * Build.
     * 
     * @return Renvoie le personnel resultant.
     */
    public Personnel build() {
      return new Personnel(nom, prenom, dateDeNaissance, numbers, titres);
    }
  }

  /**
   * Constructeur privé appelé par le builder.
   * 
   * @param nom Nom de famille.
   * @param prenom Prenom.
   * @param dateDeNaissance Date de naissance.
   * @param numbers Liste des numero.
   * @param titres Liste des titres.
   */
  private Personnel(String nom, String prenom, LocalDate dateDeNaissance, List<Telephone> numbers,
      List<String> titres) {
    this.nom = nom;
    this.prenom = prenom;
    this.dateDeNaissance = dateDeNaissance;
    this.numbers = numbers;
    this.titres = titres;
  }

  /*
   * public String display() { return display(0); }
   * 
   * public String display(int level) { String space = ""; for (int i = 0; i < level; i++) space +=
   * "|"; return space + nom + " " + prenom + "," + dateDeNaissance.toString(); }
   */

  /**
   * Get les titres.
   * 
   * @return Liste des titres.
   */
  public List<String> getTitres() {
    return titres;
  }

  /**
   * Get les num.
   * 
   * @return Listes des numeros de telephone.
   */
  public List<Telephone> getNumbers() {
    return numbers;
  }

  /**
   * Nomde famile.
   * 
   * @return Nom de famille.
   */
  public String getNom() {
    return nom;
  }

  /**
   * Prenom.
   * 
   * @return Prenom.
   */
  public String getPrenom() {
    return prenom;
  }

  /**
   * Nom a afficher.
   */
  public String getName() {
    return nom + prenom;
  }

  /**
   * Date de naissance.
   * 
   * @return Date de Naissance.
   */
  public LocalDate getDateDeNaissance() {
    return dateDeNaissance;
  }

}
