/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김성녕
 * 상태 패턴
 * 자동 판매기 상태
 */
public enum State {
    COIN_EMPTY {
        @Override
        public void insertCash(VendingMachine vendingMachine, Currency currency, int amount) {
            System.out.println("Insert a coin into the machine.");
            
            // 동전 삽입 후 대기 상태(동전이 삽입되어 상품 선택을 기다리는 상태)로 전환.
            vendingMachine.addCash(currency, amount);
            vendingMachine.setState(COIN_INSERTED);
        }

        @Override
        public void selectItem(VendingMachine vendingMachine, Item item) {
            System.out.println("Please insert a coin first.");
        }

        @Override
        public void cancel(VendingMachine vendingMachine) {
            System.out.println("There's nothing to cancel.");
        }

        @Override
        public void dispenseItem(VendingMachine vendingMachine, Item item) {
            System.out.println("Please insert a coin first.");
        }
    },

    COIN_INSERTED {
        @Override
        public void insertCash(VendingMachine vendingMachine, Currency currency, int amount) {
            System.out.println("Insert a coin into the machine.");

            // 추가 동전 삽입.
            vendingMachine.addCash(currency, amount);
        }

        @Override
        public void selectItem(VendingMachine vendingMachine, Item item) throws ChangeNotAvailableException {
            System.out.println("You've seleted " + item.name().toLowerCase() + ".");

            // 상품을 구매할 수 없을 때 처리.
            boolean changeReturned = false;
            if (!vendingMachine.canBuyItem(item)) {
                // 다른 구매 가능한 상품이 없으면 거스름 반환.
                if (!vendingMachine.canBuyAnyItem()) {
                    vendingMachine.returnChange();
                    changeReturned = true;
                }

                throw new ChangeNotAvailableException(changeReturned);
            }

            vendingMachine.setState(ITEM_SOLD);
        }

        @Override
        public void cancel(VendingMachine vendingMachine) {
            System.out.println("Item selection canceled.");

            // 상품 구매 취소 시 거스름 반환 및 상태 전환.
            vendingMachine.returnChange();
            vendingMachine.setState(COIN_EMPTY);
        }

        @Override
        public void dispenseItem(VendingMachine vendingMachine, Item item) {
            System.out.println("Please select a item first.");
        }
    },

    ITEM_EMPTY {
        @Override
        public void insertCash(VendingMachine vendingMachine, Currency currency, int amount) {
            System.out.println("Vending machine is empty.");
        }

        @Override
        public void selectItem(VendingMachine vendingMachine, Item item) {
            System.out.println("Vending machine is empty.");
        }

        @Override
        public void cancel(VendingMachine vendingMachine) {
            System.out.println("Vending machine is empty.");
        }

        @Override
        public void dispenseItem(VendingMachine vendingMachine, Item item) {
            System.out.println("Vending machine is empty.");
        }
    },

    ITEM_SOLD {
        @Override
        public void insertCash(VendingMachine vendingMachine, Currency currency, int amount) {
            System.out.println("Product is on its way! Please wait for a second.");
        }

        @Override
        public void selectItem(VendingMachine vendingMachine, Item item) {
            System.out.println("Product is on its way! Please wait for a second.");

        }

        @Override
        public void cancel(VendingMachine vendingMachine) {
            System.out.println("You've reached the point of no return. Just wait for the consequences.");
        }

        @Override
        public void dispenseItem(VendingMachine vendingMachine, Item item) throws ChangeNotAvailableException {
            System.out.println("Product is on its way! Please wait for a second.");

            // 상품 배출.
            vendingMachine.removeItem(item);

            // 거스름 처리.
            CashRegister newUserCashRegister = vendingMachine.getChange(vendingMachine.getInsertedBalance() - item.price);
            vendingMachine.setUserCashRegister(newUserCashRegister);

            vendingMachine.setState(COIN_INSERTED);

            // 다른 구매 가능한 상품이 없으면 거스름 반환.
            if (!vendingMachine.canBuyAnyItem()) {
                vendingMachine.returnChange();

                // 구매 불가능 이유에 따라서 서로 다른 처리.
                if (vendingMachine.isEmpty()) {
                    vendingMachine.setState(ITEM_EMPTY);
                } else {
                    throw new ChangeNotAvailableException(true);
                }
            }
        }
    };

    public abstract void insertCash(VendingMachine vendingMachine, Currency currency, int amount);
    public abstract void selectItem(VendingMachine vendingMachine, Item item) throws ChangeNotAvailableException;
    public abstract void cancel(VendingMachine vendingMachine);
    public abstract void dispenseItem(VendingMachine vendingMachine, Item item) throws ChangeNotAvailableException;
}
