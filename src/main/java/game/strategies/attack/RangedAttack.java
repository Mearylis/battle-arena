package game.strategies.attack;

import game.core.Hero;

public class RangedAttack implements AttackStrategy {
    @Override
    public void execute(Hero attacker, Hero target) {
        int baseDamage = (int)(attacker.getAttackPower() * 1.2);
        int finalDamage = target.getActiveDefense().mitigateDamage(baseDamage);

        // 30% шанс критического удара для лучника
        if (Math.random() < 0.3) {
            finalDamage = (int)(finalDamage * 1.5);
            String description = String.format("%s совершает критический выстрел по %s!",
                    attacker.getName(), target.getName());
            notifyAttack(attacker, target, description, finalDamage);
        } else {
            String description = String.format("%s стреляет по %s",
                    attacker.getName(), target.getName());
            notifyAttack(attacker, target, description, finalDamage);
        }

        target.receiveDamage(finalDamage);
    }

    @Override
    public String getDescription() {
        return "Дальнобойная атака";
    }

    @Override
    public int getManaCost() {
        return 5;
    }
}