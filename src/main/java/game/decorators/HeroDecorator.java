package game.decorators;

import game.core.Hero;
import game.strategies.attack.AttackStrategy;
import game.strategies.defense.DefenseStrategy;
import game.observers.GameObserver;
import game.core.events.GameEvent;
import java.util.List;

public abstract class HeroDecorator extends Hero {
    protected final Hero decoratedHero;

    public HeroDecorator(Hero hero) {
        super(hero.getName(), hero.getMaxHealth(), hero.getMaxMana(), hero.getAttackPower());
        this.decoratedHero = hero;

        this.setHealth(hero.getHealth());
        this.setMana(hero.getMana());
        this.setAlive(hero.isAlive());

        this.setAttackStrategy(hero.getActiveAttack());
        this.setDefenseStrategy(hero.getActiveDefense());

        hero.getObservers().forEach(this::registerObserver);
    }

    @Override
    public void performAttack(Hero target) {
        decoratedHero.performAttack(target);
    }

    @Override
    public void receiveDamage(int damage) {
        decoratedHero.receiveDamage(damage);
        this.setHealth(decoratedHero.getHealth());
        this.setAlive(decoratedHero.isAlive());
    }

    @Override
    public void useUltimateAbility(Hero target) {
        decoratedHero.useUltimateAbility(target);
        this.setMana(decoratedHero.getMana());
    }

    @Override
    public String getDescription() {
        return decoratedHero.getDescription();
    }

    @Override
    public void heal(int amount) {
        decoratedHero.heal(amount);
        this.setHealth(decoratedHero.getHealth());
    }

    @Override
    public void useMana(int amount) {
        decoratedHero.useMana(amount);
        this.setMana(decoratedHero.getMana());
    }

    @Override
    public void restoreMana(int amount) {
        decoratedHero.restoreMana(amount);
        this.setMana(decoratedHero.getMana());
    }

    @Override public int getHealth() { return decoratedHero.getHealth(); }
    @Override public int getMaxHealth() { return decoratedHero.getMaxHealth(); }
    @Override public int getMana() { return decoratedHero.getMana(); }
    @Override public int getMaxMana() { return decoratedHero.getMaxMana(); }
    @Override public int getAttackPower() { return decoratedHero.getAttackPower(); }
    @Override public boolean isAlive() { return decoratedHero.isAlive(); }
    @Override public AttackStrategy getActiveAttack() { return decoratedHero.getActiveAttack(); }
    @Override public DefenseStrategy getActiveDefense() { return decoratedHero.getActiveDefense(); }
    @Override public List<GameObserver> getObservers() { return decoratedHero.getObservers(); }

    @Override public void setAttackStrategy(AttackStrategy strategy) { decoratedHero.setAttackStrategy(strategy); }
    @Override public void setDefenseStrategy(DefenseStrategy strategy) { decoratedHero.setDefenseStrategy(strategy); }
    @Override public void registerObserver(GameObserver observer) { decoratedHero.registerObserver(observer); }
    @Override public void removeObserver(GameObserver observer) { decoratedHero.removeObserver(observer); }
    @Override public void notifyObservers(GameEvent event) { decoratedHero.notifyObservers(event); }
}