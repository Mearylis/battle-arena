package game.strategies.defense;

public class MagicBarrier implements DefenseStrategy {
    @Override
    public int mitigateDamage(int incomingDamage) {
        int reducedDamage = (int)(incomingDamage * 0.3);
        return reducedDamage;
    }

    @Override
    public String getDescription() {
        return "Магический барьер";
    }
}