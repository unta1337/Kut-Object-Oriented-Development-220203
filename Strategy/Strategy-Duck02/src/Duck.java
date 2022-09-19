/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기 
 * @author 김상진
 * 전략 패턴: Head First Pattern 예제
 * Duck.java: 추상클래스, 오리
 * 전략 패턴 적용하기 전 버전
 * 버전 1에 비해 fly 행위 추가 - ISP 측면에서 바람직한 것인지
 */
public abstract class Duck {
	public void quack() {
		System.out.println("꽥꽥");
	}
	public void swim() {
		System.out.println("나 물에서 수영하고 있어");
	}
	public void fly() {
		System.out.println("난 하늘을 날고 있어");
	}
	public abstract void display();
}
