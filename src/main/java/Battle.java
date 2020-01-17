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
    this.heroes = heroes;
    this.monsters = monsters;
    this.battleQueue = new ArrayList<>();
    this.battleOver = false;
    PriorityQueue<Character> initialQueue = new PriorityQueue<>(
        Collections.reverseOrder()
    );
    // Have the characters roll initiative
    for (Player hero: this.heroes) {
      hero.getInitiative();
      initialQueue.add(hero);
    }
    for (Monster monster: this.monsters) {
      monster.getInitiative();
      initialQueue.add(monster);
    }
    // Transform queue into sorted arraylist for easy iteration
    Character fastest;
    while ((fastest = initialQueue.poll()) != null) {
      this.battleQueue.add(fastest);
    }
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
    this.roundCounter += 1;
    ArrayList<Character> fallenCharacters = new ArrayList<>();
    // Loop over the queue-list and figure out attacks
    for (Character character: this.battleQueue) {
      // Skip any characters with negative HP this round
      if (character.getHitPoints() <= 0) {
        continue;
      }
      Random rand = new Random();
      boolean attackOrDefend = rand.nextBoolean(); // Attack = true, Defend = false
      // Determine whether to attack or defend based on HP
      if (character.getHitPoints() <= 10) {
        if (!attackOrDefend) {
          character.defend();
          continue;
        }
      }
      // We're committed to attacking
      // Determine if the character is a hero or a monster for target selection
      Character target;
      if (this.heroes.contains(character)) {
        target = this.getRandomCharacterFromList(this.monsters);
      } else {
        target = this.getRandomCharacterFromList(this.heroes);
      }
      // Have the character attack the target
      target.takeDamage(character.attack());
      if (target.getHitPoints() <= 0) {
        fallenCharacters.add(target);
      }
    }
    // Go through the list of fallen characters to remove them from the battle queue
    for (Character fallenCharacter: fallenCharacters) {
      this.battleQueue.remove(fallenCharacter);
      if (this.heroes.contains(fallenCharacter)) {
        this.heroes.remove(fallenCharacter);
      } else {
        this.monsters.remove(fallenCharacter);
      }
    }
    System.out.println(String.format("Round %d resolved.", this.roundCounter));
  }

  private Character getRandomCharacterFromList(ArrayList characters) {
    Random rand = new Random();
    int randomIndex = rand.nextInt(characters.size());
    return (Character) characters.get(randomIndex);
  }
}
