package game.strategies.attack;

import game.core.Hero;

public class MeleeAttack implements AttackStrategy {
    @Override
    public void execute(Hero attacker, Hero target) {
        int damage = attacker.getAttackPower();
        target.takeDamage(damage);
        attacker.notifyWatchers(attacker.getName() + " бьет мечом " + target.getName());
    }

    @Override
    public String getDescription() {
        return "Ближний бой";
    }

    @Override
    public int getManaCost() {
        return 0;
    }
}