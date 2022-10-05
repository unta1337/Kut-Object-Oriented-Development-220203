/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김상진
 * @file Mocha.java
 * 장식패턴에서 구체적인 장식자 클래스
 * 모카 첨가물
 */
public class Mocha extends CondimentDecorator {
	private Beverage beverage;
	public Mocha(Beverage beverage){
		this.beverage = beverage;
	}
	@Override
	public String getDescription() {
		return beverage.getDescription()+", 모카";
	}
	@Override
	public int cost() {
		return beverage.cost()+200;
	}
}
