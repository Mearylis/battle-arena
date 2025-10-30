package game.strategies.defense;

public class DodgeDefense implements DefenseStrategy {
    @Override
    public int mitigateDamage(int incomingDamage) {
        if (Math.random() < 0.25) {
            return 0;
        }
        return incomingDamage;
    }

    @Override
    public String getDescription() {
        return "Уклонение";
    }
}