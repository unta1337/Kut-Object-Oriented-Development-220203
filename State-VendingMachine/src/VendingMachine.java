/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김상진, 김성녕
 * 상태 패턴
 * 자동판매기
 */
public class VendingMachine {
	private InventoryStock inventoryStock = new InventoryStock(); 		// 자판기가 보유하고 있는 음료 정보
	private CashRegister cashRegister = new CashRegister();       		// 자판기가 보유하고 있는 돈 정보 (고객이 투입한 돈도 포함)
	// private CashRegister userCashRegister = new CashRegister();   		// 고객이 투입한 돈 정보
	private int userCashAmountRegister = 0;

	private State itemEmpty;
	private State coinEmpty;
	private State coinInserted;
	private State itemSold;

	private State state;

	public VendingMachine() {
		this.itemEmpty = new ItemEmpty(this);
		this.coinEmpty = new CoinEmpty(this);
		this.coinInserted = new CoinInserted(this);
		this.itemSold = new ItemSold(this);

		this.state = itemEmpty;
	}
	
	// InventoryStock 상호작용
	public boolean isEmpty() {
		return inventoryStock.isEmpty();
	}
	
	public int getNumberOfItems(Item item) {
		return inventoryStock.getNumberOfItems(item);
	}
	
	// 상태 변화가 필요할 수 있음
	public void clearItems() {
		inventoryStock.clear();

		state = itemEmpty;
	}
	
	// 상태 변화가 필요할 수 있음
	public void setItems(Item item, int amount) {
		inventoryStock.setItem(item, amount);

		state = coinEmpty;
	}
	
	// 상태 변화가 필요할 수 있음
	public void removeItem(Item item) {
		inventoryStock.removeItem(item);

		if (isEmpty())
			state = coinEmpty;
	}
	
	// cashRegister 상호작용
	public int getBalance() {
		return cashRegister.getBalance();
	}
	
	public void setCash(Currency currency, int amount) {
		cashRegister.set(currency, amount);
	}
	
	public int getAmount(Currency currency) {
		return cashRegister.getAmount(currency);
	}
	
	// userCashRegister 상호작용
	public int getInsertedBalance() {
		//return userCashRegister.getBalance();
		return userCashAmountRegister;
	}
	
	// public void setUserCashRegister(CashRegister changeRegister) {
	// 	userCashRegister = changeRegister;
	// }
	
	// 실제 투입된 돈을 처리하는 메소드
	// 고객이 투입한 돈은 자판기 보유 돈에도 포함하여 처리함
	public void addCash(Currency currency, int amount) {
		// userCashRegister.add(currency, amount);
		userCashAmountRegister += currency.value * amount;
		cashRegister.add(currency, amount);
	}
	
	// vendingMachine 자체와 상호작용
	public void insertCash(Currency currency, int amount){
		state.insertCash(currency, amount);
	}
	
	public void selectItem(Item item) throws ChangeNotAvailableException {
		state.selectItem(item);

		if (state == itemSold) {
			state.dispenseItem(item);
		}
	}
	
	public void cancel() {
		state.cancel();
	}
	
	// 거스름 처리
	public CashRegister getChange(int price){
		return cashRegister.getChange(price);
	}
	
	public void returnChange() {
		// for(var cash: userCashRegister.getHoldingCash()) {
		// 	if(cash.getValue()>0)
		// 		cashRegister.remove(cash.getKey(), cash.getValue());
		// }
		// userCashRegister.clear();

		CashRegister change = getChange(userCashAmountRegister);
		for(var cash : change.getHoldingCash())
			if(cash.getValue() > 0)
			{
				System.out.println(cash.getKey());
				cashRegister.remove(cash.getKey(), cash.getValue());
			}
		userCashAmountRegister = 0;
	}
	
	// 주어진 item을 구입할 수 있는지 여부를 알려줌
	boolean canBuyItem(Item item) {
		// int changeAmount = userCashRegister.getBalance()-item.price;
		int changeAmount = userCashAmountRegister - item.price;
		if(changeAmount<0) return false;
		else if(changeAmount==0) return true;
		else {
			CashRegister changeRegister = getChange(changeAmount);
			return changeRegister.getBalance()==changeAmount;
		}
	}
	
	// 구입할 수 있는 것이 있는지 여부를 알려줌
	boolean canBuyAnyItem() {
		for(var item: inventoryStock.getPurchasbleitems())
			if(canBuyItem(item)) return true;
		return false;
	}
	
	public void debugPrint() {
		System.out.println("=========================");
		cashRegister.debugPrint();
		inventoryStock.debugPrint();
	}

	public State getItemEmpty() {
		return itemEmpty;
	}

	public State getCoinEmpty() {
		return coinEmpty;
	}

	public State getCoinInserted() {
		return coinInserted;
	}

	public State getItemSold() {
		return itemSold;
	}

	public void setState(State state) {
		this.state = state;
	}

	public CashRegister getCachRegister() {
		return cashRegister;
	}

	// public CashRegister getUserCachRegister() {
	// 	return userCashRegister;
	// }

	public InventoryStock getInventoryStock() {
		return inventoryStock;
	}

	public void addAmount(Currency currency, int amount) {
		userCashAmountRegister += currency.value * amount;
	}

	public void addAmount(int amount) {
		userCashAmountRegister += amount;
	}
}
