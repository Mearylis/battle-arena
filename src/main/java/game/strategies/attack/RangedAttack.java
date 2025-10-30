package game.strategies.attack;

import game.core.Hero;

public class RangedAttack implements AttackStrategy {
    @Override
    public void execute(Hero attacker, Hero target) {
        int baseDamage = (int)(attacker.getAttackPower() * 1.15);
        int finalDamage = target.getActiveDefense().mitigateDamage(baseDamage);

        if (Math.random() < 0.3) {
            finalDamage = (int)(finalDamage * 1.5);
            String description = String.format("%s попадает точно в цель по %s!",
                    attacker.getName(), target.getName());
            notifyAttack(attacker, target, description, finalDamage);
        } else {
            String description = String.format("%s стреляет из лука в %s",
                    attacker.getName(), target.getName());
            notifyAttack(attacker, target, description, finalDamage);
        }

        target.receiveDamage(finalDamage);
    }

    @Override
    public String getDescription() {
        return "Стрельба из лука";
    }

    @Override
    public int getManaCost() {
        return 5;
    }
}