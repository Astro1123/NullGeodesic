#include <math.h>
#include <complex.h>

double _Complex cubic_root_c(double _Complex z) {
	return cpow(z, 1.0 / 3.0);
}

// 三次方程式の解 ( a x^3 + b x^2 + c x + d = 0)
void cubic_equation(double a, double b, double c, double d, double _Complex *solution) {
	int i;
	double u, v, x, y;
	double p, q;
	double A, D;
	double _Complex S, T;
	double _Complex omega;
	omega = cexp(2.0 * M_PI / 3.0);
	p = -(b * b - 3.0 * a * c) / (3.0 * a * a);
	q = (2.0 * b * b * b - 9.0 * a * b * c + 27.0 * a * a * d) / (27.0 * a * a * a);
	A = -27.0 * q / 2.0;
	D = pow(27.0 * q / 2.0, 2.0) + 27.0 * p * p * p;
	S = cubic_root_c(A + csqrt(D));
	T = cubic_root_c(A - csqrt(D));
	solution[0] = (cpow(omega, 2.0) * S + omega * T) / 3.0;
	solution[1] = (omega * S + cpow(omega, 2.0) * T) / 3.0;
	solution[2] = (S +  T) / 3.0;
}

double r_minimum(double b) {
	double _Complex solution[3];
	double a, max;
	double eps = 0.0000001;	
	int i;
	max = 0.0;
	cubic_equation(1.0, 0.0, -b * b, b * b, solution);
	for(i=0; i<3; i++) {
		if (fabs(cimag(solution[i])) < eps)					// 虚部の値がepsよりも小さいなら虚部は0と判定
			a = creal(solution[i]);
		else
			a = 0.0;
		if (max < a)
			max = a;
	}
	return max;
}