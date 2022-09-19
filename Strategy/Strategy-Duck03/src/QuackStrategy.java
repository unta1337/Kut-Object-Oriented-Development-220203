/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기 
 * @author 김상진 
 * 전략 패턴: Head First Pattern 예제
 * 전략 패턴: 전략 interface
 * QuackStrategy.java: 우는 전략
 */

@FunctionalInterface
public interface QuackStrategy {
	void doQuack();
}
