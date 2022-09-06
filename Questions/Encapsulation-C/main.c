#include <stdio.h>

#include "frog/frog.h"
#include "frog/robot_frog.h"

int main(void)
{
    // 기본 개구리.
    printf("기본 개구리.\n");

    struct frog* f = frog_create();

    f->jump(f);
    f->croak(f);

    printf("\n");

    // 로봇 개구리.
    printf("로봇 개구리.\n");

    struct robot_frog* rf = robot_frog_create();

    printf("초기 전력: %d\n", rf->charge);

    while (!rf->is_discharged(rf))
    {
        rf->jump(rf);
        rf->croak(rf);
    }

    printf("충전 전 전력: %d\n", rf->charge);
    rf->do_charge(rf);
    printf("충전 후 전력: %d\n", rf->charge);

    // 동적할당 해제.
    frog_delete(f);
    robot_frog_delete(rf);
}