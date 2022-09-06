#ifndef __ROBOT_FROG_H
#define __ROBOT_FROG_H

#include <stdbool.h>

#include "frog.h"

// 구조체 포인터를 이용해 상속을 흉내냄. (has-a 관계)
// 구조체 멤버를 이용해 멤버 변수를 흉내냄.
struct robot_frog
{
    struct frog* supr;

    int charge;

    void (*jump)(struct robot_frog*);
    void (*croak)(struct robot_frog*);

    void (*do_charge)(struct robot_frog*);
    bool (*is_discharged)(struct robot_frog*);
};

void __robot_frog_jump(struct robot_frog* ths)
{
    if (ths->is_discharged(ths))
    {
        printf("전력이 부족합니다\n");
        return;
    }

    printf("개굴개굴\n");

    ths->charge--;
}

void __robot_frog_croak(struct robot_frog* ths)
{
    if (ths->is_discharged(ths))
    {
        printf("전력이 부족합니다\n");
        return;
    }

    printf("폴짝폴짝\n");

    ths->charge--;
}

void __robot_frog_do_charge(struct robot_frog* ths)
{
    ths->charge = 5;
}

bool __robot_frog_is_discharged(struct robot_frog* ths)
{
    return ths->charge <= 0;
}

struct robot_frog* robot_frog_create()
{
    struct robot_frog* ths = (struct robot_frog*)malloc(sizeof(struct robot_frog));

    ths->supr = frog_create();

    ths->charge = 5;

    ths->jump = __robot_frog_jump;
    ths->croak = __robot_frog_croak;

    ths->do_charge = __robot_frog_do_charge;
    ths->is_discharged = __robot_frog_is_discharged;

    return ths;
}

void robot_frog_delete(struct robot_frog* ths)
{
    frog_delete(ths->supr);
    free(ths);
}

#endif