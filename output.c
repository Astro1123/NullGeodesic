#include <stdio.h>

int show(char *str,int output, FILE *fp) {
	if (output == 0) {
		return 0;
	} else if (output == 1) {
		printf("%s", str);
	} else if (output == 2) {
		fprintf(fp, "%s", str);
	}
	return 0;
}