package game.strategies.defense;

public class DodgeDefense implements DefenseStrategy {
    @Override
    public int reduceDamage(int damage) {
        if (Math.random() < 0.25) {
            return 0;
        }
        return damage;
    }

    @Override
    public String getDescription() {
        return "Уклонение";
    }
}