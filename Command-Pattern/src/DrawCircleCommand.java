import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김성녕
 * Lab 06. 명령 패턴
 * 원 그리기 명령 클래스
 */
public class DrawCircleCommand extends DrawShapeCommand {
    public DrawCircleCommand(Pane pane, double x, double y) {
        super(pane, x, y);
    }

    @Override
    protected Shape createShape() {
        int r = DrawShapeApp.RADIUS;
        return new Circle(x, y, r);
    }
}
