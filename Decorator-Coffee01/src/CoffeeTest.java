/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김상진
 * @file CoffeeTest.java
 * 테스트 프로그램
 */
public class CoffeeTest {
	public static void main(String[] args) {
		Beverage beverage = new HouseBlend();
		printBeverage(beverage);

		beverage = new Mocha(beverage);
		printBeverage(beverage);

		beverage = new Whip(beverage);
		printBeverage(beverage);

		beverage = new Mocha(beverage);
		printBeverage(beverage);

		System.out.println("-----");
		
		beverage = new DarkRoast();
		printBeverage(beverage);

		beverage = new Mocha(beverage);
		printBeverage(beverage);

		beverage = new Milk(beverage);
		printBeverage(beverage);
	}

	public static void printBeverage(Beverage beverage) {
		System.out.printf("%s: %,d원%n", beverage.getDescription(), beverage.cost());
	}
}
