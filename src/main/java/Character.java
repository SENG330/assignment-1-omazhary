public abstract class Character implements Comparable<Character> {

  private int initiativeModifier;
  private int initiativeScore;
  private int hitPoints;
  private int attackModifier;
  private boolean defending;
  private String name;
  private DiceRoller roller;
  private CharacterState state;

  /**
   * Creates a character with the provided attributes.
   * @param initiativeModifier A character's initiative modifier gets added to the initiative roll.
   * @param hitPoints A character's hit points represent their health.
   * @param attackModifier A character's attack modifier is added to damage they deal on attacking.
   * @param name The name of the character you are creating.
   */
  public Character(int initiativeModifier, int hitPoints, int attackModifier, String name) {
    this.initiativeModifier = initiativeModifier;
    this.hitPoints = hitPoints;
    this.attackModifier = attackModifier;
    this.name = name;
    this.defending = false;
    this.initiativeScore = 0;
    this.roller = new DiceRoller();
    this.state = CharacterState.ALIVE;
  }

  public int getInitiativeScore() {
    return this.initiativeScore;
  }

  public int getHitPoints() {
    return this.hitPoints;
  }

  public CharacterState getState() { return this.state; }

  /**
   * This method checks whether or not this character is defending.
   * @return true if the character is defending, false otherwise.
   */
  public boolean isDefending() {
    return this.defending;
  }

  /**
   * Generates an initiative value for the current character.
   */
  public void getInitiative() {
    int roll = this.roller.rollDie(DiceTypes.D20);
    this.initiativeScore = roll + this.initiativeModifier;
    System.out.println(
        String.format("%s rolled %d for initiative.", this.name, this.initiativeScore)
    );
  }

  /**
   * This method has the character perform an attack.
   * @return An integer representing the amount of damage dealt.
   */
  public int attack() {
    // Reset the defending flag, because we're no longer defending.
    this.defending = false;
    int roll = this.roller.rollDie(DiceTypes.D8);
    System.out.println(
        String.format("%s attacks for %d damage.", this.name, (roll + this.attackModifier))
    );
    return roll + this.attackModifier;
  }

  /**
   * This method sets the character's defending flag to true, so damage may be halved.
   */
  public void defend() {
    this.defending = true;
    System.out.println(String.format("%s is defending.", this.name));
  }

  public void stopDefending() {
    this.defending = false;
    System.out.println(String.format("%s is no longer defending.", this.name));
  }

  /**
   * This method updates a character's hitpoints depending on how much damage they've taken.
   * @param damage The amount of damage a character has sustained.
   */
  public void takeDamage(int damage) {
    // If we are defending, we take half damage rounded down, otherwise, we take full damage.
    int appliedDamage;
    if (this.defending) {
      double damageRaw = Math.floor(damage / 2.0);
      appliedDamage = (int) damageRaw;
    } else {
      appliedDamage = damage;
    }
    this.hitPoints = this.hitPoints - appliedDamage;
    System.out.println(
        String.format("%s has sustained %d points of damage.", this.name, appliedDamage)
    );
    if (this.hitPoints <= 0) {
      this.state = CharacterState.UNCONSCIOUS;
    }
    System.out.println(this.toString());
  }

  @Override
  public int compareTo(Character char2) {
    return Integer.compare(this.initiativeScore, char2.getInitiativeScore());
  }

  @Override
  public String toString() {
    if (this.state == CharacterState.UNCONSCIOUS) {
      return String.format(
          "%s has %d HP left. %s is unconscious.", this.name, this.hitPoints, this.name
      );
    } else {
      return String.format("%s has %d HP left. %s is alive.", this.name, this.hitPoints, this.name);
    }
  }
}
