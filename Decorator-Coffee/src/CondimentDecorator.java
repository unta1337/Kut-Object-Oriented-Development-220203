import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김상진, 김성녕
 * @file CondimentDecorator.java
 * 장식패턴에서 장식자 추상 클래스
 */
public abstract class CondimentDecorator extends Beverage {
	protected final Beverage beverage;

	protected CondimentDecorator(Beverage beverage) {
		List<String> condiments = new ArrayList<>(Arrays.asList(getClass().toString().substring(6)));
		beverage.getDecorators().forEach(d -> condiments.add(d.toString().substring(6)));

		String[] condimentArray = new String[condiments.size()];
		condiments.toArray(condimentArray);

		if (!BeverageFactory.checkCondimentValidity(beverage.getCoffee().toString().substring(6), condimentArray))
			throw new IllegalArgumentException("Invalid condiment.");

		this.beverage = beverage;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof CondimentDecorator))
			return false;

		CondimentDecorator other = (CondimentDecorator) o;

		Map<Class<? extends Beverage>, Integer> thisCondiemnts = new HashMap<>();
		Map<Class<? extends Beverage>, Integer> otherCondiemnts = new HashMap<>();

		getDecorators().forEach(d -> thisCondiemnts.put(d, thisCondiemnts.containsKey(d) ? thisCondiemnts.get(d) + 1 : 1));
		other.getDecorators().forEach(d -> otherCondiemnts.put(d, otherCondiemnts.containsKey(d) ? otherCondiemnts.get(d) + 1 : 1));

		return (getCoffee() == other.getCoffee()) && thisCondiemnts.equals(otherCondiemnts);
	}

	@Override
	public Beverage removeCondiment() {
		return beverage;
	}

	@Override
	public Class<? extends Beverage> getCoffee() {
		return beverage.getCoffee();
	}

	@Override
	public List<Class<? extends Beverage>> getDecorators() {
		List<Class<? extends Beverage>> ret = new ArrayList<>(Arrays.asList(getClass()));
		beverage.getDecorators().forEach(d -> ret.add(d));

		return ret;
	}

	public abstract String getDescription();
}
