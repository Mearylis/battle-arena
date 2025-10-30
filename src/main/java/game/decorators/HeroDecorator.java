package game.decorators;

import game.core.Hero;
import game.strategies.attack.AttackStrategy;
import game.strategies.defense.DefenseStrategy;
import game.observers.GameObserver;
import game.core.events.GameEvent;

import java.util.List;

public abstract class HeroDecorator extends Hero {
    protected Hero decoratedHero;

    public HeroDecorator(Hero hero) {
        super(hero.getName(), hero.getHealth(), hero.getMana(), hero.getAttackPower());
        this.decoratedHero = hero;

        // Копируем все стратегии и наблюдатели
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
    }

    @Override
    public void useUltimateAbility(Hero target) {
        decoratedHero.useUltimateAbility(target);
    }

    @Override
    public String getDescription() {
        return decoratedHero.getDescription();
    }

    // Делегируем все остальные методы decoratedHero
    @Override
    public int getHealth() {
        return decoratedHero.getHealth();
    }

    @Override
    public int getMaxHealth() {
        return decoratedHero.getMaxHealth();
    }

    @Override
    public int getMana() {
        return decoratedHero.getMana();
    }

    @Override
    public int getMaxMana() {
        return decoratedHero.getMaxMana();
    }

    @Override
    public int getAttackPower() {
        return decoratedHero.getAttackPower();
    }

    @Override
    public boolean isAlive() {
        return decoratedHero.isAlive();
    }

    @Override
    public AttackStrategy getActiveAttack() {
        return decoratedHero.getActiveAttack();
    }

    @Override
    public DefenseStrategy getActiveDefense() {
        return decoratedHero.getActiveDefense();
    }

    @Override
    public List<GameObserver> getObservers() {
        return decoratedHero.getObservers();
    }

    @Override
    public void heal(int amount) {
        decoratedHero.heal(amount);
    }

    @Override
    public void useMana(int amount) {
        decoratedHero.useMana(amount);
    }

    @Override
    public void restoreMana(int amount) {
        decoratedHero.restoreMana(amount);
    }

    @Override
    public void setAttackStrategy(AttackStrategy strategy) {
        decoratedHero.setAttackStrategy(strategy);
    }

    @Override
    public void setDefenseStrategy(DefenseStrategy strategy) {
        decoratedHero.setDefenseStrategy(strategy);
    }

    @Override
    public void registerObserver(GameObserver observer) {
        decoratedHero.registerObserver(observer);
    }

    @Override
    public void removeObserver(GameObserver observer) {
        decoratedHero.removeObserver(observer);
    }

    @Override
    public void notifyObservers(GameEvent event) {
        decoratedHero.notifyObservers(event);
    }
}