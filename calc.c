#include <math.h>
#include <stdlib.h>

// dr / d(ct)
double rd(double r, double b) {
	return (r - 1.0) / r * sqrt( ( r*r*r - r*b*b + b*b) / (r*r*r) );
}

// d(phi) / d(ct)
double pd(double r, double b) {
	return (r - 1.0) / r * b / r / r;
}

// 距離
double distance(double x, double y) {
	return sqrt( x * x + y * y );
}

// 角度
double angle(double x, double y) {
	return atan2(y, x);
}

// deg から rad へ
double deg2rad(double deg) {
	return M_PI * deg / 180.0;
}

// 極座標系から直交座標系へ
void rp2xy(double *x, double *y, double r, double phi) {
	*x = r * cos(phi);
	*y = r * sin(phi);
}

// 直交座標系から極座標系へ
void xy2rp(double *r, double *phi, double x, double y) {
	*r = distance(x, y);
	*phi = atan2(y, x);
}

double f(double x, double b) {
	return x*x*x - x*b*b + b*b;
}

// 4次Runge-Kutta法
void RungeKutta(double *dr, double *dph, double r0, double b, double dt) {
	double r;
	double k1, k2, k3, k4;
	double l1, l2, l3, l4;
	r = r0;
	k1 = rd(r, b);
	l1 = pd(r, b);
	r = r0 + k1 * dt / 2.0;
	k2 = rd(r, b);
	l2 = pd(r, b);
	r = r0 + k2 * dt / 2.0;
	k3 = rd(r, b);
	l3 = pd(r, b);
	r = r0 + k3 * dt;
	k4 = rd(r, b);
	l4 = pd(r, b);
	*dr = -dt * (k1 + k2 * 2.0 + k3 * 2.0 + k4) / 6.0;
	*dph = dt * (l1 + l2 * 2.0 + l3 * 2.0 + l4) / 6.0;
}