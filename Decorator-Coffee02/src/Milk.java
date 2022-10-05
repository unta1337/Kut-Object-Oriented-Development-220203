/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김상진
 * @file Milk.java
 * 우유 첨가물
 */
public class Milk implements Condiment {
	@Override
	public String getDescription() {
		return "우유";
	}
	@Override
	public int cost() {
		return 500;
	}
}
