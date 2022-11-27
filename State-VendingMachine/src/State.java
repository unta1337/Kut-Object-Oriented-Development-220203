public enum State {
    COIN_EMPTY,
    COIN_INSERTED,
    ITEM_EMPTY,
    ITEM_SOLD;

    public void insertCash(VendingMachine vendingMachine, Currency currency, int amount) {
        switch (this) {
            case COIN_EMPTY:
                System.out.println("Insert a coin into the machine.");
                
                // 동전 삽입 후 대기 상태(동전이 삽입되어 상품 선택을 기다리는 상태)로 전환.
                vendingMachine.addCash(currency, amount);
                vendingMachine.setState(COIN_INSERTED);
                break;
            case COIN_INSERTED:
                System.out.println("Insert a coin into the machine.");

                // 추가 동전 삽입.
                vendingMachine.addCash(currency, amount);
                break;
            case ITEM_EMPTY:
                System.out.println("Vending machine is empty.");
                break;
            case ITEM_SOLD:
                System.out.println("Product is on its way! Please wait for a second.");
                break;
        }
    }

    public void selectItem(VendingMachine vendingMachine, Item item) throws ChangeNotAvailableException {
        switch (this) {
            case COIN_EMPTY:
                System.out.println("Please insert a coin first.");
                break;
            case COIN_INSERTED:
                System.out.println("You've seleted " + item.name().toLowerCase() + ".");

                // 상품 구매할 수 없을 때 처리.
                if (!vendingMachine.canBuyItem(item)) {
                    // 다른 구매 가능한 상품이 없으면 거스름 반환.
                    if (!vendingMachine.canBuyAnyItem())
                        vendingMachine.returnChange();

                    // 거스름 관련 예외 발생.
                    throw new ChangeNotAvailableException(!vendingMachine.canBuyAnyItem());
                }

                vendingMachine.setState(ITEM_SOLD);
                break;
            case ITEM_EMPTY:
                System.out.println("Vending machine is empty.");
                break;
            case ITEM_SOLD:
                System.out.println("Product is on its way! Please wait for a second.");
                break;
        }
    }

    public void cancel(VendingMachine vendingMachine) {
        switch (this) {
            case COIN_EMPTY:
                System.out.println("There's nothing to cancel.");
                break;
            case COIN_INSERTED:
                System.out.println("Item selection canceled.");

                // 상품 구매 취소 시 거스름 반환 및 상태 전환.
                vendingMachine.returnChange();
                vendingMachine.setState(COIN_EMPTY);
                break;
            case ITEM_EMPTY:
                System.out.println("Vending machine is empty.");
                break;
            case ITEM_SOLD:
                System.out.println("You've reached the point of no return. Just wait for the consequences.");
                break;
        }
    }

    public void dispenseItem(VendingMachine vendingMachine, Item item) {
        switch (this) {
            case COIN_EMPTY:
                System.out.println("Please insert a coin first.");
                break;
            case COIN_INSERTED:
                System.out.println("Please select a item first.");
                break;
            case ITEM_EMPTY:
                System.out.println("Vending machine is empty.");
                break;
            case ITEM_SOLD:
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
                vendingMachine.setState(COIN_INSERTED);
                break;
        }
    }
}
