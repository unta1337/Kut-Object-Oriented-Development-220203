/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김성녕
 * Lab 06. 명령 패턴
 * 명령 패턴 인터페이스
 */
public interface Command {
    void execute();
    void undo();
}
