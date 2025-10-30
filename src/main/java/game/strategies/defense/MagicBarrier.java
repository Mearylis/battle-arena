package game.strategies.defense;

public class MagicBarrier implements DefenseStrategy {
    @Override
    public int mitigateDamage(int incomingDamage) {
        // Магический барьер лучше против магических атак
        int reducedDamage = (int)(incomingDamage * 0.3); // Уменьшает на 70%
        return reducedDamage;
    }

    @Override
    public String getDescription() {
        return "Магический барьер";
    }
}