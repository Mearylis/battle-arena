package game.strategies.defense;


public class DodgeDefense implements DefenseStrategy {
    @Override
    public int mitigateDamage(int incomingDamage) {
        // 25% шанс полностью увернуться
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