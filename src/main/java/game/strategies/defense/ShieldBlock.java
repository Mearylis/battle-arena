package game.strategies.defense;

public class ShieldBlock implements DefenseStrategy {
    @Override
    public int mitigateDamage(int incomingDamage) {
        int blockedDamage = (int)(incomingDamage * 0.4);
        return incomingDamage - blockedDamage;
    }

    @Override
    public String getDescription() {
        return "Защита щитом";
    }
}