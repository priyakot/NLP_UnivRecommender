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
        for words in line:
            wordtoken = tokenize(words)
            for word in wordtoken:
            	p = p * ((likelihood[c][word] + 1 ) / (n + vocabSize))
            	pword = pword + math.log((likelihood[c][word] + 1 ) / (n + vocabSize))     
        		#prob[c] = float(pclass * pword)  
            prob[c] = pword   
    print(max(prob, key=prob.get))



def classifyInput(line, priors, likelihood, vocabSize, count):

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
        univ2Out1 = ""
        univ2Out2 = ""
        univ3 = ""
        univ3Out1 = ""
        univ3Out2 = ""
        univ4 = ""
        univ4Out1 = ""
        univ4Out2 = ""
        
        pword = math.log(float(priors[c]/priorsTotal))
        n = float(sum(likelihood[c].values()))
        wordtoken1 = line.split(' ')
        line1 = "prg:"+wordtoken1[0]+" "+"awa:"+wordtoken1[1]+" "+"qnt:"+wordtoken1[2]+" "+"vrb:"+wordtoken1[3]
        wordtoken = line1.split(' ')
        for word in wordtoken:
            p = p * ((likelihood[c][word] + 1 ) / (n + vocabSize))
            pword = pword + math.log((likelihood[c][word] + 1 ) / (n + vocabSize))     
        prob[c] = pword   
    univ1 = max(prob, key=prob.get)
    univ2 = sorted(prob, key=prob.get)[-2]
    univ3 = sorted(prob, key=prob.get)[-3]
    univ4 = sorted(prob, key=prob.get)[-4]
    univ5 = sorted(prob, key=prob.get)[-5]
    
    univ1Out1 = univ1.split("_")[0]
    univ1Out2 = univ1.split("_")[1]
    univ2Out1 = univ2.split("_")[0]
    univ2Out2 = univ2.split("_")[1]
    univ3Out1 = univ3.split("_")[0]
    univ3Out2 = univ3.split("_")[1]
    univ4Out1 = univ4.split("_")[0]
    univ4Out2 = univ4.split("_")[1]
    univ5Out1 = univ5.split("_")[0]
    univ5Out2 = univ5.split("_")[1]
    
    
    print('Profile:','[%s]' % ', '.join(map(str, wordtoken)))
    if univ1Out2 == "Admit":
    	print("Safe University:     ",univ1Out1)
    if univ1Out2 == "Reject":
    	print("Ambitious University:",univ1Out1)
    if univ2Out2 == "Admit" and univ1Out1 != univ2Out1:
    	print("Safe University:     ",univ2Out1)
    if univ2Out2 == "Reject" and univ1Out1 != univ2Out1:
    	print("Ambitious University:",univ2Out1)
    if univ3Out2 == "Admit" and univ3Out1 != univ1Out1 and univ2Out1 != univ3Out1:
    	print("Safe University:     ",univ3Out1)
    if univ3Out2 == "Reject" and univ3Out1 != univ1Out1 and univ2Out1 != univ3Out1:
    	print("Ambitious University:",univ3Out1)
    if univ4Out2 == "Admit" and univ4Out1 != univ1Out1 and univ4Out1 != univ2Out1 and univ4Out1 != univ3Out1:
    	print("Safe University:     ",univ4Out1)
    if univ4Out2 == "Reject" and univ4Out1 != univ1Out1 and univ4Out1 != univ2Out1 and univ4Out1 != univ3Out1:
    	print("Ambitious University:",univ4Out1)
    if univ5Out2 == "Admit" and univ5Out1 != univ1Out1 and univ5Out1 != univ2Out1 and univ5Out1 != univ3Out1 and univ5Out1 != univ4Out1:
    	print("Safe University:     ",univ5Out1)
    if univ5Out2 == "Reject" and univ5Out1 != univ1Out1 and univ5Out1 != univ2Out1 and univ5Out1 != univ3Out1 and univ5Out1 != univ4Out1:
    	print("Ambitious University:",univ5Out1)
    
    
def readTestingFile(fileName):
    return [line.strip().split('\n') for line in open(fileName,errors='ignore').readlines()]

def main():
	count = 0
	argsLength = len(sys.argv)
	modelFile = sys.argv[1]
	if argsLength == 3:
		testingFile = sys.argv[2]

	with open(modelFile, 'rb') as handle:
			priors, likelihood, vocabSize = pickle.load(handle)
	
	if argsLength == 2:
		print('Enter the data: Program AWA QNT VRB ')
		data = input()
		
		classifyInput(data, priors, likelihood, vocabSize, count) 
	else:
		lines = readTestingFile(testingFile)

		for line in lines:
			count = count + 1
			classify(line, priors, likelihood, vocabSize, count)         
	
   
if __name__ == '__main__':
    main()