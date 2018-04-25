import os
import sys
import random
'''
merge sort:
O(nlng n)

'''

def main():

	for i in range(20):
		x = random.randint(1,100)
		print(x, end=' ')
	print('')

	arrStr = input('Please input a list of numbers:')

	arr = arrStr.split(' ')
	arr = list(map(int,arr))
	arrR = mergeSort(arr)
	print(arrR)


def mergeSort(arr):

	n = len(arr)
	arrA = arr[0:int(n/2)]
	arrB = arr[int(n/2):n]
	if n > 2:
		#print(arrA)
		#print(arrB)
		arrA = mergeSort(arrA)  
		arrB = mergeSort(arrB)
	
	return merge(arrA,arrB)

def merge(arrA, arrB):
	arrL = arrA + arrB
	n = len(arrL)
	i = 0
	j = 0
	for l in range(n):
		if i >= len(arrA):
			arrL[l] = arrB[j]
			j+=1
		elif j >= len(arrB):
			arrL[l] = arrA[i]
			i+=1
		elif arrA[i] >= arrB[j]:
			arrL[l] = arrB[j]
			j+=1
		elif arrA[i] < arrB[j]:
			arrL[l] = arrA[i]
			i+=1
		else:
			print('Error!',end=':')
			print(arrA[i],end='/')
			print(arrB[j])
	return arrL


if __name__ == '__main__':
	main()