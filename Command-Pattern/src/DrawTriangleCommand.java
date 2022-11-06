import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김성녕
 * Lab 06. 명령 패턴
 * 정삼각형 그리기 클래스
 */
public class DrawTriangleCommand extends DrawShapeCommand {
    public DrawTriangleCommand(Pane pane, double x, double y) {
        super(pane, x, y);
    }

    @Override
    protected Shape createShape() {
        int r = DrawShapeApp.RADIUS;

        Polygon triangle = new Polygon();
        final double radian = Math.PI / 180F;
        triangle.getPoints().addAll(new Double[] {
            x + r * Math.cos(30 * radian),
            y + r * Math.sin(30 * radian),
            x + r * Math.cos(150 * radian),
            y + r * Math.sin(150 * radian),
            x + r * Math.cos(270 * radian),
            y + r * Math.sin(270 * radian)
        });

        return triangle;
    }
}
