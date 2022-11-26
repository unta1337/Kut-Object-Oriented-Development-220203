/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김성녕
 * 상태 패턴
 * 동전이 없는 상태
 */
public class CoinEmpty implements State {
    private VendingMachine vendingMachine;

    public CoinEmpty(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertCash(Currency currency, int amount) {
        System.out.println("Insert a coin into the machine.");
        
        // 동전 삽입 후 대기 상태(동전이 삽입되어 상품 선택을 기다리는 상태)로 전환.
        vendingMachine.addCash(currency, amount);
        vendingMachine.setState(vendingMachine.getCoinInserted());
    }

    @Override
    public void selectItem(Item item) {
        System.out.println("Please insert a coin first.");
    }

    @Override
    public void cancel() {
        System.out.println("There's nothing to cancel.");
    }

    @Override
    public void dispenseItem(Item item) {
        System.out.println("Please insert a coin first.");
    }
}