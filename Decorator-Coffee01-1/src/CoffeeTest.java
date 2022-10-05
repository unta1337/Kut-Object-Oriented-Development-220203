/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김상진, 김성녕
 * @file CoffeeTest.java
 * 테스트 프로그램
 */
public class CoffeeTest {
	public static void main(String[] args) throws Exception {
		BeverageFactory.addAdditionRestriction("Whip", 1);
		BeverageFactory.addAdditionRestriction("Milk", 1);
		BeverageFactory.addAdditionRestriction("Mocha", 2);
		BeverageFactory.addCoffeeRestriction("Mocha", "DarkRoast");

		Beverage beverage = new HouseBlend();
		printBeverage(beverage);

		beverage = new Milk(beverage);
		beverage = new Mocha(beverage);
		beverage = new Whip(beverage);
		printBeverage(beverage);

		System.out.println("Try to add more Milk...");
		try {
			beverage = new Milk(beverage);
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("=====");

		beverage = BeverageFactory.createCoffee("DarkRoast", "Milk", "Whip");
		printBeverage(beverage);

		System.out.println("Try to create coffee with two milk...");
		try {
			beverage = BeverageFactory.createCoffee("DarkRoast", "Milk", "Whip", "Milk");
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("=====");

		System.out.println("Try to create DarkRoast with Mocha...");

		beverage = new DarkRoast();
		printBeverage(beverage);

		try {
			beverage = new Mocha(beverage);
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("=====");

		Beverage b1 = new HouseBlend();
		b1 = new Milk(b1);
		b1 = new Mocha(b1);

		Beverage b2 = BeverageFactory.createCoffee("HouseBlend", "Mocha", "Milk");

		printBeverage(b1);
		printBeverage(b2);
		System.out.println("Equals: " + b1.equals(b2));

		System.out.println("=====");

		b1 = new Whip(b1);

		printBeverage(b1);
		printBeverage(b2);
		System.out.println("Equals: " + b1.equals(b2));
	}

	public static void printBeverage(Beverage beverage) {
		System.out.printf("%s: %,d원%n", beverage.getDescription(), beverage.cost());
	}
}
