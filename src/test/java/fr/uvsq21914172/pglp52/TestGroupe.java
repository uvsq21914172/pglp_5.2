package fr.uvsq21914172.pglp52;

import static org.junit.Assert.assertEquals;
import java.util.Iterator;
import org.junit.Test;
import fr.uvsq21914172.pglp52.Groupable;
import fr.uvsq21914172.pglp52.Groupe;

public class TestGroupe {

  @Test
  public void testIterateurLargeur() {
    Groupe groupe1 = new Groupe("1");
    Groupe groupe2 = new Groupe("2");
    groupe1.add(groupe2);
    Groupe groupe3 = new Groupe("3");
    groupe2.add(groupe3);
    Groupe groupe4 = new Groupe("4");
    groupe1.add(groupe4);
    Iterator<Groupable> it = groupe1.iteratorLargeur();
    assertEquals("1", it.next().getName());
    assertEquals("2", it.next().getName());
    assertEquals("4", it.next().getName());
    assertEquals("3", it.next().getName());
  }
  
  @Test
  public void testIterateurProfondeur() {
    Groupe groupe1 = new Groupe("1");
    Groupe groupe2 = new Groupe("2");
    groupe1.add(groupe2);
    Groupe groupe3 = new Groupe("3");
    groupe2.add(groupe3);
    Groupe groupe4 = new Groupe("4");
    groupe1.add(groupe4);
    Iterator<Groupable> it = groupe1.iteratorProfondeur();
    assertEquals("1", it.next().getName());
    assertEquals("4", it.next().getName());
    assertEquals("2", it.next().getName());
    assertEquals("3", it.next().getName());
  }

}
