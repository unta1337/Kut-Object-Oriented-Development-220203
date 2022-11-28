/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김상진, 김성녕
 * 상태 패턴: 문맥 기반 전이 방식
 * 문
 */
public class Door {
	private DoorState currentState = DoorState.CLOSED;

	public DoorState getState() {
		return currentState;
	}

	public void SetState(DoorState state) {
		currentState = state;
	}

	public void open(){
		currentState.open(this);
	}

	public void close(){
		currentState.close(this);
	}

	public void lock(){
		currentState.lock(this);
	}

	public void unlock(){
		currentState.unlock(this);
	}
}
