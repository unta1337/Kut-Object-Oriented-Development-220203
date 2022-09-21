/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김성녕
 * @file BiasStrategy.java
 * 전략패턴: 구체적인 전략 클래스
 * user가 이전에 낸 손 중에서 가장 비율이 높은 손을 다시 낼 것으로 기대하는 전략
 */
public class BiasStrategy implements PlayingStrategy {
    @Override
    public HandType computeNextHand(GameModel model) {
        return null;
    }
    
}
