package game.heroes;

import game.core.Hero;
import game.strategies.attack.MeleeAttack;
import game.strategies.defense.DodgeDefense;

public class Assassin extends Hero {
    public Assassin(String name) {
        super(name, 95, 80, 26);
        setAttack(new MeleeAttack());
        setDefense(new DodgeDefense());
    }

    @Override
    public void attack(Hero target) {
        if (!isAlive() || !target.isAlive()) return;

        if (Math.random() < 0.4 && getMana() >= 10) {
            useMana(10);
            int damage = getAttackPower();
            target.takeDamage(damage);
            target.addPoison(2); // Ассасин добавляет 2 стака яда
            notifyWatchers(getName() + " наносит отравленный удар!");
        } else {
            super.attack(target);
        }
    }

    @Override
    public void useUltimate(Hero target) {
        if (getMana() < 45) {
            notifyWatchers(getName() + " пытается использовать смертельный удар, но нет маны!");
            return;
        }

        useMana(45);
        int baseDamage = getAttackPower();
        int poisonBonus = getPoisonStacks() * 8;
        int total = baseDamage + poisonBonus;
        target.takeDamage(total);

        notifyWatchers(getName() + " использует СМЕРТЕЛЬНЫЙ УДАР! Наносит " + total + " урона");
    }

    @Override
    public String getDescription() {
        return "Ассасин - мастер ядов";
    }
}