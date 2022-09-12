# **Use-A 관계를 활용한 RobotFrog의 구현.**
## **상속과 포함을 활용한 관계 정의.**
앞서 구현한 RobotFrog와 RobotFrogHas는 각각 상속과 포함 관계를 활용하여 Frog와 관계를 정의했다.  

이러한 관계의 구현은 아래와 같다.  

``` java
// Frog의 구현.
public class Frog {
    ...
}
```

``` java
// 상속을 활용한 RobotFrog의 구현.
public class RobotFrog extends Frog {
    ...
}
```

``` java
// 포함을 활용한 RobotFrogHas의 구현.
public class RobotFrogHas {
    private Frog frog;      // 상속의 효과를 볼 수 있도록 Frog 객체를 맴버로 유지.
    ...
}
```

## **함수를 매개로 객체 간 관계 정의.**
C 언어와 같이 현대의 객체지향을 온전히 제공하지 않는 언어에서는 함수를 매개로 객체 또는 변수 간의 관계를 정의하여 사용할 수 있다.  

하나의 예시로 C++에서는 vector 등과 같은 컨테이너를 제공하지만 C에서는 객체지향 프로그래밍을 지원하지 않아 이같은 컨테이너를 기본으로 제공하지는 않는다.  

하지만, vector와 같은 객체지향적 도구가 필요한 컨테이너가 아닌 함수들은 제공하는 경우가 있다. 이때 C의 표준 라이브러리는 함수를 매개로 변수 간 관계를 정의한다.  

예를 들어 stdlib 헤더에서 제공하는 qsort 함수의 원형은 다음과 같다.

``` c
void qsort(void* ptr,
           size_t count,
           size_t size,
           void (*comp)(const void*, cosnt void*));
```

위의 함수는 문법적으로 서로 전혀 관계가 없는 ptr, count, size, comp를 인수로 받아 이들의 관계를 정의, ptr에 저장된 요소들을 정렬한다.  

## **함수를 매개로 RobotFrogUseA 정의.**
위와 비슷하게 RobotFrog의 자식 클래스를 함수를 매개로 흉내낼 수 있다.

``` java
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
```

``` java
public static void robotFrogJump(Frog frog, AtomicInteger charge) {
    if (charge.get() <= 0) {
        System.out.println("전력이 부족합니다.");
        return;
    }

    frog.jump();

    charge.set(charge.get() - 1);
}
```

``` java
public static void robotFrogCroak(Frog frog, AtomicInteger charge) {
    if (charge.get() <= 0) {
        System.out.println("전력이 부족합니다.");
        return;
    }

    frog.croak();

    charge.set(charge.get() - 1);
}
```

``` java
public static void robotFrogCharge(AtomicInteger charge) {
    charge.set(5);
}
```

위와 같이 객체에 필요한 변수를 선언한 후 적절한 함수를 이용해 이 변수들의 관계를 정의할 수 있다.  
정의한 관계를 통해 클래스를 작성한 것과 비슷한 효과를 흉내낼 수 있다.  

이러한 구조는 C와 같은 객체지향을 온전히 지원하지 않는 언어에서 구현된 라이브러리 등에서 찾아볼 수 있다.  