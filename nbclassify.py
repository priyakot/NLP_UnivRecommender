from collections import Counter, defaultdict
import re
import sys
import random
import math
import os
import operator
import pickle

def tokenize(text):
	return text.split(' ')
    #return re.findall('[a-z0-9]+', text)
    
def classify(line, priors, likelihood, vocabSize, count):

    prob = Counter()
    priorsTotal = sum(priors.values())
    pword = 1.0
    for c in priors.keys():
        n = 0.0
        p = priors[c]
        univ1 = ""
        univ1Out1 = ""
        univ1Out2 = ""
        univ2 = ""
        univ1Out2 = ""
        univ1Out2 = ""
        univ3 = ""
        univ1Out3 = ""
        univ1Out3 = ""
        
        pword = math.log(float(priors[c]/priorsTotal))
        n = float(sum(likelihood[c].values()))
        print(n)
        """
        for words in line:
            wordtoken = tokenize(words)
            for word in wordtoken:
            	p = p * ((likelihood[c][word] + 1 ) / (n + vocabSize))
            	pword = pword + math.log((likelihood[c][word] + 1 ) / (n + vocabSize))     
        		#prob[c] = float(pclass * pword)  
            prob[c] = pword   
		"""
	
   # print('prob', prob)
   # print(max(prob), maxC[max(prob)])
   #***** print(count,' key: ',max(prob, key=prob.get),'value: ',max(prob.values()))
    #print(1,' ',max(prob, key=prob.get),' ',2,' ',sorted(prob, key=prob.get)[-2],' ',3,' ',sorted(prob, key=prob.get)[-3])
    univ1 = max(prob, key=prob.get)
    univ2 = sorted(prob, key=prob.get)[-2]
    univ3 = sorted(prob, key=prob.get)[-3]
    univ1Out1 = univ1.split("_")[0]
    univ1Out2 = univ1.split("_")[1]
    univ2Out1 = univ2.split("_")[0]
    univ2Out2 = univ2.split("_")[1]
    univ3Out1 = univ3.split("_")[0]
    univ3Out2 = univ3.split("_")[1]
    
    
    print('Profile: ','[%s]' % ', '.join(map(str, line)))
    if univ1Out2 == "Admit":
    	print("Safe University:",univ1Out1,' ', end="")
    if univ1Out2 == "Reject":
    	print("Ambitious University:",univ1Out1,' ',end="")
    if univ2Out2 == "Admit":
    	print("Safe University:",univ2Out1,' ',end="")
    if univ2Out2 == "Reject":
    	print("Ambitious University:",univ2Out1,' ',end="")
    if univ3Out2 == "Admit":
    	print("Safe University:",univ3Out1,' ',end="")
    if univ3Out2 == "Reject":
    	print("Ambitious University:",univ3Out1,' ',end="")
    print("\n")
    #import heapq
	#res = heapq.nlargest(2, some_sequence)
	#print res[1]
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
	
	with open(modelFile, 'rb') as handle:
		priors, likelihood, vocabSize = pickle.load(handle)

	lines = readTestingFile(testingFile)
	print("classes: ",len(priors), "likeli", likelihood.values())
	for line in lines:
	    count = count + 1
	    classify(line, priors, likelihood, vocabSize, count)        
	
   
if __name__ == '__main__':
    main()