package game.heroes;

import game.core.Hero;
import game.strategies.attack.MagicAttack;
import game.strategies.defense.MagicBarrier;

public class Mage extends Hero {
    public Mage(String name) {
        super(name, 85, 150, 32);
        setAttack(new MagicAttack());
        setDefense(new MagicBarrier());
    }

    @Override
    public void useUltimate(Hero target) {
        if (getMana() < 60) {
            notifyWatchers(getName() + " пытается призвать молнию, но нет маны!");
            return;
        }

        useMana(60);
        int damage = (int)(getAttackPower() * 2.2);
        target.takeDamage(damage);
        restoreMana(25);

        notifyWatchers(getName() + " призывает МОЛНИЮ! Наносит " + damage + " урона");
    }

    @Override
    public String getDescription() {
        return "Маг - могущественный заклинатель";
    }
}