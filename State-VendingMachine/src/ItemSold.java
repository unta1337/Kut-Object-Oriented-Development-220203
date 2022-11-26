/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김성녕
 * 상태 패턴
 * 상품을 판매하는 상태
 */
public class ItemSold implements State {
    private VendingMachine vendingMachine;

    public ItemSold(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertCash(Currency currency, int amount) {
        System.out.println("Product is on its way! Please wait for a second.");
    }

    @Override
    public void selectItem(Item item) {
        System.out.println("Product is on its way! Please wait for a second.");
    }

    @Override
    public void cancel() {
        System.out.println("You've reached the point of no return. Just wait for the consequences.");
    }

    @Override
    public void dispenseItem(Item item) {
        System.out.println("Product is on its way! Please wait for a second.");

        // 상품 배출.
        vendingMachine.removeItem(item);

        // 거스름 처리.
        CashRegister newUserCashRegister = vendingMachine.getChange(vendingMachine.getInsertedBalance() - item.price);
        vendingMachine.setUserCashRegister(newUserCashRegister);

        // 더 이상 구매 가능한 상품이 없으면 상태 전환.
        if (!vendingMachine.canBuyAnyItem()) {
            vendingMachine.returnChange();
            return;
        }

        // 계속해서 구매 가능하면 동전 삽입 상태로 전환.
        vendingMachine.setState(vendingMachine.getCoinInserted());
    }
}