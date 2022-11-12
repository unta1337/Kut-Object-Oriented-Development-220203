import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김상진, 김성녕
 * @file CardGame.java 
 * 탬플릿 메소드 패턴
 */
public abstract class CardGame {
	public static final int TOTALNUMBEROFCARDS = 52;
	public final int NUMBEROFCARDSPERPLAYER;
	private List<Card> originalDeck 
		= new ArrayList<>(TOTALNUMBEROFCARDS);
	protected Queue<Card> remainingDeck = new ArrayDeque<>();
	protected List<List<Card>> userCards = new ArrayList<>();
	protected int numberOfPlayers;
	
	public CardGame(int numberOfCardsPerPlayer) {
		if(numberOfCardsPerPlayer<0||numberOfCardsPerPlayer>TOTALNUMBEROFCARDS)
			throw new IllegalArgumentException("사용자마다 나누어 주는 카드 수가 잘못 설정");
		NUMBEROFCARDSPERPLAYER = numberOfCardsPerPlayer;
	}
	
	// 탬플릿 메소드
	public final void init(int numberOfPlayers){
		originalDeck.clear();
		remainingDeck.clear();
		userCards.clear();
		CardFace[] cardFaces = CardFace.values();
		for(int i=0; i<TOTALNUMBEROFCARDS; i++){
			originalDeck.add(new Card( i % 13 + 1, cardFaces[i/13]));
		}
		if(needJokerCards()) {
			originalDeck.add(new Card(0, CardFace.SPADES));
			originalDeck.add(new Card(0, CardFace.DIAMONDS));
		}
		shuffle();
		remainingDeck.addAll(originalDeck);
		setNumberOfPlayers(numberOfPlayers);
		dealCards();
	}
	// hook: 원하면 섞는 알고리즘을 변경할 수 있음
	protected void shuffle(){
		// Fisher-Yates Shuffle
		/*
		 * 매 반복마다 모든 인덱스를 고려하면 일부 선지에 편향된다.
		 * e.g. 3개를 정렬하는 경우의 수 = 3! = 6
		 * 	    매 반복마다 모든 인덱스를 고려하는 경우의 수 = 3^3 = 27
		 * 		27은 6의 배수가 아니므로 확률 분포가 고르지 않다.
		 * 
		 * 		매 반복마다 이미 결정된 인덱스를 제외하면 이를 방지할 수 있다.
		 */
		for (int i = 0; i < originalDeck.size(); i++) {
			int randomIndex = ThreadLocalRandom.current().nextInt(i, originalDeck.size());
			Card temp = originalDeck.get(i);
			originalDeck.set(i, originalDeck.get(randomIndex));
			originalDeck.set(randomIndex, temp);
		}
	}
	// hook: 원하면 조커 카드를 추가할 수 있음
	protected boolean needJokerCards() {
		return false;
	}
	// Concrete Method
	private void setNumberOfPlayers(int numberOfPlayers) {
		if(numberOfPlayers<=0 || numberOfPlayers>=TOTALNUMBEROFCARDS/NUMBEROFCARDSPERPLAYER)
			throw new IllegalArgumentException("플레이어 수가 적절하지 않음");
		this.numberOfPlayers = numberOfPlayers;
	}
	// Abstract Method
	// 카드를 각 플레이어에게 나누어주기
	protected abstract void dealCards();
	public Card getCard() {
        return remainingDeck.poll();
    }
	public List<List<Card>> getUserCards(){
		return userCards;
	}
	// debug용
	public void display() {
		for(var cards: userCards) {
			for(var card: cards) {
				System.out.print(card+", ");
			}
			System.out.println();
		}
	}
}
