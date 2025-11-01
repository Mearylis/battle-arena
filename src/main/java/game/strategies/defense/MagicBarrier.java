package game.strategies.defense;

public class MagicBarrier implements DefenseStrategy {
    @Override
    public int reduceDamage(int damage) {
        return (int)(damage * 0.3);
    }

    @Override
    public String getDescription() {
        return "Магический барьер";
    }
}