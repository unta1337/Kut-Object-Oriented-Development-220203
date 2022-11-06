import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김성녕
 * Lab 06. 명령 패턴
 * 도형 그리기 추상 클래스
 * Pane에 도형을 그리는 명령 클래스의 공통 기능을 구현한다.
 * 실질적인 도형을 정의하는 부분은 자식 클래스에서 구현한다.
 */
public abstract class DrawShapeCommand implements Command {
    protected Pane pane;
    protected Shape shape;
    protected double x;
    protected double y;

    public DrawShapeCommand(Pane pane, double x, double y) {
        this.pane = pane;
        this.x = x;
        this.y = y;
    }

    protected abstract Shape createShape();

    @Override
    public void execute() {
        shape = createShape();

		shape.setStroke(Color.BLACK);
		shape.setFill(null);
		shape.setStrokeWidth(5d);
		pane.getChildren().add(shape);
    }

    @Override
    public void undo() {
        pane.getChildren().remove(shape);
    }
}
