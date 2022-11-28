import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VendingMachineTest {

	@Test
	void normalExecutionTest01() throws ChangeNotAvailableException {
		VendingMachine vMachine = new VendingMachine();
		vMachine.setItems(Item.Cider, 3);
		vMachine.setItems(Item.Cola, 2);
		vMachine.setItems(Item.Pocari, 3);
		assertEquals(vMachine.getNumberOfItems(Item.Cider),3);
		assertEquals(vMachine.getNumberOfItems(Item.Cola),2);
		assertEquals(vMachine.getNumberOfItems(Item.Pocari),3);
		assertEquals(vMachine.getNumberOfItems(Item.Max),0);
		vMachine.setCash(Currency.C100, 10);
		vMachine.setCash(Currency.C500, 5);
		vMachine.setCash(Currency.P1000, 5);
		assertEquals(vMachine.getAmount(Currency.C100), 10);
		assertEquals(vMachine.getAmount(Currency.P5000), 0);
		assertEquals(vMachine.getBalance(), 8_500);
		vMachine.insertCash(Currency.P1000, 2);
		assertEquals(vMachine.getAmount(Currency.P1000), 7);
		assertEquals(vMachine.getBalance(), 10_500);
		vMachine.selectItem(Item.Cider);
		assertEquals(vMachine.getNumberOfItems(Item.Cider),2);
		assertEquals(vMachine.getBalance(), 10_500);
		// 예외 발생 검사.
		boolean isExceptionThrown = false;
		try {
			vMachine.selectItem(Item.Cola);
		}
		catch(ChangeNotAvailableException e) {
			isExceptionThrown = true;
			assertTrue(e.changeReturned);
		}
		assertTrue(isExceptionThrown);
		assertEquals(vMachine.getNumberOfItems(Item.Cola),1);
		assertEquals(vMachine.getAmount(Currency.C100), 6);
		assertEquals(vMachine.getBalance(), 10_100);
	}
	
	@Test
	void changeNotAvailableTest01() {
		VendingMachine vMachine = new VendingMachine();
		vMachine.setItems(Item.Cider, 3);
		vMachine.setItems(Item.Cola, 2);
		vMachine.setItems(Item.Pocari, 3);
		assertEquals(vMachine.getNumberOfItems(Item.Cider),3);
		assertEquals(vMachine.getNumberOfItems(Item.Cola),2);
		assertEquals(vMachine.getNumberOfItems(Item.Pocari),3);
		assertEquals(vMachine.getNumberOfItems(Item.Max),0);
		vMachine.setCash(Currency.C500, 5);
		vMachine.setCash(Currency.P1000, 5);
		assertEquals(vMachine.getAmount(Currency.C100), 0);
		assertEquals(vMachine.getAmount(Currency.C500), 5);
		assertEquals(vMachine.getBalance(), 7_500);
		vMachine.insertCash(Currency.P1000, 2);
		assertEquals(vMachine.getBalance(), 9_500);
		assertEquals(vMachine.getAmount(Currency.P1000), 7);
		assertThrows(ChangeNotAvailableException.class,()->vMachine.selectItem(Item.Cider));
		assertEquals(vMachine.getNumberOfItems(Item.Cider),3);
		assertEquals(vMachine.getBalance(), 9_500);
		assertEquals(vMachine.getAmount(Currency.C500), 5);
	}
	
	@Test
	void changeNotAvailableTest02() {
		VendingMachine vMachine = new VendingMachine();
		vMachine.setItems(Item.Cider, 3);
		vMachine.setItems(Item.Cola, 2);
		vMachine.setItems(Item.Pocari, 3);
		assertEquals(vMachine.getNumberOfItems(Item.Cider),3);
		assertEquals(vMachine.getNumberOfItems(Item.Cola),2);
		assertEquals(vMachine.getNumberOfItems(Item.Pocari),3);
		assertEquals(vMachine.getNumberOfItems(Item.Max),0);
		vMachine.setCash(Currency.C500, 6);
		vMachine.setCash(Currency.P1000, 2);
		assertEquals(vMachine.getAmount(Currency.C100), 0);
		assertEquals(vMachine.getAmount(Currency.C500), 6);
		assertEquals(vMachine.getBalance(), 5_000);
		vMachine.insertCash(Currency.P10000, 1);
		assertEquals(vMachine.getBalance(), 15_000);
		assertEquals(vMachine.getAmount(Currency.P10000), 1);
		assertThrows(ChangeNotAvailableException.class,()->vMachine.selectItem(Item.Pocari));
		assertEquals(vMachine.getNumberOfItems(Item.Pocari),3);
		assertEquals(vMachine.getBalance(), 5_000);
		assertEquals(vMachine.getAmount(Currency.P10000), 0);
	}
	
	@Test
	void normalExecutionTest02() throws ChangeNotAvailableException {
		VendingMachine vMachine = new VendingMachine();
		vMachine.setItems(Item.Cider, 3);
		assertEquals(vMachine.getNumberOfItems(Item.Cider),3);
		assertEquals(vMachine.getNumberOfItems(Item.Cola),0);
		vMachine.setCash(Currency.C100, 10);
		vMachine.setCash(Currency.C500, 10);
		assertEquals(vMachine.getAmount(Currency.C100), 10);
		assertEquals(vMachine.getAmount(Currency.C500), 10);
		assertEquals(vMachine.getBalance(), 6_000);
		vMachine.insertCash(Currency.P5000, 1);
		assertEquals(vMachine.getBalance(), 11_000);
		assertEquals(vMachine.getAmount(Currency.C500), 10);
		assertEquals(vMachine.getAmount(Currency.P5000), 1);
		vMachine.selectItem(Item.Cider);
		assertEquals(vMachine.getNumberOfItems(Item.Cider),2);
		assertEquals(vMachine.getBalance(), 11_000);
		vMachine.cancel();
		assertEquals(vMachine.getBalance(), 6_800);
		assertEquals(vMachine.getAmount(Currency.C100), 8);
		assertEquals(vMachine.getAmount(Currency.C500), 2);
	}
	
	@Test
	void normalExecutionTest03() throws ChangeNotAvailableException {
		VendingMachine vMachine = new VendingMachine();
		vMachine.setItems(Item.Cider, 3);
		assertEquals(vMachine.getNumberOfItems(Item.Cider),3);
		assertEquals(vMachine.getNumberOfItems(Item.Cola),0);
		vMachine.setCash(Currency.C100, 10);
		vMachine.setCash(Currency.C500, 10);
		vMachine.setCash(Currency.P1000, 2);
		assertEquals(vMachine.getAmount(Currency.C100), 10);
		assertEquals(vMachine.getAmount(Currency.C500), 10);
		assertEquals(vMachine.getAmount(Currency.P1000), 2);
		assertEquals(vMachine.getBalance(), 8_000);
		vMachine.insertCash(Currency.P5000, 1);
		assertEquals(vMachine.getBalance(), 13_000);
		assertEquals(vMachine.getAmount(Currency.C500), 10);
		assertEquals(vMachine.getAmount(Currency.P5000), 1);
		vMachine.selectItem(Item.Cider);
		assertEquals(vMachine.getNumberOfItems(Item.Cider),2);
		assertEquals(vMachine.getBalance(), 13_000);
		vMachine.cancel();
		assertEquals(vMachine.getBalance(), 8_800);
		assertEquals(vMachine.getAmount(Currency.P1000), 0);
		assertEquals(vMachine.getAmount(Currency.C100), 8);
		assertEquals(vMachine.getAmount(Currency.C500), 6);
		assertEquals(vMachine.getAmount(Currency.P5000), 1);
	}
	
	@Test
	void changeTest01(){
		VendingMachine vMachine = new VendingMachine();
		vMachine.setItems(Item.Cider, 3);
		vMachine.setItems(Item.Cola, 2);
		vMachine.setItems(Item.Pocari, 3);
		assertEquals(vMachine.getNumberOfItems(Item.Cider),3);
		assertEquals(vMachine.getNumberOfItems(Item.Cola),2);
		assertEquals(vMachine.getNumberOfItems(Item.Pocari),3);
		assertEquals(vMachine.getNumberOfItems(Item.Max),0);
		vMachine.setCash(Currency.C100, 10);
		vMachine.setCash(Currency.C500, 5);
		vMachine.setCash(Currency.P1000, 5);
		assertEquals(vMachine.getAmount(Currency.C100), 10);
		assertEquals(vMachine.getAmount(Currency.P5000), 0);
		assertEquals(vMachine.getBalance(), 8_500);
		vMachine.insertCash(Currency.C500, 2);
		assertEquals(vMachine.getAmount(Currency.C500), 7);
		assertEquals(vMachine.getBalance(), 9_500);
		vMachine.cancel();
		assertEquals(vMachine.getAmount(Currency.C500), 5);
		assertEquals(vMachine.getBalance(), 8_500);
	}
	
	@Test
	void soldOutTest01() throws ChangeNotAvailableException{
		VendingMachine vMachine = new VendingMachine();
		vMachine.setItems(Item.Cider, 1);
		vMachine.setItems(Item.Cola, 2);
		vMachine.setItems(Item.Pocari, 1);
		assertEquals(vMachine.getNumberOfItems(Item.Cider),1);
		assertEquals(vMachine.getNumberOfItems(Item.Cola),2);
		assertEquals(vMachine.getNumberOfItems(Item.Pocari),1);
		assertEquals(vMachine.getNumberOfItems(Item.Max),0);
		vMachine.setCash(Currency.C100, 10);
		vMachine.setCash(Currency.C500, 5);
		vMachine.setCash(Currency.P1000, 5);
		assertEquals(vMachine.getAmount(Currency.C100), 10);
		assertEquals(vMachine.getAmount(Currency.P5000), 0);
		assertEquals(vMachine.getBalance(), 8_500);
		vMachine.insertCash(Currency.P5000, 1);
		assertEquals(vMachine.getAmount(Currency.P5000), 1);
		assertEquals(vMachine.getBalance(), 13_500);
		vMachine.selectItem(Item.Cider);
		assertEquals(vMachine.getNumberOfItems(Item.Cider),0);
		assertEquals(vMachine.getBalance(), 13_500);
		vMachine.selectItem(Item.Pocari);
		assertEquals(vMachine.getNumberOfItems(Item.Pocari),0);
		assertEquals(vMachine.getBalance(), 13_500);
		vMachine.selectItem(Item.Cola);
		assertEquals(vMachine.getNumberOfItems(Item.Cola),1);
		assertEquals(vMachine.getBalance(), 13_500);
		vMachine.selectItem(Item.Cola);
		assertEquals(vMachine.getNumberOfItems(Item.Cola),0);
		assertEquals(vMachine.getBalance(), 11_900);	
		vMachine.insertCash(Currency.P1000, 1);
		assertEquals(vMachine.getBalance(), 11_900);
		vMachine.setItems(Item.Cola, 2);
		vMachine.insertCash(Currency.P1000, 1);
		assertEquals(vMachine.getBalance(), 12_900);
	}
}
