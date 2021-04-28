#ifndef CALC_H
#define CALC_H

double rd(double r, double b);
double pd(double r, double b);
double deg2rad(double deg);
double distance(double x, double y);
double angle(double x, double y);
void rp2xy(double *x, double *y, double r, double phi);
void xy2rp(double *r, double *phi, double x, double y);
void RungeKutta(double *dr, double *dph, double r0, double b, double dt);
double distance(double x, double y);
double f(double x, double b);

#endif // CALC_H