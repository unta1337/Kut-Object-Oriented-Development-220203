/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기 
 * @author 김상진, 김성녕
 * @file: GameModel.java 
 * 묵찌바 게임에 필요한 데이터를 유지하고 게임 로직 제공
 */
public class GameModel {
	private ComputerPlayer computer 
		// = new ComputerPlayer(new RandomStrategy());
		// = new ComputerPlayer(new LastHandBasedStrategy());
		= new ComputerPlayer(new BiasStrategy());
	@SuppressWarnings("unused")
	private HandType prevUserHand = null;
	private HandType currUserHand = HandType.MOOK;
	private boolean playingMookJiBa = false;
	private boolean isUserAttack = false;
	
	// 새 게임을 할 때마다 객체를 생성하는 대신 사용 (상태 초기화)
	public void init() {
		playingMookJiBa = false;
		isUserAttack = false;
	}
	
	// 기본 Getter
	public boolean isUserAttack() {
		return isUserAttack;
	}
	
	public boolean playingMookJiBa() {
		return playingMookJiBa;
	}
	
	public void setUserHand(HandType userHand) {
		currUserHand = userHand;
	}

	public HandType prevUserHand() {
		return prevUserHand;
	}
	
	public HandType getComputerCurrentHand() {
		return computer.getHand();
	}
	// 다음 컴퓨터 손 계산함
	public HandType getComputerNextHand() {
		HandType nextHand = computer.nextHand(this);
		prevUserHand = currUserHand;
		return nextHand;
	}
	
	// 묵찌바 게임 결과 판단
	// @return 묵찌바 게임의 결과
	public GameResult playMookJiBa() {
		GameResult GBBResult = playGawiBawiBo();

		// 가위바위보의 결과가 비겼다면, 공격한 쪽의 승리.
		if (GBBResult == GameResult.DRAW)
			return isUserAttack ? GameResult.USERWIN : GameResult.COMPUTERWIN;

		// 나머지 경우에 대해선 승자를 결정할 수 없음.
		return GameResult.DRAW;
	}
	
	// 가위바위보 게임 결과 판단
	// @return 가위바위보 게임의 결과 
	public GameResult playGawiBawiBo() {
		int userHandInteger = currUserHand.ordinal();
		int computerHandInteger = computer.getHand().ordinal();

		/*
		 * R S P R S P
		 * 0 1 2 3 4 5
		 * 
		 * (3 + user) - comp
		 * -> user가 승리일 때 위의 식은 2 또는 4.
		 * -> 이에 mod 3을 적용하면 1.
		 * => 위의 식에 mod 3를 적용한 값이 1이면 user 승.
		 */

		int resultInteger = 3 + userHandInteger - computerHandInteger;
		resultInteger %= 3;

		// 결과 기본값을 컴퓨터의 승으로 설정한 후, 조건문을 통해 최종 결과 도출.
		GameResult result = GameResult.COMPUTERWIN;

		if (resultInteger == 2)
			result = GameResult.USERWIN;
		else if (userHandInteger == computerHandInteger)
			result = GameResult.DRAW;

		// 각 상황에 맞는 플래그 갱신.
		playingMookJiBa = result != GameResult.DRAW;										// 상태에 상관없이 비기지 않으면 묵찌빠 상태로 넘어감.
		isUserAttack = playingMookJiBa ? result == GameResult.USERWIN : isUserAttack;		// 공격 상황 판단은 묵찌빠 상태로 넘어갈 때에만 유효함.

		// user의 이전 손 갱신.
		prevUserHand = currUserHand;

		return result;
	}
}
