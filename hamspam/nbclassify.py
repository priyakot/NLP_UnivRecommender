from collections import Counter, defaultdict
import re
import sys
import random
import math
import os
import operator
import pickle

def tokenize(text):
    return re.findall('[a-z0-9]+', text)
    
def classify(line, priors, likelihood, vocabSize, count):

   # print("sum priors: ",sum(priors.values()))
    prob = Counter()
    priorsTotal = sum(priors.values())
    pword = 1.0
    for c in priors.keys():
        n = 0.0
       # p = math.log(priors[c])
        p = priors[c]
        pword = math.log(float(priors[c]/priorsTotal))
       # pclass = math.log(float(priors[c]/priorsTotal))
        n = float(sum(likelihood[c].values()))
        #print('p: ',c,p)
        #print('n: ',n)
      #  print('line: ',line)
        for words in line:
            #print('wordtoken: ',tokenize(word))
            wordtoken = tokenize(words)
            #print('words:' ,wordtoken)
            for word in wordtoken:
            	#print('word: ',word, likelihood[c][word])
            	p = p * ((likelihood[c][word] + 1 ) / (n + vocabSize))
            	pword = pword + math.log((likelihood[c][word] + 1 ) / (n + vocabSize))     
        		#prob[c] = float(pclass * pword)  
            prob[c] = pword   
           #***** print('class ',c,' pword: ', prob[c])
          #  p = p + math.log((likelihood[c][word] + 1 ) / (n + vocabSize))


   # print('prob', prob)
   # print(max(prob), maxC[max(prob)])
   #***** print(count,' key: ',max(prob, key=prob.get),'value: ',max(prob.values()))
    print(max(prob, key=prob.get))
   # x = max(maxC.keys(), key=operator.itemgetter(1))[0]
   # print('correct max: ',x,maxC[x])
   # max_index, max_value = max(enumerate(prob), key=operator.itemgetter(1))
   # print('max class, max value : ',max_index,max_value)
    
   ##*** print('\n\n')
   
    
def readTestingFile(fileName):
    return [line.strip().split('\n') for line in open(fileName,errors='ignore').readlines()]

def main():
	count = 0
	modelFile = sys.argv[1]
	testingFile = sys.argv[2]
	
	"""  CODE TO make one aggregate dev file   
   # outFileName = '/Users/priyakotwal/Documents/sharedUbuntu/544/a1/dev.txt'
	outFile = open('testFile.txt', 'w')
	for subdir1, dirs1, files1 in os.walk(testingFile):
		for file1 in files1:
			fn1 = os.path.join(testingFile, file1)
           # lines1 = readTestingFile(fn1)
			f = open(fn1, 'r', errors='ignore')
			for line1 in f:
				outFile.write(line1.rstrip('\r\n'))
			outFile.write("\n") """

	with open(modelFile, 'rb') as handle:
		priors, likelihood, vocabSize = pickle.load(handle)
		
	#print('priors: ',priors)
	#print('likelihood2', likelihood2)
	#print('vocabSize2: ',vocabSize)
	
	#(priors, likelihood, vocabSize) = readTrainingFile(trainingFile)
	###***print(priors)
	lines = readTestingFile(testingFile)
	#with open(testingFile,errors='ignore') as f:
	    #lines = f.read()
	for line in lines:
	    #print('line1:', line)
	    count = count + 1
	    classify(line, priors, likelihood, vocabSize, count)         
	
""" code to read test files directly from a dir
    for subdir, dirs, files in os.walk(testingFile):
        for file in files:
            fn = os.path.join(testingFile, file)
            lines = readTestingFile(fn)
    
            for line in lines:
                classify(line, priors, likelihood, vocabSize)
           
"""
   
if __name__ == '__main__':
    main()