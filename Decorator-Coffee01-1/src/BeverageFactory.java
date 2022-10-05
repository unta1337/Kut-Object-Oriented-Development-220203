import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김상진, 김성녕
 * @file BeverageFactory.java
 * Beverage 생성을 위한 Factory 클래스
 */
public class BeverageFactory {
    private static Map<String, Restriction> restrictionTable = new HashMap<>();

    static {
        restrictionTable.put("Milk", new Restriction());
        restrictionTable.put("Mocha", new Restriction());
        restrictionTable.put("Whip", new Restriction());
    }

    public static Beverage createCoffee(String coffee, String... list) throws Exception {
        if (!checkCondimentValidity(coffee, list))
            throw new IllegalArgumentException("Invalid condiment.");

        Beverage beverage = createBaseCoffee(coffee);
        beverage = decorateBaseCoffee(beverage, list);

        return beverage;
    }

    private static Beverage createBaseCoffee(String coffee) throws Exception {
        Class<? extends Beverage> coffeeClass = Class.forName(coffee).asSubclass(Beverage.class);
        if (coffeeClass.getSuperclass() != Beverage.class || coffeeClass == CondimentDecorator.class)
            throw new IllegalArgumentException("Must use Concrete Decoratee.");

        Constructor<? extends Beverage> coffeeConstructor = coffeeClass.getDeclaredConstructor();
        return (Beverage) coffeeConstructor.newInstance();
    }

    private static Beverage decorateBaseCoffee(Beverage beverage, String... list) throws Exception {
        if (list.length == 0)
            return beverage;

        Arrays.sort(list);

        for (String s : list) {
            Class<? extends CondimentDecorator> condimentClass = Class.forName(s).asSubclass(CondimentDecorator.class);

            Constructor<? extends CondimentDecorator> condimentConstructor = condimentClass.getConstructor(Beverage.class);
            beverage = (Beverage) condimentConstructor.newInstance(beverage);
        }

        return beverage;
    }

    public static void addAdditionRestriction(String condiment, int addMax) {
        restrictionTable.get(condiment).maxAddition = addMax;
    }

    public static void addCoffeeRestriction(String condiment, String coffee) {
        restrictionTable.get(condiment).exclusionList.add(coffee);
    }

    public static boolean checkCondimentValidity(String coffee, String... list) {
        List<String> condimentList = Arrays.asList(list);
        Set<String> condiments = new HashSet<>(condimentList);

        for (String condiment : condiments) {
            if (restrictionTable.get(condiment).exclusionList.contains(coffee))
                return false;

            int limit = restrictionTable.get(condiment).maxAddition;
            int count = Collections.frequency(condimentList, condiment);

            if (limit != 0 && count > limit)
                return false;
        }

        return true;
    }
}
