import java.util.List;
import java.util.ArrayList;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김성녕
 * @file BiasStrategy.java
 * 전략패턴: 구체적인 전략 클래스
 * user가 이전에 낸 손 중에서 가장 비율이 높은 손을 다시 낼 것으로 기대하는 전략
 * 
 * user는 사람이므로 무의식적으로 자신이 선호하는 손만 계속 낼 수 있다.
 * 이를 파고들어 이전에 낸 손 중에서 가장 비율이 높은 손을 다시 낼 것으로 기대하는 전략이다.
 * 
 * (user, comp): (묵, 찌), (찌, 묵), (묵, 빠), (빠, 찌), (묵, 찌), ...
 * 위와 같은 순서로 게임이 진행되었을 때, user가 가장 많이 낸 손은 묵이다.
 * 이를 통해 user가 묵을 내는 것을 선호한다고 가정, 이를 이용해 공격 및 수비 전략을 세운다.
 * 
 * user가 낸 손의 비율이 실제로 선호도를 따라간다면 꽤 높은 확률로 user의 다음 손을 예측할 수 있다.
 * 하지만 이전 손에 대한 데이터가 부족하거나 이로 인해 데이터가 편향된다면 되려 역으로 당할 가능성이 있다.
 * 따라서 최초 1회에 한에서만 무작위 손을 선택하는 것이 아닌, 어느 정도 데이터가 쌓이기 전까지 무작위 선택을 할 수도 있다.
 */
public class BiasStrategy implements PlayingStrategy {
    // 과거 user의 손을 기록하기 위한 변수.
    private List<HandType> handHistory = new ArrayList<>();

    @Override
    public HandType computeNextHand(GameModel model) {
        // 최초 1회에 한해 user에 대한 정보가 없으므로 무작위 손 선택.
        if (model.prevUserHand() == null) {
            return HandType.values()[(int) (Math.random() * HandType.values().length)];
        }

        // user가 낸 손 기록.
        // currUserHand가 아닌 prevUserHand를 이용해 이를 기록한다.
        // currUserHand가 MOOK으로 명시적 초기화가 돼 있어 최초 상황을 구분하기 어렵기 때문.
        handHistory.add(model.prevUserHand());

        // 각 손에 대한 과거의 비율 계산.
        double mookRatio = (double) handHistory.stream().mapToInt(elem -> elem == HandType.MOOK ? 1 : 0).sum() / handHistory.size();
        double jiRatio = (double) handHistory.stream().mapToInt(elem -> elem == HandType.JI ? 1 : 0).sum() / handHistory.size();
        double baRatio = (double) handHistory.stream().mapToInt(elem -> elem == HandType.BA ? 1 : 0).sum() / handHistory.size();

        // 과거의 손 비율을 바탕으로 다음 손으로 기대되는 손에 대해 전략 수행.
        double probability = 0;
        double randomValue = Math.random();
        int handIndex = 0;

        // 공격 상황이 아닐 때에는 user가 내지 않을 것으로 기대되는 손으로 방어.
        // 공격 상황일 때에는 user가 낼 것으로 기대되는 손으로 공격.
        if (randomValue < (probability += mookRatio)) {
            handIndex = model.isUserAttack() ? ((int) (Math.random() * 2) + HandType.MOOK.ordinal() + 1) % 3 : HandType.MOOK.ordinal();
        } else if (randomValue < (probability += jiRatio)) {
            handIndex = model.isUserAttack() ? ((int) (Math.random() * 2) + HandType.JI.ordinal() + 1) % 3 : HandType.JI.ordinal();
        } else if (randomValue < (probability += baRatio)) {
            handIndex = model.isUserAttack() ? ((int) (Math.random() * 2) + HandType.BA.ordinal() + 1) % 3 : HandType.BA.ordinal();
        }

        return HandType.values()[handIndex];
    }
    
}
