import os
import sys
import random


'''

4 8 3 1 6 5 7 2

'''

def main():

	#f = open('IntegerArray.txt','r')
	f = open('test.txt','r')
	result = list()
	for line in open('test.txt'):
		line = f.readline()
		line = line.strip()
		if not len(line) or line.startswith('#'):
			continue
		result.append(line)
	f.close()
	print(result)
	arr = result
	#arrStr = input('Please input a list of numbers:')
	#arr = arrStr.split(' ')
	arr = list(map(int,arr))
	#print(arr)
	arrR, nR = findInversions(arr)
	#print(arrR)
	print(nR)
	

def findInversions(arr):
	n = len(arr)
	arrA = arr[0:int(n/2)]
	arrB = arr[int(n/2):n]
	if n == 1:
		return arr,0
	else:
		arrA,nA = findInversions(arrA)  
		arrB,nB = findInversions(arrB)
		arrL,nL = merge(arrA,arrB)
		return arrL,nA+nB+nL

def merge(arrA, arrB):
	#print(arrA,end='====')
	#print(arrB)
	arrL = arrA + arrB
	nA = len(arrA)
	nB = len(arrB)
	n= nA + nB
	i = 0
	j = 0
	count = 0
	for l in range(n):
		if i >= len(arrA):
			arrL[l] = arrB[j]
			j+=1
		elif j >= len(arrB):
			arrL[l] = arrA[i]
			i+=1
		elif arrA[i] >= arrB[j]:
			arrL[l] = arrB[j]
			# print(arrA[i],end='/')
			# print(arrB[j])
			j+=1
			count = count + (nA-i)
		elif arrA[i] < arrB[j]:
			arrL[l] = arrA[i]
			i+=1
		else:
			print('Error!',end=':')
			print(arrA[i],end='/')
			print(arrB[j])
	return arrL,count




if __name__ == '__main__':
	main()