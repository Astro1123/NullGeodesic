#ifdef 	__MACH__
#include <GLUT/glut.h>					// macOS
#else
#include <GL/glut.h>					// Windows
#endif

#include <stdlib.h>
#include <math.h>
#include <stdio.h>
#include <string.h>

#include "calc.h"                   // 計算関係
#include "bool.h"                   // bool型の定義
#include "ccalc.h"                  // 複素数を用いた計算
#include "output.h"                // 出力関係

// ウィンドウのサイズ
#define WindowX 960.0
#define WindowY 620.0

// ウィンドウの場所
#define PosX 100
#define PosY 100

#define sqrt3 1.732050807568877

double mag;											// この値が大きいほど図が拡大される
double lx, ly;           								// 光源の位置 (シュバルツシルト半径：r_g = 1.0)
double deg; 											// 光を放つ角度[deg]
double dt;  											// 時間の刻み幅
int lcount;												// ループ回数
int nacount;        									// ループ回数
double ddeg;										// ループごとに変化させる角度
double dy;          									// ループごとに変化させるy座標
double b;												// 衝突パラメータ
int sign;									    		// 符号 (ブラックホールに近づくとき+1)
double epsc;
int type;												// 0:角度を変化、1:y座標を変化
int cf;
int output;                                            // 出力(0：出力しない、1：terminalに出力、2：ファイルに出力)
bool output_r = false;                          // 計算結果の出力
char filename[64];                               // 出力するファイル名
FILE *fp;

int input(void) {
    FILE *f;
    char str[128];
    f = fopen("data.txt","r");
    if (f == NULL) {
        printf("ファイルを開けませんでした\n");
        return -1;
    }
    fgets(str, sizeof(str), f);	
    mag = atof(str);
    fgets(str, sizeof(str), f);	
    lx = atof(str);
    fgets(str, sizeof(str), f);	
    ly = atof(str);
    fgets(str, sizeof(str), f);	
    deg = atof(str);
    fgets(str, sizeof(str), f);	
    dt = atof(str);
    fgets(str, sizeof(str), f);	
    lcount = atoi(str);
    fgets(str, sizeof(str), f);	
    nacount = atoi(str);
    fgets(str, sizeof(str), f);	
    ddeg = atof(str);
    fgets(str, sizeof(str), f);	
    dy = atof(str);
    fgets(str, sizeof(str), f);	
    sign = atoi(str);
    fgets(str, sizeof(str), f);	
    type = atoi(str);
    fgets(str, sizeof(str), f);	
    output = atoi(str);
    fgets(str, sizeof(str), f);	
    cf = atoi(str);
    fgets(str, sizeof(str), f);	
    strcpy(filename,str);
    fclose(f);
    return 0;
} 

// 正n角形を描く（nを大きくすると円）
// 半径 radius、中心 ( x, y )
void Circle2D(double radius, double x, double y, int n) {
  int i;
  double th1, th2, x1, x2, y1, y2;
  glBegin(GL_LINE_LOOP);
  for (i=0;  i < n;  i++) {
  	th1 = 2.0 * M_PI / n * i;
 	th2 = 2.0 * M_PI / n * (i + 1);
    
    x1 = radius * cos(th1) * mag;
    y1 = radius * sin(th1) * mag;
    x2 = radius * cos(th2) * mag;
    y2 = radius * sin(th2) * mag;

    glVertex2d( x1+x, y1+y );     
    glVertex2d( x2+x, y2+y );
  }
  glEnd();
}

// 十字を描く
// 大きさ size、中心 ( x, y )
void PrPlus(double size, double x, double y) {
  glBegin(GL_LINES);
  glVertex2d(-size*mag+x*mag, y*mag);
  glVertex2d( size*mag+x*mag, y*mag);
  glEnd();
  glBegin(GL_LINES);
  glVertex2d( x*mag, size*mag+y*mag);
  glVertex2d( x*mag,-size*mag+y*mag);
  glEnd();
}

// 線を引く
// ( x1, y1 ) -> ( x2 , y2 )
void PrLine(double x1, double y1, double x2, double y2) {
  glBegin(GL_LINES);
  glVertex2d(x1*mag, y1*mag);
  glVertex2d(x2*mag, y2*mag);
  glEnd();
}

