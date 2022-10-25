import java.util.concurrent.ThreadLocalRandom;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기 
 * @author 김성녕
 * @file LastHandBasedStrategy.java
 * 전략패턴: 구체적인 전략 클래스
 * user의 이전 손을 기준으로 공격 및 방어 시의 손을 능동적으로 선택
 */
public class LastHandBasedStrategy implements PlayingStrategy {
    @Override
    public HandType computeNextHand(GameModel model) {
        double determineHandDouble = ThreadLocalRandom.current().nextDouble();

        int prevUserHandInteger = model.prevUserHand() != null ? model.prevUserHand().ordinal() : -1;
        if (prevUserHandInteger == -1) {
            return HandType.valueOf(ThreadLocalRandom.current().nextInt(HandType.values().length));
        }

        int handIndex = 0;
        if (determineHandDouble < 0.2) {
            // # user는 20%의 확률로 이전에 낸 손과 동일한 손을 낸다, 즉 경우의 수가 1.
            // 수비 상황일 경우 상황 역전을 위해 user가 낸 손을 이기는 손을 채택.
            // 공격 상황일 경우 승리를 위해 user가 낸 손을 채택.
            handIndex = model.isUserAttack()
                        ? (prevUserHandInteger + 2) % 3
                        : prevUserHandInteger;
        }
        else {
            // # user는 80%의 확률로 이전에 낸 손과 다른 손을 낸다, 즉 경우의 수가 2.
            // 수비 상황일 경우 해당 턴에서 지는 것을 막기 위해 (user가 낼 것으로 기대되는) 이전 턴에서 user가 내지 않은 손 중에서 채택.
            // 공격 상황일 경우 해당 턴에서 이기기 위해 (user가 낼 것으로 기대되는) 이전 턴에서 user가 내지 않은 손 중에서 채택.
            // 따라서 수비와 공격에 상관없이 이전에 user가 내지 않은 손 중에서 무작위로 채택한다.
            handIndex = (ThreadLocalRandom.current().nextInt(2) + prevUserHandInteger + 1) % 3;
        }

        return HandType.valueOf(handIndex);
    }
    
}
