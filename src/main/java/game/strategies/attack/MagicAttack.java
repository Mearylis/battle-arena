package game.strategies.attack;

import game.core.Hero;

public class MagicAttack implements AttackStrategy {
    @Override
    public void execute(Hero attacker, Hero target) {
        if (attacker.getMana() < getManaCost()) {
            String description = String.format("%s пытается читать заклинание, но не хватает маны!",
                    attacker.getName());
            notifyAttack(attacker, target, description, 0);
            return;
        }

        attacker.useMana(getManaCost());
        int baseDamage = (int)(attacker.getAttackPower() * 1.4);
        int finalDamage = target.getActiveDefense().mitigateDamage(baseDamage);

        String description = String.format("%s бросает магический снаряд в %s",
                attacker.getName(), target.getName());

        notifyAttack(attacker, target, description, finalDamage);
        target.receiveDamage(finalDamage);
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