void display(void) {
  double r, x, y, x0, y0, r0, phi0, theta0, phi, rmin;
  double dr, dph;
  int i, j;
  char str[64];
  
  glClear(GL_COLOR_BUFFER_BIT);
  
  glColor3d(0.0, 0.0, 0.0);
  Circle2D(1.0, 0, 0, 100);				         		// 事象の地平面
  PrPlus(0.1, 0, 0);					        				// 特異点
  
  glColor3d(0.0, 0.0, 1.0);
  sprintf(str, "刻み幅 (dt)：%f\n", dt);
  show(str, output, fp);
  sprintf(str, "loop回数：%d\n", lcount);
  show(str, output, fp);
  if(type == 0) {
  	PrPlus(0.1, lx, ly);			          					// 光源
  	sprintf(str, "光源：(%f, %f)\n", lx, ly);
  	show(str, output, fp);
  	sprintf(str, "ddeg：%f\n", ddeg);
  	show(str, output, fp);
  } else if(type == 1) {
  	for(i=0; i<lcount; i++)
  		PrPlus(0.1, lx, ly+i*dy);	         				// 光源
  	sprintf(str, "dy：%f\n", dy);
  	show(str, output, fp);
  }
  
  sprintf(str,"\n");
  show(str, output, fp);
  
	for(i=0; i<lcount; i++) {
		if (type == 0) {
  			x = lx;
 			y = ly;
			theta0 = deg2rad(deg + ddeg * i);
			sprintf(str, "光源(%d)：(%f, %f)\n", i+1, lx, ly);
  	        show(str, output, fp);
			sprintf(str, "deg(%d)：%f\n", i+1, deg + ddeg * i);
  	        show(str, output, fp);
		} else if (type == 1) {
  			x = lx;
 			y = ly+dy*i;
			theta0 = deg2rad(deg);
			sprintf(str, "光源(%d)：(%f, %f)\n", i+1, lx, ly + dy*i);
  	        show(str, output, fp);
			sprintf(str, "deg(%d)：%f\n", i+1, deg);
  	        show(str, output, fp);
		}
		xy2rp(&r0, &phi0, x, y);
		b = r0 * sin(phi0 + theta0);
	    if (b < 1.4999*sqrt(3) && cf == 1) {
	        glColor3d(0.75, 0.0, 0.0);
	    } else {
	        glColor3d(1.0, 0.0, 0.0);
	    }
	    
		sign = 1;
		r = r0;
		phi = phi0;
		rmin = r_minimum(b);
		sprintf(str, "衝突パラメータ (b)：%f\n",b);
  	    show(str, output, fp);
		sprintf(str, "r_min：%f\n",rmin);
  	    show(str, output, fp);
  	    if (output_r == true) {
		    sprintf(str, "------------------------------\n");
  	        show(str, output, fp);
  	    }
		for(j = 0; j < nacount; j++) {
			RungeKutta(&dr, &dph, r, b, dt);
			if (output_r == true) {
			    sprintf(str, "%f , %f / %f , %f\n", x , y, distance(x, y), angle(x, y) );
  	            show(str, output, fp);
			}
			if (r + dr * sign < 1.0 + epsc) {
			 	r += dr * sign;
 				phi += dph;
 				x0 = x;
 				y0 = y;
 				rp2xy(&x, &y, r, phi);
 			 	PrLine(x, y, x0, y0);
 			 	break;
 			 } 
 			 if (f(r + dr * sign, b)<0) {
 			 	sign *= -1;
 			 } 
 			 r += dr * sign;
 			 phi += dph;
 			 x0 = x;
 			 y0 = y;
 			 rp2xy(&x, &y, r, phi);
 			 PrLine(x, y, x0, y0);
 			 if (x*mag > WindowX / 200 + 0.1 || y*mag > WindowY / 200 + 0.1 || x*mag < -WindowX / 200 - 0.1 || y*mag < -WindowY / 200 - 0.1) {
	 		 	break;
	 		 }
		}
		if (output_r == true) {
		    sprintf(str, "%f , %f / %f , %f\n", x , y, distance(x, y), angle(x, y) );
  	        show(str, output, fp);
		}
		sprintf(str, "------------------------------\n");
  	    show(str, output, fp);
	}
  
  glFlush();
}

void resize(int w, int h) {
  /* ウィンドウ全体をビューポートにする */
  glViewport(0, 0, w, h);
  /* 変換行列の初期化 */
  glLoadIdentity();
  /* スクリーン上の表示領域をビューポートの大きさに比例させる */
  glOrtho(-w / 200.0, w / 200.0, -h / 200.0, h / 200.0, -1.0, 1.0);
}

void init(void) {
  glClearColor(1.0, 1.0, 1.0, 1.0);
}


void keyboard(unsigned char key, int x, int y) {
  switch(key) {
        case 27  : exit(0); break;               // ESC キーで終了
    }
}

int main(int argc, char *argv[]) {
  if (input() == -1) return -1;
  if (type != 1)
  	type = 0;
  if (output != 1 && output != 2)
    output = 0;
  if(output==2) {
      fp = fopen(filename,"w");
      if (fp == NULL) {
        printf("ファイルを開けませんでした\n");
        return -1;
      }
  }
  glutInit(&argc, argv);
  glutInitDisplayMode(GLUT_RGBA);
  glutInitWindowPosition(PosX, PosY);
  glutInitWindowSize(WindowX, WindowY);
  glutCreateWindow(argv[0]);
  glutDisplayFunc(display);
  init();
  glutReshapeFunc(resize);
  glutKeyboardFunc(keyboard);
  glutMainLoop();
  if(output==2)
    fclose(fp);
  return 0;
}