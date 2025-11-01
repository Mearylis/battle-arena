package game.heroes;

import game.core.Hero;
import game.strategies.attack.RangedAttack;
import game.strategies.defense.DodgeDefense;

public class Archer extends Hero {
    public Archer(String name) {
        super(name, 110, 70, 30);
        setAttack(new RangedAttack());
        setDefense(new DodgeDefense());
    }

    @Override
    public void useUltimate(Hero target) {
        if (getMana() < 35) {
            notifyWatchers(getName() + " пытается сделать снайперский выстрел, но нет маны!");
            return;
        }

        useMana(35);
        int damage = (int)(getAttackPower() * 2.8);
        target.takeDamage(damage);

        notifyWatchers(getName() + " делает СНАЙПЕРСКИЙ ВЫСТРЕЛ! Наносит " + damage + " урона");
    }

    @Override
    public String getDescription() {
        return "Лучник - меткий стрелок";
    }
}