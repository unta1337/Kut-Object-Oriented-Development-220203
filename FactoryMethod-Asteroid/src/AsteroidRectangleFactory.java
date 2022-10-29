import java.util.concurrent.ThreadLocalRandom;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기 
 * @author 김성녕
 * 팩토리 메소드 패턴: Asteroid
 * AsteriodRectangleFactory.java: 직사각형 모양의 Asteroid을 생성하는 클래스
 */
public class AsteroidRectangleFactory extends AsteroidFactory {
    @Override
    protected Double[] createPoints(Location centerLoc, int radius) {
		Double[] points = new Double[8];

		int width = radius;
		int height = radius;

		if (ThreadLocalRandom.current().nextBoolean()) 
			width += ThreadLocalRandom.current().nextInt(radius);
		if (ThreadLocalRandom.current().nextBoolean()) 
			height += ThreadLocalRandom.current().nextInt(radius);

		// 기존에는 마름모꼴로 점을 생성했지만, 이번에는 직사각형으로 점을 생성한다.
		points[0] = Double.valueOf(centerLoc.x() + width);
		points[1] = Double.valueOf(centerLoc.y() - height);
		points[2] = Double.valueOf(centerLoc.x() + width);
		points[3] = Double.valueOf(centerLoc.y() + height);
		points[4] = Double.valueOf(centerLoc.x() - width);
		points[5] = Double.valueOf(centerLoc.y() + height);
		points[6] = Double.valueOf(centerLoc.x() - width);
		points[7] = Double.valueOf(centerLoc.y() - height);

		return points;
    }
}
