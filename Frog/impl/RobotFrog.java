package impl;

public class RobotFrog extends Frog {
    private int charge;

    public RobotFrog() {
        this.charge = 5;
    }

    @Override
    public void jump() {
        if (isDischarged()) {
            System.out.println("전력이 부족합니다.");
            return;
        }

        super.jump();

        charge--;
    }

    @Override
    public void croak() {
        if (isDischarged()) {
            System.out.println("전력이 부족합니다.");
            return;
        }

        super.croak();

        charge--;
    }

    public void charge() {
        charge = 5;
    }

    public boolean isDischarged() {
        return charge <= 0;
    }

    public int getCharge() {
        return charge;
    }
}

/*
 * is-a 관계에선 Frog의 멤버를 활용하기 위해 이를 상속한다.
 * 
 * 장:
 * # 부모 클래스의 메소드 등을 그대로 활용 가능하다면 별도의 코드 작성 없이 해당 메소드를 자식 클래스에서 사용할 수 있다.
 * # Frog와 Frog 및 RobotFrog의 최상위 클래스가 Frog이므로 해당 타입에서 파생되는 타입을 Frog 변수에 유지할 수 있다.
 *   이를 통해 Frog와 RobotFrog를 포함한 기타 파생 클래스를 동일한 배열 등에 유지할 수 있다.
 * # 프로그래밍 언어에서 제공하는 OOP 관련 기능을 모두 활용할 수 있다.
 *   e.g. 자바에서 제공하는 어노테이션 @Override 등.
 * 
 * 단:
 * # 부모 클래스에 원하지 않는 변수나 메소드가 있어도 이를 유지해야 한다.
 *   이를 원하지 않으면 해당 변수의 접근을 막거나 관련 메소드를 빈 메소드로 재정의하는 등의 조치가 필요하다.
 * # 자바의 경우 인터페이스 구현을 제외하면 단일 클래스만을 상속하게 되므로 여러 가지의 클래스를 상속하여 클래스를 정의할 수 없다.
 *   C++과 같은 경우에는 다중 상속을 허용하나 다이아몬드형 상속에서 문제가 발생할 수 있다.
 */