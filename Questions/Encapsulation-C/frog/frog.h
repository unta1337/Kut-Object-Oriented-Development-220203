#ifndef __FROG_H
#define __FROG_H

#include <stdio.h>
#include <stdlib.h>

// 구조체의 멤버 변수 중 함수 포인터를 이용하여 클래스의 멤버 함수를 흉내냄.
struct frog
{
    void (*jump)(struct frog*);
    void (*croak)(struct frog*);
};

// '__'를 붙여 해당 함수가 내부 함수 혹은 멤버 함수임을 나타냄.
void __frog_jump(struct frog* ths)
{
    printf("개굴개굴\n");
}

void __frog_croak(struct frog* ths)
{
    printf("폴짝폴짝\n");
}

// 동적할당을 이용해 생성자를 흉내냄.
struct frog* frog_create()
{
    struct frog* ths = (struct frog*)malloc(sizeof(struct frog));

    ths->jump = __frog_jump;
    ths->croak = __frog_croak;

    return ths;
}

// 동적할당 헤제를 이용해 소멸자를 흉내냄.
void frog_delete(struct frog* ths)
{
    free(ths);
}

#endif