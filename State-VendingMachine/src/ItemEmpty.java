/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김성녕
 * 상태 패턴
 * 상품이 없는 상태
 */
public class ItemEmpty implements State {
    public ItemEmpty(VendingMachine vendingMachine) {
    }

    @Override
    public void insertCash(Currency currency, int amount) {
        System.out.println("Vending machine is empty.");
    }

    @Override
    public void selectItem(Item item) {
        System.out.println("Vending machine is empty.");
    }

    @Override
    public void cancel() {
        System.out.println("Vending machine is empty.");
    }

    @Override
    public void dispenseItem(Item item) {
        System.out.println("Vending machine is empty.");
    }
}