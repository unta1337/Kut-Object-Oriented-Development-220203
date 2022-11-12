import java.util.List;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김상진, 김성녕
 * @file BlackJackPlayerHand.java
 * 탬플릿 메소드 패턴
 * 블랙잭 게임에서 각 플레이어의 패 정보 유지
 */
public class BlackJackPlayerHand {
	private List<Card> cards;
	private int score = 0;
	private boolean isBlackJack = false;
	public BlackJackPlayerHand(List<Card> cards) {
		this.cards = cards;
		score = computeScore();
	}
	public void init() {
		cards.clear();
		score = 0;
		isBlackJack = false;
	}
	public List<Card> getCards(){
		return cards;
	}
	public void addCard(Card card) {
		cards.add(card);
		score = computeScore();
	}
	public int getScore() {
		return score;
	}
	public boolean isBlackJack() {
		return isBlackJack;
	}
	private int computeScore() {
		isBlackJack = false;		// 점수 계산 전 블랙잭 여부 초기화.

		// ACE를 제외한 카드의 합계 계산.
		// ACE는 1000번대로 표현.
		int sum = cards.stream().mapToInt(card->card.number()).map(number -> switch (number) {
			case 1 -> 1000;
			case 11, 12, 13 -> 10;
			default -> number;
		}).sum();

		// ACE 관련 점수 계산.
		int aceCount = sum / 1000;
		sum %= 1000;

		// ACE 미포함 시의 점수가 이미 21점을 초과하거나 ACE가 없으면 기존 점수 + ACE 점수(1점으로 계산).
		// 전자의 경우 ACE를 1점으로 계산하여 최대한 21에 가깝게 설정.
		// 후자의 경우 ACE가 없으므로 aceCount 변수가 점수에 영향을 주지 않음.
		if (sum > 21 || aceCount == 0)
			return sum + aceCount;

		// 점수가 21점 미만인 선에서 ACE를 1 또는 11점으로 계산하여 최대한 21에 가깝게 설정.
		while (aceCount > 0 && sum < 21) {
			aceCount--;
			
			// 남은 ACE를 1점으로 가정하여 21점을 넘지 않는 선에서 점수 계산.
			if (sum + 11 + aceCount <= 21) sum += 11;
			else if (sum + 1 + aceCount <= 21) sum += 1;
		}
		sum += aceCount;

		isBlackJack = sum == 21;

		return sum;
	}
	public static BlackJackGameResult determineResult(BlackJackPlayerHand userHand, BlackJackPlayerHand dealerHand) {
		int dealerScore = dealerHand.getScore();
		int userScore = userHand.getScore();

		// 둘 다 Bust.
		if (dealerScore > 21 && userScore > 21) return BlackJackGameResult.DRAW;

		// 둘 중 한 명만 Bust.
		else if (dealerScore > 21) return BlackJackGameResult.USERWIN;
		else if (userScore > 21) return BlackJackGameResult.USERLOST;

		// 둘 다 21.
		else if (dealerScore == userScore && dealerScore == 21) {
			if (dealerHand.isBlackJack() && userHand.isBlackJack()) return BlackJackGameResult.DRAW;
			else return dealerHand.isBlackJack() ? BlackJackGameResult.USERLOST : BlackJackGameResult.USERWIN;
		}

		// 둘 다 Bust가 아닌 경우.
		else return dealerScore >= userScore ? BlackJackGameResult.USERLOST: BlackJackGameResult.USERWIN;
	}
}
