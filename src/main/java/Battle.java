import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

public class Battle {
  private ArrayList<Player> heroes;
  private ArrayList<Monster> monsters;
  private ArrayList<Character> battleQueue;
  private boolean battleOver;
  private int roundCounter;

  /**
   * Initializes a battle instance between heroes and monsters.
   * @param heroes An ArrayList of player characters.
   * @param monsters An ArrayList of monster characters.
   */
  public Battle(ArrayList<Player> heroes, ArrayList<Monster> monsters) {

  }

  public boolean isBattleOver() {
    return this.battleOver;
  }

  /**
   * Runs a battle instance between two sets of characters.
   */
  public void conductBattle() {
    while (!this.battleOver) {
      this.resolveRound();
      if (this.heroes.size() == 0) {
        System.out.println("The battle is over, and the monsters have won.");
        this.battleOver = true;
      } else if (this.monsters.size() == 0) {
        System.out.println("The battle is over, and the heroes have won.");
        this.battleOver = true;
      }
    }
  }

  private void resolveRound() {

  }

  /**
   * Helper function to select a random element from an ArrayList
   * @param characters The ArrayList to select an element from.
   * @return A random element from the given list.
   */
  private Character getRandomCharacterFromList(ArrayList characters) {
    Random rand = new Random();
    int randomIndex = rand.nextInt(characters.size());
    return (Character) characters.get(randomIndex);
  }
}
