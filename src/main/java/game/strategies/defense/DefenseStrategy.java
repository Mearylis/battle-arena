package game.strategies.defense;

public interface DefenseStrategy {
    int mitigateDamage(int incomingDamage);
    String getDescription();
}