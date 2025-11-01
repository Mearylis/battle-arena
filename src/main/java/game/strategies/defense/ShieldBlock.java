package game.strategies.defense;

public class ShieldBlock implements DefenseStrategy {
    @Override
    public int reduceDamage(int damage) {
        return damage - (int)(damage * 0.4);
    }

    @Override
    public String getDescription() {
        return "Защита щитом";
    }
}