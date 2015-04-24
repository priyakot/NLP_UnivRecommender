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

def readTrainingFile(fileName):
    priors = Counter()
    likelihood = defaultdict(Counter)
    vocab = defaultdict(int)

    with open(fileName,errors='ignore') as f:
        for line in f:
            label = line.split(' ',1)[0]
            priors[label] += 1
            for word in tokenize(line):
                likelihood[label][word] += 1
                vocab[word] += 1 
    vocabSize = len(vocab)
    return (priors, likelihood , vocabSize)
    
def main():
	count = 0
	trainingFile = sys.argv[1]
	modelFile = sys.argv[2]
	(priors, likelihood, vocabSize) = readTrainingFile(trainingFile)
	with open(modelFile, 'wb') as handle:
		pickle.dump((priors, likelihood, vocabSize), handle)

	

if __name__ == '__main__':
    main()