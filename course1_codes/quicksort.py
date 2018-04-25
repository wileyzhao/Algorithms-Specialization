import os
import sys
import random

'''
quick sort
running time is O(nlogN)
'''

def main():
	for i in range(20):
		x = random.randint(1,100)
		print(x, end=' ')
	print('')

	arrStr = input('Please input a list of numbers:')

	arr = arrStr.split(' ')
	arr = list(map(int,arr))
	arrR = quickSort(arr)
	print(arrR)


def quickSort(arr):
	n = len(arr)
	if n < 2:
		return arr
	pivot = arr[0]
	i = 1
	j = 1
	while j < n:
		if pivot < arr[j]:
			j+=1
		elif i == j:
			i+=1
			j+=1
		else:
			temp = arr[i]
			arr[i] = arr[j]
			arr[j] = temp
			i+=1
			j+=1
	temp = arr[i-1]
	arr[i-1] = arr[0]
	arr[0] = temp
	arrA = quickSort(arr[0:i-1])
	arrB = quickSort(arr[i:n])	
	arrA.append(arr[i-1])
	return arrA + arrB

if __name__ == '__main__':
	main()