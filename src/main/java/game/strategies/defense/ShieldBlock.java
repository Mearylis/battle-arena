package game.strategies.defense;

public class ShieldBlock implements DefenseStrategy {
    @Override
    public int mitigateDamage(int incomingDamage) {
        int blockedDamage = (int)(incomingDamage * 0.4); // Блокирует 40% урона
        return incomingDamage - blockedDamage;
    }

    @Override
    public String getDescription() {
        return "Блок щитом";
    }
}