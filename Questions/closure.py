def main():
    print('누산기 a')
    a = get_acc()   # 누산기 변수 선언.

    print(a(0))     # 누산기에 0을 더하므로 결과는 0.
    print(a(1))     # 누산기에 1을 더하므로 결과는 1.
    print(a(1))     # 누산기에 1을 더하므로 결과는 2.
    print(a(1))     # 누산기에 1을 더하므로 결과는 3.
    print()

    print('누산기 b')
    b = get_acc()   # 새로운 누산기 변수 선언.

    # a와는 다른 누산기 변수이므로 0부터 시작한다.
    print(b(0))     # 누산기에 0을 더하므로 결과는 0.
    print(b(1))     # 누산기에 1을 더하므로 결과는 1.
    print(b(1))     # 누산기에 1을 더하므로 결과는 2.
    print(b(1))     # 누산기에 1을 더하므로 결과는 3.

# 누산기의 구현.
def get_acc():
    acc = 0             # 실제 누산 결과를 유지할 변수.

    def calc(a):
        nonlocal acc    # 현재 스코프인 calc에 정의되지 않은 변수 acc를 이용하기 위해 nonlocal 선언.

        # 누산 후 결과 반환.
        acc += a
        return acc

    # 누산기 역할을 하는 함수 반환.
    return calc

if __name__ == '__main__':
    main()