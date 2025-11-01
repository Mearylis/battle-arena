package game.core;

import game.heroes.*;
import game.strategies.attack.*;
import game.strategies.defense.*;
import game.observers.*;

import java.util.*;

public class GameManager {
    private Scanner scanner;
    private Hero player;
    private List<GameObserver> watchers;
    private StatisticsTracker stats;

    public GameManager() {
        scanner = new Scanner(System.in);
        watchers = new ArrayList<>();
        watchers.add(new BattleLogger());
        watchers.add(new GameAnnouncer());
        stats = new StatisticsTracker();
        watchers.add(stats);
    }

    public void run() {
        System.out.println("⚔️  Welcome to Hero Battle Arena! ⚔️");
        System.out.println("====================================");

        player = chooseHero();
        int wins = 0;

        // Regular battles
        for (int battle = 1; battle <= 3; battle++) {
            Hero enemy = createEnemy();
            System.out.println("\n⚔️  BATTLE " + battle + ": " + player.getName() + " vs " + enemy.getName());

            // Buffs for previous wins
            if (wins == 1) {
                player.addFireEnchantment();
            } else if (wins == 2) {
                player.addStoneSkin();
            }

            boolean won = fight(player, enemy);

            if (won) {
                wins++;
                System.out.println("🎉 You won battle " + battle + "!");
                player.heal(40);
                player.restoreMana(30);
                System.out.println("💚 Restored: 40 HP, 30 MP");
            } else {
                System.out.println("💀 You lost battle " + battle);
                break;
            }

            if (battle < 3) {
                System.out.println("\n--- Preparing for next battle ---");
                waitForEnter();
            }
        }

        if (wins == 3) {
            System.out.println("\n" + "🌋".repeat(50));
            System.out.println("🔥 ПОЯВЛЯЕТСЯ ЛЕГЕНДАРНЫЙ БОСС!");
            System.out.println("🔥 ТЕМИРГАЛЫ ДИНМУХАММЕД!");
            System.out.println("🌋".repeat(50));

            Hero boss = new Boss("Темиргалы Динмухаммед");
            for (GameObserver watcher : watchers) {
                boss.addWatcher(watcher);
            }

            boolean wonBoss = fight(player, boss);

            if (wonBoss) {
                System.out.println("\n" + "🏆".repeat(50));
                System.out.println("🏆 ТЫ ВЫЖИЛ");
                System.out.println("🏆 ПОБЕДИЛИ САМОГО ТЕМИРГАЛЫ ДИНМУХАММЕДА! но это не точно");
                System.out.println("🏆".repeat(50));
            } else {
                System.out.println("\n💀 It is RETAKE bro(.....");
            }
        }

        System.out.println("\n⭐ GAME OVER ⭐");
        System.out.println("Total wins: " + wins + " out of 3");
        stats.printStats();
    }

    private Hero chooseHero() {
        System.out.println("\nChoose your hero:");
        System.out.println("1. Warrior - strong and tough");
        System.out.println("2. Mage - powerful spells");
        System.out.println("3. Archer - accurate shots");
        System.out.println("4. Assassin - poisons and tricks");

        int choice;
        while (true) {
            try {
                System.out.print("Your choice (1-4): ");
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 4) break;
                System.out.println("Only 1-4!");
            } catch (Exception e) {
                System.out.println("Enter a number!");
                scanner.next();
            }
        }

        scanner.nextLine();
        System.out.print("Hero name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "Nameless";

        Hero hero = null;
        if (choice == 1) {
            hero = new Warrior(name);
        } else if (choice == 2) {
            hero = new Mage(name);
        } else if (choice == 3) {
            hero = new Archer(name);
        } else if (choice == 4) {
            hero = new Assassin(name);
        }

        for (GameObserver watcher : watchers) {
            hero.addWatcher(watcher);
        }

        return hero;
    }

    private Hero createEnemy() {
        String[] names = {"Garrosh", "Jaina", "Sylvanas", "Valira", "Thrall", "Arthas"};
        String name = names[new Random().nextInt(names.length)];

        int type = new Random().nextInt(4) + 1;
        Hero enemy = null;
        if (type == 1) {
            enemy = new Warrior(name);
        } else if (type == 2) {
            enemy = new Mage(name);
        } else if (type == 3) {
            enemy = new Archer(name);
        } else if (type == 4) {
            enemy = new Assassin(name);
        }

        for (GameObserver watcher : watchers) {
            enemy.addWatcher(watcher);
        }

        return enemy;
    }

    private boolean fight(Hero player, Hero enemy) {
        int round = 1;

        while (player.isAlive() && enemy.isAlive() && round <= 30) {
            System.out.println("\n--- Round " + round + " ---");
            showStatus(player, enemy);

            playerTurn(player, enemy);
            if (!enemy.isAlive()) break;

            enemyTurn(enemy, player);
            if (!player.isAlive()) break;

            round++;
        }

        boolean playerWon = player.isAlive();
        System.out.println("\n" + (playerWon ? "🏆 VICTORY!" : "💀 DEFEAT"));
        return playerWon;
    }

