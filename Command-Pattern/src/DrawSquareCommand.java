import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김성녕
 * Lab 06. 명령 패턴
 * 정사각형 그리기 클래스
 */
public class DrawSquareCommand extends DrawShapeCommand {
    public DrawSquareCommand(Pane pane, double x, double y) {
        super(pane, x, y);
    }

    @Override
    protected Shape createShape() {
        int r = DrawShapeApp.RADIUS;
        return new Rectangle(x - r, y - r, 2 * r, 2 * r);
    }
}
