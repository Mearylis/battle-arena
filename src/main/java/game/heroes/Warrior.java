package game.heroes;

import game.core.Hero;
import game.strategies.attack.MeleeAttack;
import game.strategies.defense.ShieldBlock;

public class Warrior extends Hero {
    public Warrior(String name) {
        super(name, 160, 40, 28);
        setAttack(new MeleeAttack());
        setDefense(new ShieldBlock());
    }

    @Override
    public void useUltimate(Hero target) {
        if (getMana() < 30) {
            notifyWatchers(getName() + " пытается использовать ультимейт, но нет маны!");
            return;
        }

        useMana(30);
        int damage = (int)(getAttackPower() * 2.0);
        target.takeDamage(damage);

        notifyWatchers(getName() + " использует МОЩНЫЙ УДАР! Наносит " + damage + " урона");
    }

    @Override
    public String getDescription() {
        return "Воин - сильный и крепкий боец";
    }
}