package game.strategies.attack;

import game.core.Hero;

public class MagicAttack implements AttackStrategy {
    @Override
    public void execute(Hero attacker, Hero target) {
        if (attacker.getMana() < getManaCost()) {
            attacker.notifyWatchers(attacker.getName() + " не хватает маны для заклинания!");
            return;
        }

        attacker.useMana(getManaCost());
        int damage = (int)(attacker.getAttackPower() * 1.4);
        target.takeDamage(damage);
        attacker.notifyWatchers(attacker.getName() + " бросает магию в " + target.getName());
    }

    @Override
    public String getDescription() {
        return "Магическая атака";
    }

    @Override
    public int getManaCost() {
        return 12;
    }
}