import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김성녕
 * Lab 06. 명령 패턴
 * 도형 색 채우기 클래스
 */
public class FillShapeCommand implements Command {
    protected Shape shape;
    protected Color prevColor;
    protected Color newColor;

    public FillShapeCommand(Shape shape, Color newColor) {
        this.shape = shape;
        this.prevColor = (Color) shape.getFill();
        this.newColor = newColor;
    }

    @Override
    public void execute() {
        shape.setFill(newColor);
    }

    @Override
    public void undo() {
        shape.setFill(prevColor);
    }
}
