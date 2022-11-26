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
        vendingMachine.getCachRegister().add(currency, amount);;
        //vendingMachine.getUserCachRegister().add(currency, amount);;
        vendingMachine.addAmount(currency, amount);
    }

    @Override
    public void selectItem(Item item) throws ChangeNotAvailableException {
        if (!vendingMachine.canBuyItem(item)) {
            if (!vendingMachine.canBuyAnyItem())
                vendingMachine.returnChange();

            throw new ChangeNotAvailableException(!vendingMachine.canBuyAnyItem());
        }

        vendingMachine.setState(vendingMachine.getItemSold());
    }

    @Override
    public void cancel() {
        System.out.println("hiiii");
        vendingMachine.returnChange();
        vendingMachine.setState(vendingMachine.getCoinEmpty());
    }

    @Override
    public void dispenseItem(Item item) {
        System.out.println("Please select a item first.");
    }
}