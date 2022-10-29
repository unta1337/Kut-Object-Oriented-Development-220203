import java.util.concurrent.ThreadLocalRandom;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기 
 * @author 김성녕
 * 팩토리 메소드 패턴: Asteroid
 * AsteriodPolygonFactory.java: 다각형 모양의 Asteroid을 생성하는 클래스
 */
public class AsteroidPolygonFactory extends AsteroidFactory {
    @Override
    protected Double[] createPoints(Location centerLoc, int radius) {
		// 10~15 이내의 점을 이용해 다각형 생성.
		int numPoints = ThreadLocalRandom.current().nextInt(10, 16);
		Double[] points = new Double[numPoints * 2];

		// 각 점의 좌표를 반지름을 기준으로 랜덤하게 생성한다.
		for (int i = 0; i < numPoints; i++) {
			double r = ThreadLocalRandom.current().nextDouble(radius, radius * 1.5);
			double a = i * Math.PI * 2 / numPoints;

			double x = centerLoc.x() + r * Math.cos(a);
			double y = centerLoc.y() + r * Math.sin(a);

			points[2 * i] = x;
			points[2 * i + 1] = y;
		}

		return points;
    }
}
