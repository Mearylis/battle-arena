package game.strategies.attack;

import game.core.Hero;

public class RangedAttack implements AttackStrategy {
    @Override
    public void execute(Hero attacker, Hero target) {
        int damage = (int)(attacker.getAttackPower() * 1.15);

        if (Math.random() < 0.3) {
            damage = (int)(damage * 1.5);
            attacker.notifyWatchers(attacker.getName() + " попадает точно в цель! " + target.getName());
        } else {
            attacker.notifyWatchers(attacker.getName() + " стреляет в " + target.getName());
        }

        target.takeDamage(damage);
    }

    @Override
    public String getDescription() {
        return "Дальний бой";
    }

    @Override
    public int getManaCost() {
        return 5;
    }
}