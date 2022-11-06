import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김성녕
 * Lab 06. 명령 패턴
 * 도형 지우기 명령 클래스
 */
public class DeleteShapeCommand implements Command {
    protected Pane pane;
    protected Shape shape;

    public DeleteShapeCommand(Pane pane, Shape shape) {
        this.pane = pane;
        this.shape = shape;
    }

    @Override
    public void execute() {
        pane.getChildren().remove(shape);
    }

    @Override
    public void undo() {
        pane.getChildren().add(shape);
    }
}
