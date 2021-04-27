#include <stdio.h>

int show(char *str,int output, FILE *fp) {
	if (output == 0) {
		return 0;
	} else if (output == 1) {
		printf("%s\n", str);
	} else if (output == 2) {
		fprintf(fp, "%s\n", str);
	}
	return 0;
}