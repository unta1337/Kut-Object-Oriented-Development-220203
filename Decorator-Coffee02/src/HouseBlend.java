/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2021년도 2학기
 * @author 김상진
 * @file HouseBlend.java
 * 하우스블랜드 커피
 */
public class HouseBlend extends Beverage {
	public HouseBlend(){
		setDescription("하우스블랜드 커피");
	}
	@Override
	public int cost() {
		return 1000;
	}
}
