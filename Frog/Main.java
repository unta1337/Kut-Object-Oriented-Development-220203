import java.util.concurrent.atomic.AtomicInteger;

import impl.Frog;
import impl.RobotFrog;
import impl.RobotFrogHas;

public class Main {
    public static void main(String[] args) {
        // 기본 개구리.
        System.out.println("기본 개구리.");

        Frog frog = new Frog();

        frog.jump();
        frog.croak();

        System.out.println();

        // 상속으로 구현된 로봇 개구리.
        System.out.println("상속으로 구현된 로봇 개구리.");

        RobotFrog robotFrog = new RobotFrog();

        System.out.println("초기 전력: " + robotFrog.getCharge());
        while (!robotFrog.isDischarged()) {
            robotFrog.jump();
            robotFrog.croak();
        }

        System.out.println("충전 전 전력: " + robotFrog.getCharge());
        robotFrog.charge();
        System.out.println("충전 후 전력: " + robotFrog.getCharge());

        System.out.println();

        // 포함관계로 구현된 로봇 개구리.
        System.out.println("포함관계으로 구현된 로봇 개구리.");

        RobotFrogHas robotFrogHas = new RobotFrogHas();

        System.out.println("초기 전력: " + robotFrogHas.getCharge());
        while (!robotFrogHas.isDischarged()) {
            robotFrogHas.jump();
            robotFrogHas.croak();
        }

        System.out.println("충전 전 전력: " + robotFrogHas.getCharge());
        robotFrogHas.charge();
        System.out.println("충전 후 전력: " + robotFrogHas.getCharge());
        
        System.out.println();

        // 상속 또는 포함관계가 아닌 방법으로 RobotFrog 구현 (use-a).
        System.out.println("상속 또는 포함관계가 아닌 방법으로 RobotFrog 구현 (use-a).");
        robotFrogUseA();
    }

    /*
     * 상속 또는 포함관계가 아닌 방법으로 RobotFrog 구현 (use-a)
     *
     * Frog, RobotFrog, RobotFrogHas에서,
     * 상속관계는 클래스를 통해 Frog와 RobotFrog의 관계를 정의한다.
     * 포함관계는 Frog 클래스와 멤버 변수 및 메소드를 통해 Frog와 RobotFrogHas의 관계를 정의한다.
     * 
     * 아래의 구현은 각각의 변수들을 어떤 함수를 매개로 변수들의 관계를 정의한다.
     * 
     * e.g. C/C++의 qsort
     * 
     * C/C++의 표준 라이브러리에 정의된 qsort.
     * void qsort(void* ptr, size_t count, size_t size, (*comp)(const void*, const void*));
     * 
     * qsort 함수는 서로 관련이 없는 ptr, count, size, comp의 관계를 정의하여 정렬을 수행한다.
     * 
     * 위의 예시와 같이 아래에 정의된 함수들은 서로 관계가 없는 frog과 charge의 관계를 정의하여 각 행동을 수행한다.
     */
    public static void robotFrogUseA() {
        Frog frog = new Frog();                         // 개구리를 유지하는 변수.
        AtomicInteger charge = new AtomicInteger(5);    // 로봇 개구리의 전력을 유지하는 변수. (자바의 특성상 함수에서 수정이 가능하도록 AtomicInteger로 선언)

        System.out.println("초기 전력: " + charge);
        while (charge.get() > 0) {
            robotFrogJump(frog, charge);
            robotFrogCroak(frog, charge);
        }

        System.out.println("충전 전 전력: " + charge);
        robotFrogCharge(charge);
        System.out.println("충전 후 전력: " + charge);
    }

    public static void robotFrogJump(Frog frog, AtomicInteger charge) {
        if (charge.get() <= 0) {
            System.out.println("전력이 부족합니다.");
            return;
        }

        frog.jump();

        charge.set(charge.get() - 1);
    }

    public static void robotFrogCroak(Frog frog, AtomicInteger charge) {
        if (charge.get() <= 0) {
            System.out.println("전력이 부족합니다.");
            return;
        }

        frog.croak();

        charge.set(charge.get() - 1);
    }

    public static void robotFrogCharge(AtomicInteger charge) {
        charge.set(5);
    }
}