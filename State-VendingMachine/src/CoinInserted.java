/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김성녕
 * 상태 패턴
 * 동전이 삽입된 상태
 */
public class CoinInserted implements State {
    private VendingMachine vendingMachine;

    public CoinInserted(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertCash(Currency currency, int amount) {
        System.out.println("Insert a coin into the machine.");

        // 추가 동전 삽입.
        vendingMachine.addCash(currency, amount);
    }

    @Override
    public void selectItem(Item item) throws ChangeNotAvailableException {
        System.out.println("You've seleted " + item.toString() + ".");

        // 상품 구매할 수 없을 때 처리.
        if (!vendingMachine.canBuyItem(item)) {
            // 다른 구매 가능한 상품이 없으면 거스름 반환.
            if (!vendingMachine.canBuyAnyItem())
                vendingMachine.returnChange();

            // 거스름 관련 예외 발생.
            throw new ChangeNotAvailableException(!vendingMachine.canBuyAnyItem());
        }

        vendingMachine.setState(vendingMachine.getItemSold());
    }

    @Override
    public void cancel() {
        System.out.println("Item selection canceled.");

        // 상품 구매 취소 시 거스름 반환 및 상태 전환.
        vendingMachine.returnChange();
        vendingMachine.setState(vendingMachine.getCoinEmpty());
    }

    @Override
    public void dispenseItem(Item item) {
        System.out.println("Please select a item first.");
    }
}