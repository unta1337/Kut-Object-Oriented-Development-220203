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
	private CashRegister userCashRegister = new CashRegister();   		// 고객이 투입한 돈 정보

	private State state;

	public VendingMachine() {
		this.state = State.ITEM_EMPTY;
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

		// 재고 소진 상태 전환.
		state = State.ITEM_EMPTY;
	}
	
	// 상태 변화가 필요할 수 있음
	public void setItems(Item item, int amount) {
		inventoryStock.setItem(item, amount);

		// 동전 소진 상태 전환.
		state = State.COIN_EMPTY;
	}
	
	// 상태 변화가 필요할 수 있음
	public void removeItem(Item item) {
		inventoryStock.removeItem(item);

		// 재고 소진 시 상태 변환.
		if (isEmpty())
			state = State.ITEM_EMPTY;
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
		return userCashRegister.getBalance();
		// return userCashAmountRegister;
	}
	
	public void setUserCashRegister(CashRegister changeRegister) {
		userCashRegister = changeRegister;
	}
	
	// 실제 투입된 돈을 처리하는 메소드
	// 고객이 투입한 돈은 자판기 보유 돈에도 포함하여 처리함
	public void addCash(Currency currency, int amount) {
		userCashRegister.add(currency, amount);
		cashRegister.add(currency, amount);
	}
	
	// vendingMachine 자체와 상호작용
	public void insertCash(Currency currency, int amount){
		state.insertCash(this, currency, amount);
	}
	
	public void selectItem(Item item) throws ChangeNotAvailableException {
		// 상품을 고르는 과정에서 발생할 수 있는 예외 처리.
		try {
			state.selectItem(this, item);
		}
		
		// 상위 메소드에 예외 발생 여부를 알리기 위해 예외 되던지기.
		catch (ChangeNotAvailableException e) {
			throw e;
		}
		
		// 예외 발생 여부와 관계없이 상품 배출이 필요하면 수행해야 함.
		finally {
			if (state == State.ITEM_SOLD) {
				state.dispenseItem(this, item);
			}
		}
	}
	
	public void cancel() {
		state.cancel(this);
	}
	
	// 거스름 처리
	public CashRegister getChange(int price){
		return cashRegister.getChange(price);
	}
	
	public void returnChange() {
		for(var cash: userCashRegister.getHoldingCash()) {
			if(cash.getValue()>0)
			{
				System.out.println(cash.getKey());
				cashRegister.remove(cash.getKey(), cash.getValue());
			}
		}
		userCashRegister.clear();
	}
	
	// 주어진 item을 구입할 수 있는지 여부를 알려줌
	boolean canBuyItem(Item item) {
		int changeAmount = userCashRegister.getBalance()-item.price;
		// int changeAmount = userCashAmountRegister - item.price;
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

	public void setState(State state) {
		this.state = state;
	}
}
