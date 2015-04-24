import random
import sys

file1 = sys.argv[1]
file2 = sys.argv[2]
"""with open(file1,'r') as source:
    data = [ (random.random(), line) for line in source ]
data.sort()
with open(file2,'w') as target:
    for _, line in data:
        target.write( line )"""
        
with open(file1, 'rb') as infile:
    lines = infile.readlines()

random.shuffle(lines)

with open(file2, 'wb') as outfile:
    outfile.writelines(lines)