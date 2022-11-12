import java.util.ArrayList;
import java.util.List;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김상진, 김성녕
 * @file OneCardGame.java
 * 탬플릿 메소드 패턴
 * 원카드 게임: 7장씩 나누어 줌, 2장의 조커 포함
 */
public class OneCardGame extends CardGame {
	public OneCardGame() {
		super(7);
	}
	@Override
	protected void dealCards() {
		for(int i=0; i<numberOfPlayers; i++) {
			List<Card> userCard = new ArrayList<>();
			for(int j=0; j<NUMBEROFCARDSPERPLAYER; j++)
				userCard.add(getCard());
			userCards.add(userCard);
		}
	}
}
