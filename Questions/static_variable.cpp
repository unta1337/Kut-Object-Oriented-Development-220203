#include <iostream>

int acc(int add)
{
    static int value = 0;

    return value + add;
}

int main(void)
{
    std::cout << acc(0) << "\n";
    std::cout << acc(1) << "\n";
    std::cout << acc(2) << "\n";
    std::cout << acc(3) << "\n";

    return 0;
}