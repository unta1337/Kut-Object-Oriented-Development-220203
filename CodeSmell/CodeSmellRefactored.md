# **부적절한 코드를 리팩토링하기.**  
## **코드의 요구 사항.**  
### **입력**
0, 1, 2, 3으로 표시되는 맵의 정보.

_0: 통로_  
_1: 시작 지점_  
_2: 보석_  
_3: 벽_  

### **출력**
주어진 맵의 시작 지점으로부터 보석까지 도달하는 경로 중 길이가 가장 짧은 경로의 길이.

### **입출력 예시**
> **입력 예시**  
> ```
> 3 0 3 0 3 1 3  
> 3 0 0 0 3 0 3  
> 3 0 3 0 0 0 3  
> 3 0 3 3 3 0 3  
> 3 0 0 2 3 0 0  
> 3 3 3 3 3 3 3  
> ```

> **출력 예시**  
> ```
> 12
> ```

## **기존 코드의 문제점.**  
### **너무 많은 역할을 담당하는 메소드.**
기존 코드에 주어진 solve 메소드는 너무 많은 역할을 담당한다.  
해당 메소드의 내용을 주석을 통해 묘사하면 다음과 같다.  

``` java
public static int solve(int[][] map) {
    // 맵에서 시작 위치를 탐색.
    ... 

    // 시작 위치에서 BFS를 활용하여 도착 지점 탐색.
    ...
    
    return minLength;
}
```

solve 메소드는 상기한 것과 같이 시작 지점을 찾아 BFS를 수행해 답을 찾아낸다.  
이것만 볼 때에는 큰 문제가 없어보이나 문제가 되는 것은 이러한 일련의 알고리즘을 단일 함수에 모두 구현한 것이다.  

너무 많은 구현을 하나의 함수에 모두 작성하면 가독성 측면에서 함수의 구현 목적을 판단하기 어렵다.  
또 해당 함수의 어떤 부분이 어느 문제를 해결하는지 파익하기 어려워지므로 유지 및 보수 측면에서도 문제가 생길 수 있다.  

### **불필요한 변수의 남용.**
기존의 코드에서 탐색 위치와 경로의 길이를 추적하기 위해 세 개의 서로 다른 큐를 사용하고 있다.  

``` java
Queue<Integer> rowQueue = new ArrayDeque<>();
Queue<Integer> colQueue = new ArrayDeque<>();
Queue<Integer> lengthQueue = new ArrayDeque<>();
```

이 역시 앞서 살펴본 메소드처럼 가독성과 유지 및 보수에 악영향을 미친다.  
또한 알고 보면 간단한 개념인 위치와 길이를 담는 데 세 개의 변수를 활용하므로 과도하게 복잡한 코드가 된다.  

### **가독성을 고려하지 않은 조건문의 남용**
기존 코드에서 탐색 방향에 따른 탐색 유효성을 판단하기 위해 탐색 방향에 대해 모든 경우의 수를 고려하는 조건문을 작성했다.  

``` java
if(currR+1<map.length && !visited[currR+1][currC]) {
    if(map[currR+1][currC]==2) {
        ...
    }
    else if(map[currR+1][currC]==0) {
        ...
    }
}
if(currR-1>=0 && !visited[currR-1][currC]) {
    if(map[currR-1][currC]==2) {
        ...
    }
    else if(map[currR-1][currC]==0) {
        ...
    }
}
if(currC+1<map[0].length && !visited[currR][currC+1]) {
    if(map[currR][currC+1]==2) {
        ..
    }
    else if(map[currR][currC+1]==0) {
        ...
    }
}
if(currC-1>=0 && !visited[currR][currC-1]) {
    if(map[currR][currC-1]==2) {
        ...
    }
    else if(map[currR][currC-1]==0) {
        ...
    }
}
```

위의 예시에서 탐색의 유효성을 판단하기 위해 확인하는 조건은 두 가지가 있다.  
첫째, 탐색하고자 하는 좌표가 유효한가?  
둘째, 탐색하고자 하는 좌표가 아직 탐색하지 않은 좌표인가?  

