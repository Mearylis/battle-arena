package game.factories;

import game.core.Hero;
import game.heroes.Archer;
import game.heroes.Mage;
import game.heroes.Warrior;
import game.heroes.Assassin;
import game.enums.HeroType;

public class HeroFactory {
    public static Hero createHero(HeroType type, String name) {
        switch (type) {
            case WARRIOR:
                return new Warrior(name);
            case MAGE:
                return new Mage(name);
            case ARCHER:
                return new Archer(name);
            case ASSASSIN:
                return new Assassin(name);
            default:
                throw new IllegalArgumentException("Неизвестный тип героя: " + type);
        }
    }
}