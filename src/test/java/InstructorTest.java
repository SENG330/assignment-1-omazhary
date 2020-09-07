import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Player Test Class")
public class InstructorTest {
  private static Player player1;
  private static Player player2;
  private static Player monster1;
  private static Player monster2;

  @BeforeAll
  static void setUp() {
    player1 = new Player(4, 20, 6, "Aragorn");
    player2 = new Player(24, 40, 10, "Gandalf");
    monster1 = new Player(4, 20, 6, "Beholder");
    monster2 = new Player(24, 40, 10, "Tiamat");
  }

  @Test
  void testPlayerTakeDamageWhileDefending() {
    player1.defend();
    int damage = 5;
    int oldHP = player1.getHitPoints();
    int appliedDamage;
    double damageRaw = Math.floor(damage / 2.0);
    appliedDamage = (int) damageRaw;
    int newHP = oldHP - appliedDamage;
    player1.takeDamage(damage);
    assertEquals(newHP, player1.getHitPoints());
  }

  @Test
  void testMonsterTakeDamageWhileDefending() {
    monster1.defend();
    int damage = 5;
    int oldHP = monster1.getHitPoints();
    int appliedDamage;
    double damageRaw = Math.floor(damage / 2.0);
    appliedDamage = (int) damageRaw;
    int newHP = oldHP - appliedDamage;
    monster1.takeDamage(damage);
    assertEquals(newHP, monster1.getHitPoints());
  }

  @Test
  void testPlayerComparability() {
    player1.getInitiative();
    player2.getInitiative();
    assertTrue(player2.compareTo(player1) > 0);
  }

  @Test
  void testPlayerStringOutput() {
    String expectedOutput = "Gandalf has 40 HP left.";
    assertEquals(expectedOutput, player2.toString());
    player2.takeDamage(50);
    expectedOutput = "Gandalf has -10 HP left. Gandalf is unconscious.";
    assertEquals(expectedOutput, player2.toString());
  }

  @Test
  void testMonsterComparability() {
    monster1.getInitiative();
    monster2.getInitiative();
    assertTrue(monster2.compareTo(monster1) > 0);
  }

  @Test
  void testMonsterStringOutput() {
    String expectedOutput = "Tiamat has 40 HP left.";
    assertEquals(expectedOutput, monster2.toString());
    monster2.takeDamage(50);
    expectedOutput = "Tiamat has -10 HP left. Tiamat is unconscious.";
    assertEquals(expectedOutput, monster2.toString());
  }

}
