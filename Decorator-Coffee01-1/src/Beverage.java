import java.util.ArrayList;
import java.util.List;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김상진, 김성녕
 * @file Beverage.java
 * 장식패턴에서 장식대상 추상클래스
 * 기본 예제
 */
public abstract class Beverage {
	private String description = "이름없는 음료";

	protected Beverage() { }

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Beverage) || o instanceof CondimentDecorator)
			return false;

		return this.getCoffee() == ((Beverage) o).getCoffee();
	}

	public Beverage removeCondiment() {
		return this;
	}

	public String getDescription(){
		return description;
	}

	public Class<? extends Beverage> getCoffee() {
		return getClass();
	}

	public List<Class<? extends Beverage>> getDecorators() {
		return new ArrayList<>();
	}

	public void setDescription(String description){
		this.description = description;
	}

	public abstract int cost();
}