이와 더불어 탐색 종료 조건을 확인하기 위해 동일한 조건을 매 방향마다 검사하고 있다.  

이렇게 코드를 작성하면 추후 코드를 읽을 때 해당 조건문이 무엇을 위해 작성되었는지 판단하기 어려워진다.  

## **코드 리팩토링하기.**  
### **너무 많은 역할을 담당하는 메소드.**
불필요하게 한 번에 구현된 알고리즘을 별도의 메소드로 분리하여 구현할 수 있다.  

``` java
public static int solve(int[][] map) {
    Optional<Status> startPos = Optional.ofNullable(getStartingStatus(map));

    return startPos.map(pos -> getMinLength(map, pos)).orElse(-1);
}
```

불필요한 구현을 별도의 메소드로 분리하면 주석없이 코드를 이해할 수 있다.  
코드에 문제가 생겼을 때 어떤 함수에서 문제가 생겼는지 추적하여 어떤 문제가 생겼는지 디버깅하기도 용이해진다.  

추가로 자바에서 제공하는 Optional 클래스를 활용해 시작 지점이 주어지지 않은 유효하지 않은 입력에 대한 처리를 수행했다.  

#### **별도의 메소드 분리로 얻을 수 있는 추가적인 효과.**
위와 같이 메소드를 별도로 분리하면 아래와 같은 반복문을 더 간단하게 바꿀 수 있다.

``` java
FOUND:
for(int r=0; r<map.length; ++r)
    for(int c=0; c<map[0].length; ++c) {
        if(map[r][c]==1) {
            startR = r;
            startC = c;
            break FOUND;
        }
    }
```

위의 반복문은 이중 반복문으로 FOUND 레이블을 이용해 break하고 있다.  
좋은 접근이지만 이를 별도의 메소드로 분리하면 아래와 같이 return 구문을 통해 간단하게 작성할 수 있다.  

``` java
public static Status getStartingStatus(int[][] map) {
    for (int row = 0; row < map.length; row++)
        for (int col = 0; col < map[0].length; col++)
            if (map[row][col] == 1)
                return new Status(row, col, 0);

    return null;
}
```

### **불필요한 변수의 남용.**
논리적으로 연관성이 깊은 변수를 따로 유지하는 것을 클래스를 작성해 유지할 수 있다.  

``` java
record Status(int row, int col, int length) {
}
```

특히 행, 열, 길이 등 변화가 필요하지 않은 정보에 대해서는 자바의 record를 활용해 위와 같이 유지할 수 있다.

### **가독성을 고려하지 않은 조건문의 남용**
한 번에 처리할 수 있는 조건문은 본격적인 알고리즘이 시작하기 전 사전 조건으로 검사하는 것이 편리하다.  

``` java
if (!(0 <= row && row < map.length) || !(0 <= col && col < map[0].length))
    continue;

if (visited[row][col])
    continue;
visited[row][col] = true;
```

탐색하고자 하는 좌표의 유효성과 방문 유무는 위와 같이 한꺼번에 조건을 검사할 수 있다.  
유효성을 판단할 때 위와 같이 !과 && 연산자 등을 활용하여 사람에게 익숙한 형태로 작성하는 것이 도움이 될 수 있다.  

``` java
switch (map[row][col]) {
    case 2:
        return length;
    case 3:
        continue;
}
```

한편 종료 조건에 대한 검사는 모두 map에 저장된 값을 기준으로 하므로 이를 이용해 switch-case 구문으로 작성했다.  

#### **조건 간단화를 통해 얻을 수 있는 효과.**
위와 같이 사전 조건과 종료 조건을 검사하면 메소드의 주 목적인 탐색에 대한 알고리즘을 아래와 같이 간단하게 표현할 수 있게 된다.  

``` java
queue.add(new Status(row + 1, col, length + 1));
queue.add(new Status(row - 1, col, length + 1));
queue.add(new Status(row, col + 1, length + 1));
queue.add(new Status(row, col - 1, length + 1));
```