package impl;

public class RobotFrogHas {
    private Frog frog;
    private int charge;

    public RobotFrogHas() {
        frog = new Frog();
        charge = 5;
    }

    public void jump() {
        if (isDischarged()) {
            System.out.println("전력이 부족합니다.");
            return;
        }

        frog.jump();

        charge--;
    }

    public void croak() {
        if (isDischarged()) {
            System.out.println("전력이 부족합니다.");
            return;
        }

        frog.croak();

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
 * has-a 관계에선 Frog에 정의된 멤버를 활용하기 위해 Frog 타입의 멤버 변수를 유지한다.
 * 
 * 장:
 * # 별도의 조치 없이 Frog에서 원하지 않는 변수나 메소드 등을 의도적으로 활용하지 않도록 설정할 수 있다.
 * # Frog와 RobotFrog처럼 클래스에 논리적으로 대응되는 클래스가 하나가 아닐 때에도 유연하게 사용할 수 있다.
 *   컴퓨터의 구성 요소를 정의할 때 has-a 관계를 활용하면 이를 쉽게 나타낼 수 있다.
 * # 언어적으로 다중 상속과 같은 기능을 제공하지 않을 때 비슷한 효과를 줄 수 있다.
 * 
 * 단:
 * # 자식 클래스가 부모 클래스에 있는 메소드를 그대로 활용하더라도 이를 따로 정의해야 사용할 수 있다.
 * # has-a를 통해 유지되는 변수는 문법상으로 아무런 관계가 없으므로 언어에서 제공하는 관련 기능을 사용할 수 없다.
 *   때문에 때에 따라선 프로그램의 강건성을 해칠 수 있다.
 *   e.g. 자바에서 제공하는 어노테이션 @Override 등.
 * # Frog와 RobotFrogHas는 문법적으로 아무런 관계가 없으므로 이를 배열 등에 동시에 유지할 수 없다.
 */