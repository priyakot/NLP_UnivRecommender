import random
import sys
import fileinput

file1 = sys.argv[1]

#with open(file1, 'rb') as file1:
"""for line in fileinput.FileInput(file1,inplace=1):
	line = line.replace(line.rstrip().split(' ',1)[0],"TEST")"""
	
for line in fileinput.input(file1, inplace=True):
    print(line.replace(line.rstrip().split(' ',1)[0], "TEST"), end='')

		
    	

