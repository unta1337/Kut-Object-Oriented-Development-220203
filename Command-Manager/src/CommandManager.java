import java.util.Stack;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김성녕
 * 명령 매니저 클래스
 */
public class CommandManager {
    private Stack<Command> commandStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    public void execute(Command command) {
        command.execute();
        commandStack.push(command);
        redoStack.clear();
    }

    /**
     * 가장 최근 명령 실행 취소
     * 
     * @return 추가로 취소 가능한 명령 존재 여부
     */
    public boolean undo() {
        if (commandStack.isEmpty())
            return false;

        Command command = commandStack.pop();
        command.undo();
        redoStack.push(command);

        if (commandStack.isEmpty())
            return false;
        return true;
    }

    /**
     * 가장 최근 명령 재실행
     * 
     * @return 추가로 재실행 가능한 명령 존재 여부
     */
    public boolean redo() {
        if (redoStack.isEmpty())
            return false;

        Command command = redoStack.pop();
        command.execute();
        commandStack.push(command);

        if (redoStack.isEmpty())
            return false;
        return true;
    }
}
