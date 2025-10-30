package game.heroes;

import game.core.Hero;
import game.strategies.attack.RangedAttack;
import game.strategies.defense.DodgeDefense;
import game.core.events.GameEvent;
import game.enums.EventType;

public class Archer extends Hero {
    public Archer(String name) {
        super(name, 100, 80, 28);
        setAttackStrategy(new RangedAttack());
        setDefenseStrategy(new DodgeDefense());
    }

    @Override
    public void useUltimateAbility(Hero target) {
        if (getMana() < 40) return;

        useMana(40);
        int damage = (int)(getAttackPower() * 2.5); // Высокий урон, игнорирующий защиту

        notifyObservers(new GameEvent(
                EventType.ULTIMATE_USED, this, target,
                getName() + " использует СНАЙПЕРСКИЙ ВЫСТРЕЛ! Наносит сокрушительный урон " + damage
        ));

        target.receiveDamage(damage);
    }

    @Override
    public String getDescription() {
        return "Лучник - меткий стрелок с шансом критического удара";
    }
}