    private void showStatus(Hero p1, Hero p2) {
        String p1Poison = p1.getPoisonStacks() > 0 ? " ☠️(" + p1.getPoisonStacks() + ")" : "";
        String p2Poison = p2.getPoisonStacks() > 0 ? " ☠️(" + p2.getPoisonStacks() + ")" : "";

        System.out.printf("❤️  %s: %d/%d HP | 🔷 %d/%d MP%s\n",
                p1.getName(), p1.getHealth(), p1.getMaxHealth(), p1.getMana(), p1.getMaxMana(), p1Poison);
        System.out.printf("❤️  %s: %d/%d HP | 🔷 %d/%d MP%s\n",
                p2.getName(), p2.getHealth(), p2.getMaxHealth(), p2.getMana(), p2.getMaxMana(), p2Poison);
    }

    private void playerTurn(Hero player, Hero enemy) {
        System.out.println("\n🎲 Your turn:");
        System.out.println("1. Attack");
        System.out.println("2. Change attack");
        System.out.println("3. Change defense");
        System.out.println("4. Ultimate");
        System.out.printf("❤️  Health: %d/%d | 🔷 Mana: %d/%d\n",
                player.getHealth(), player.getMaxHealth(),
                player.getMana(), player.getMaxMana());

        int choice;
        while (true) {
            try {
                System.out.print("Choose action (1-4): ");
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 4) break;
                System.out.println("Only 1-4!");
            } catch (Exception e) {
                System.out.println("Enter a number!");
                scanner.next();
            }
        }

        if (choice == 1) {
            player.attack(enemy);
        } else if (choice == 2) {
            changeAttack(player);
        } else if (choice == 3) {
            changeDefense(player);
        } else if (choice == 4) {
            player.useUltimate(enemy);
        }

        waitForEnter();
    }

    private void enemyTurn(Hero enemy, Hero player) {
        System.out.println("\n🤖 Enemy turn...");

        if (enemy.getMana() >= getUltimateCost(enemy) && Math.random() < 0.3) {
            enemy.useUltimate(player);
        } else if (enemy.getHealth() < enemy.getMaxHealth() * 0.4 && Math.random() < 0.4) {
            if (enemy instanceof Mage) {
                enemy.setDefense(new MagicBarrier());
            } else if (enemy instanceof Assassin) {
                enemy.setDefense(new DodgeDefense());
            } else {
                enemy.setDefense(new ShieldBlock());
            }
            enemy.attack(player);
        } else if ((enemy instanceof Mage || enemy instanceof Assassin) &&
                Math.random() < 0.25 && enemy.getMana() > 15) {
            player.addPoison(1);
            enemy.useMana(15);
        } else {
            enemy.attack(player);
        }

        waitForEnter();
    }

    private int getUltimateCost(Hero hero) {
        if (hero instanceof Warrior) return 30;
        if (hero instanceof Mage) return 60;
        if (hero instanceof Archer) return 35;
        if (hero instanceof Assassin) return 45;
        if (hero instanceof Boss) return 50;
        return 40;
    }

    private void changeAttack(Hero hero) {
        System.out.println("\n🎯 Choose attack:");
        System.out.println("1. Melee - reliable, no mana");
        System.out.println("2. Ranged - high damage, crit chance");
        System.out.println("3. Magic - powerful, needs mana");

        int choice;
        while (true) {
            try {
                System.out.print("Your choice (1-3): ");
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) break;
                System.out.println("Only 1-3!");
            } catch (Exception e) {
                System.out.println("Enter a number!");
                scanner.next();
            }
        }

        if (choice == 1) {
            hero.setAttack(new MeleeAttack());
        } else if (choice == 2) {
            hero.setAttack(new RangedAttack());
        } else if (choice == 3) {
            hero.setAttack(new MagicAttack());
        }
    }

    private void changeDefense(Hero hero) {
        System.out.println("\n🛡️  Choose defense:");
        System.out.println("1. Shield - reliable damage block");
        System.out.println("2. Dodge - chance to avoid damage");
        System.out.println("3. Magic Barrier - best against magic");

        int choice;
        while (true) {
            try {
                System.out.print("Your choice (1-3): ");
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) break;
                System.out.println("Only 1-3!");
            } catch (Exception e) {
                System.out.println("Enter a number!");
                scanner.next();
            }
        }

        if (choice == 1) {
            hero.setDefense(new ShieldBlock());
        } else if (choice == 2) {
            hero.setDefense(new DodgeDefense());
        } else if (choice == 3) {
            hero.setDefense(new MagicBarrier());
        }
    }

    private void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }
}