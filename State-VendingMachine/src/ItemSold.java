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
        System.out.println("Product on its way! Please wait for a second.");
    }

    @Override
    public void selectItem(Item item) {
        System.out.println("Product on its way! Please wait for a second.");
    }

    @Override
    public void cancel() {
        System.out.println("You've reached the point of no return. Just wait for the consequences.");
    }

    @Override
    public void dispenseItem(Item item) {
        System.out.println("Product on its way! Please wait for a second.");

        vendingMachine.getInventoryStock().removeItem(item);
        //vendingMachine.getUserCachRegister().remove(null, 0);
        vendingMachine.addAmount(-item.price);

        if (!vendingMachine.canBuyAnyItem()) {
            vendingMachine.returnChange();
            vendingMachine.setState(vendingMachine.getItemEmpty());
            return;
        }

        vendingMachine.setState(vendingMachine.getCoinInserted());
    }
}