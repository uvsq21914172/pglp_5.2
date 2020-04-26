package fr.uvsq21914172.pglp52;


import static org.junit.Assert.*;
import java.time.LocalDate;
import org.junit.Test;
import fr.uvsq21914172.pglp52.Personnel;
import fr.uvsq21914172.pglp52.Telephone;

public class TestPersonnel {

  @Test
  public void testBuilder1() {
    Personnel.Builder builder = new Personnel.Builder("H", "B", LocalDate.now());
    Personnel personnel = builder.build();
    assertEquals("HB", personnel.getName());
  }

  @Test
  public void testBuilder2() {
    Personnel.Builder builder = new Personnel.Builder("H", "B", LocalDate.now());
    builder.addTitres("directeur");
    Personnel personnel = builder.build();
    assertTrue(personnel.getTitres().contains("directeur"));
    assertFalse(personnel.getTitres().contains("nondirecteur"));
  }

  @Test
  public void testBuilder3() {
    Personnel.Builder builder = new Personnel.Builder("H", "B", LocalDate.now());
    Telephone tel = new Telephone("+33", "123456789");
    builder.addNumber(tel);
    Personnel personnel = builder.build();
    assertTrue(personnel.getNumbers().contains(tel));
    assertFalse(personnel.getNumbers().contains(new Telephone("+44", "987654321")));
  }

}
