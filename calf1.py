from collections import Counter
import sys

#arg1 correct output arg2 is obtained output
file1 = sys.argv[1]
file2 = sys.argv[2]
rf1 = open(file1)
rf2 = open(file2)
correct = 0
error = 0
total = 0
count1 = 0
count2 = 0
accuracy = 0.0

label1 = Counter()
label2 = Counter()


for line1 in rf1:
    text1 = line1.rstrip().split(' ',1)[0]
    label1[count1] = text1
    count1 = count1 + 1

for line2 in rf2:
    text2 = line2.rstrip().split(' ',1)[0]
    label2[count2] = text2
    count2 = count2 + 1
print("l1 len",len(label1))
print("l2 len",len(label2))


for i in range(0,len(label1),1):
    """print(i, 'label1 ',label1[i])
    print(i, 'label2 ',label2[i])"""
    print(label1[i],' ',label2[i])
    if label1[i] == label2[i]: 
        correct = correct + 1
    else:
    	error = error + 1
    total = total + 1


#fscoreHAM = float(2* ((precisionHAM*recallHAM)/(precisionHAM+recallHAM)))   
#fscoreSPAM = float(2* ((precisionSPAM*recallSPAM)/(precisionSPAM+recallSPAM)))    

accuracy = float(correct/total)
    
print("Accuracy: ",accuracy,' correct: ', correct, ' total: ',total)


  
 