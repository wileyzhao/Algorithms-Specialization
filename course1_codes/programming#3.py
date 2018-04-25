import os
import math

'''
1/162085
2/164123	
3/138382
'''
def main():
		
	f = open('QuickSort.txt','r')
	#f = open('test.txt','r')
	result = list()
	for line in open('QuickSort.txt'):
	#for line in open('test.txt'):
		line = f.readline()
		line = line.strip()
		if not len(line) or line.startswith('#'):
			continue
		result.append(line)
	f.close()
	arr = result
	#arrStr = input('Please input a list of numbers:')
	#arr = arrStr.split(' ')
	arr = list(map(int,arr))
	#print(arr)
	arrR,countsR = MidianPivotQuickSort_test(arr)

	#firstPivotQuickSort(arr)
	#print(arrR)
	print(countsR)
	

def firstPivotQuickSort(arr):
	#print(arr)
	n = len(arr)
	if n < 2:
		return arr,0
	pivot = arr[0]
	i = 1
	j = 1
	counts = 0
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
		counts+=1
	temp = arr[i-1]
	arr[i-1] = arr[0]
	arr[0] = temp
	arrA,countsA = firstPivotQuickSort(arr[0:i-1])
	arrB,countsB = firstPivotQuickSort(arr[i:n])	
	arrA.append(arr[i-1])
	return arrA + arrB,counts+countsA+countsB

def lastPivotQuickSort_test(arr):
	n = len(arr)
	if n < 2:
		return arr,0
	temp = arr[n-1]
	arr[n-1] = arr[0]
	arr[0] = temp
	pivot = arr[0]
	i = 1
	j = 1
	counts = 0
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
		counts+=1
	temp = arr[i-1]
	arr[i-1] = arr[0]
	arr[0] = temp
	arrA,countsA = lastPivotQuickSort_test(arr[0:i-1])
	arrB,countsB = lastPivotQuickSort_test(arr[i:n])	
	arrA.append(arr[i-1])
	return arrA + arrB,counts+countsA+countsB

def lastPivotQuickSort(arr):
	n = len(arr)
	if n < 2:
		return arr,0
	pivot = arr[n-1]
	i = 0
	j = 0
	counts = 0
	while j < n-1:
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
		counts+=1
	temp = arr[i]
	arr[i] = arr[n-1]
	arr[n-1] = temp
	arrA,countsA = lastPivotQuickSort(arr[0:i])
	arrB,countsB = lastPivotQuickSort(arr[i+1:n])	
	arrA.append(arr[i])
	return arrA + arrB,counts+countsA+countsB


def MidianPivotQuickSort_test(arr):
	n = len(arr)
	if n < 2:
		return arr,0
	#find out the first,last and the middle.then choose the second largest(the second smallest,too) one,and consider this one is the pivot
	pi = (n-1) // 2
	first = arr[0]
	last = arr[n-1]
	mid = arr[pi]
	
	if (first - mid)*(first - last) < 0:
		pi = 0
	elif (mid - first)*(mid - last) < 0:
		pi = pi
	else:
		pi = n-1
		
	if pi != 0:
		temp = arr[pi]
		arr[pi] = arr[0]
		arr[0] = temp

	pivot = arr[0]
	i = 1
	j = 1
	counts = 0
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
		counts+=1
	temp = arr[i-1]
	arr[i-1] = arr[0]
	arr[0] = temp
	arrA,countsA = MidianPivotQuickSort_test(arr[0:i-1])
	arrB,countsB = MidianPivotQuickSort_test(arr[i:n])	
	arrA.append(arr[i-1])
	return arrA + arrB,counts+countsA+countsB


def MidianPivotQuickSort(arr):
	n = len(arr)
	if n < 2:
		return arr,0
	pi = (n-1) // 2
	pivot = arr[pi]
	i = 0
	j = 0
	counts = 0
	while j < n:
		if pivot <= arr[j]:
			j+=1
		elif i == j:
			i+=1
			j+=1
		else:
			if i == pi:
				pi = j
			temp = arr[i]
			arr[i] = arr[j]
			arr[j] = temp
			i+=1
			j+=1
		counts+=1
	temp = arr[i]
	arr[i] = arr[pi]
	arr[pi] = temp
	arrA,countsA = MidianPivotQuickSort(arr[0:i])
	arrB,countsB = MidianPivotQuickSort(arr[i+1:n])	
	arrA.append(arr[i])
	return arrA + arrB,counts+countsA+countsB


if __name__ == '__main__':
		main()
	