/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김성녕
 * 상태 패턴
 * 상태 인터페이스
 */
public interface State {
    public abstract void insertCash(Currency currency, int amount);
    public abstract void selectItem(Item item) throws ChangeNotAvailableException;
    public abstract void cancel();
    public abstract void dispenseItem(Item item);
}
