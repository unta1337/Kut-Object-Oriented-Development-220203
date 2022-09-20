/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기 
 * @author 김상진, 김성녕
 * @file: GameModel.java 
 * 묵찌바 게임에 필요한 데이터를 유지하고 게임 로직 제공
 */
public class GameModel {
	private ComputerPlayer computer 
		= new ComputerPlayer(new RandomStrategy());
		//= new ComputerPlayer(new LastHandBasedStrategy());
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
	
	public HandType getComputerCurrentHand() {
		return computer.getHand();
	}
	// 다음 컴퓨터 손 계산함
	public HandType getComputerNextHand() {
		HandType nextHand = computer.nextHand();
		prevUserHand = currUserHand;
		return nextHand;
	}
	
	// 묵찌바 게임 결과 판단
	// @return 묵찌바 게임의 결과
	public GameResult playMookJiBa() {
		GameResult GBBResult = playGawiBawiBo();

		GameResult res = GameResult.DRAW;
		if (isUserAttack) {
			switch (GBBResult) {
				case DRAW:
					res =  GameResult.USERWIN;
					break;
				case USERWIN:
					res = GameResult.DRAW;
					break;
				case COMPUTERWIN:
					res = GameResult.DRAW;
					break;
			}
		}
		else {
			switch (GBBResult) {
				case DRAW:
					res = GameResult.COMPUTERWIN;
					break;
				case USERWIN:
					res =  GameResult.DRAW;
					break;
				case COMPUTERWIN:
					res =  GameResult.DRAW;
					break;
			}
		}

		return res;
	}
	
	// 가위바위보 게임 결과 판단
	// @return 가위바위보 게임의 결과 
	public GameResult playGawiBawiBo() {
		int userHandInteger = currUserHand.ordinal();
		int computerHandInteger = computer.getHand().ordinal();

		int resultInteger = 3 + userHandInteger - computerHandInteger;
		resultInteger %= 3;

		if (userHandInteger == computerHandInteger)
			return GameResult.DRAW;
		else if (resultInteger == 2)
		{
			playingMookJiBa = true;
			isUserAttack = true;
			return GameResult.USERWIN;
		}

		playingMookJiBa = true;
		isUserAttack = false;
		return GameResult.COMPUTERWIN;
	}
}
