package game.strategies.attack;

import game.core.Hero;

public class MeleeAttack implements AttackStrategy {
    @Override
    public void execute(Hero attacker, Hero target) {
        int baseDamage = attacker.getAttackPower();
        int finalDamage = target.getActiveDefense().mitigateDamage(baseDamage);

        String description = String.format("%s наносит ближнюю атаку по %s",
                attacker.getName(), target.getName());

        notifyAttack(attacker, target, description, finalDamage);
        target.receiveDamage(finalDamage);
    }

    @Override
    public String getDescription() {
        return "Ближняя атака";
    }

    @Override
    public int getManaCost() {
        return 0;
    }
}