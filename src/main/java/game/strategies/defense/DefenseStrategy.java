package game.strategies.defense;

public interface DefenseStrategy {
    int reduceDamage(int damage);
    String getDescription();
}