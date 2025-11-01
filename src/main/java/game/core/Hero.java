package game.core;

import game.observers.GameObserver;
import game.strategies.attack.AttackStrategy;
import game.strategies.defense.DefenseStrategy;
import game.strategies.defense.ShieldBlock;

import java.util.*;

public abstract class Hero {
    private String name;
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int attackPower;
    private boolean alive;

    private AttackStrategy attack;
    private DefenseStrategy defense;
    private List<GameObserver> watchers;

    // Effects
    private boolean hasFireEnchantment = false;
    private boolean hasStoneSkin = false;
    private int poisonStacks = 0;
    private int poisonTurns = 0;

    public Hero(String name, int health, int mana, int attackPower) {
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.maxMana = mana;
        this.mana = mana;
        this.attackPower = attackPower;
        this.alive = true;
        this.watchers = new ArrayList<>();
        this.defense = new ShieldBlock();
    }

    public void attack(Hero target) {
        if (!alive || !target.alive) return;

        if (mana >= attack.getManaCost()) {
            if (attack.getManaCost() > 0) {
                useMana(attack.getManaCost());
            }
            attack.execute(this, target);

            // Fire enchantment effect
            if (hasFireEnchantment) {
                int fireDamage = 8;
                target.takeDamage(fireDamage);
                notifyWatchers("🔥 Fire enchantment deals " + fireDamage + " extra damage");
            }
        } else {
            notifyWatchers(name + " cannot attack - not enough mana!");
        }

        // Apply poison damage
        applyPoisonDamage();
    }

    public void takeDamage(int damage) {
        if (!alive) return;

        // Stone skin effect
        if (hasStoneSkin) {
            damage = Math.max(0, damage - 15);
        }

        health = Math.max(0, health - damage);
        notifyWatchers(name + " takes " + damage + " damage");

        if (health <= 0) {
            alive = false;
            notifyWatchers(name + " has fallen!");
        }
    }

    private void applyPoisonDamage() {
        if (poisonStacks > 0 && poisonTurns > 0) {
            int poisonDamage = poisonStacks * 4;
            takeDamage(poisonDamage);
            poisonTurns--;
            notifyWatchers("☠️ Poison deals " + poisonDamage + " damage to " + name + " (" + poisonTurns + " turns left)");

            if (poisonTurns == 0) {
                poisonStacks = 0;
                notifyWatchers("💨 Poison wears off from " + name);
            }
        }
    }

    public void addPoison(int stacks) {
        poisonStacks = Math.min(poisonStacks + stacks, 3);
        poisonTurns = 4;
        notifyWatchers("☠️ " + name + " is poisoned! Stacks: " + poisonStacks);
    }

    public void addFireEnchantment() {
        hasFireEnchantment = true;
        notifyWatchers("🔥 " + name + " gains fire enchantment!");
    }

    public void addStoneSkin() {
        hasStoneSkin = true;
        notifyWatchers("🪨 " + name + " gains stone skin!");
    }

    public void heal(int amount) {
        if (!alive) return;

        int old = health;
        health = Math.min(maxHealth, health + amount);
        int healed = health - old;

        if (healed > 0) {
            notifyWatchers(name + " heals " + healed + " HP");
        }
    }

    public void useMana(int amount) {
        mana = Math.max(0, mana - amount);
        if (amount > 0) {
            notifyWatchers(name + " uses " + amount + " mana");
        }
    }

    public void restoreMana(int amount) {
        int old = mana;
        mana = Math.min(maxMana, mana + amount);
        int restored = mana - old;

        if (restored > 0) {
            notifyWatchers(name + " restores " + restored + " mana");
        }
    }

    public void setAttack(AttackStrategy newAttack) {
        this.attack = newAttack;
        notifyWatchers(name + " changes attack to: " + newAttack.getDescription());
    }

    public void setDefense(DefenseStrategy newDefense) {
        this.defense = newDefense;
        notifyWatchers(name + " changes defense to: " + newDefense.getDescription());
    }

    public void addWatcher(GameObserver watcher) {
        watchers.add(watcher);
    }

    public void removeWatcher(GameObserver watcher) {
        watchers.remove(watcher);
    }

    public void notifyWatchers(String message) {
        for (GameObserver watcher : watchers) {
            watcher.onEvent(message);
        }
    }

    public abstract void useUltimate(Hero target);
    public abstract String getDescription();

    // Getters
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    public int getAttackPower() { return attackPower; }
    public boolean isAlive() { return alive; }
    public AttackStrategy getAttack() { return attack; }
    public DefenseStrategy getDefense() { return defense; }
    public int getPoisonStacks() { return poisonStacks; }
}