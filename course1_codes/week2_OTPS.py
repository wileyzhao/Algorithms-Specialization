import os
import sys
import random

'''
week 2
Optional Theory Problems

good question,good theory.
'''


def main():
	'''
	#question one start
	n = input("Please input the number n (such as 2**n):")
	for i in range(2**int(n)):
		x = random.randint(1,100)
		print(x, end=' ')
	print('')
	arrStr = input('Please input a list of numbers:')
	arr = arrStr.split(' ')
	arr = list(map(int,arr))
	maxR,arrR = question_No1(arr)
	print(maxR)
	print(arrR)
	second = getLargest(arrR)
	print(second)
	#question one end
	'''
	'''
	#question two
	arrStr = input('Please input a list of numbers:')
	arr = arrStr.split(' ')
	arr = list(map(int,arr))
	maxR = question_No2(arr)
	print(maxR)
	'''
	#question three
	# arrStr = input('Please input a list of numbers:')
	# arr = arrStr.split(' ')
	# arr = list(map(int,arr))
	# maxR = question_No3(arr,0)
	# print(maxR)

	# myList = [([random.randint(1,10)] * 4) for i in range(4)]
	# myList[1][0] = 1
	# for i in myList:
	# 	print(i)

	# print(len(myList))
	
	n = int(input("Please input the number n (such as 2**n):"))
	randlist = random.sample(range(1,100),n*n)
	print(randlist)
	matrix = [([random.randint(1,10)] * n) for i in range(n)]
	for i in range(n):
		matrix[i] = randlist[i*n:n*(i+1)]
	print('---------')
	for i in matrix:
		print(i)
	x,y = question_No4_bak(matrix)
	print('========')
	print(x,y)

	'''
	get the second largest number of the list
	'''
def question_No1(arr):
	n = len(arr)
	if n == 1:
		return arr[0],[]
	arrA = arr[0:int(n/2)]
	arrB = arr[int(n/2):n]
	
	maxA,LA = question_one(arrA)  
	maxB,LB = question_one(arrB)

	if maxA > maxB:
		LA.append(maxB)
		return maxA,LA
	else:
		LB.append(maxA)
		return maxB,LB

def getLargest(LL):
	large = LL[0]
	n = len(LL)
	for i in range(1,n):
		if large < LL[i]:
			large = LL[i]
	return large;


#run time is O(logN)
def question_No2(arr):
	n = len(arr)
	print(arr)
	if n == 1:
		return arr[0]
	elif n == 2:
		if arr[0] > arr[1]:
			return arr[0]
		else:
			return arr[1]
	else:
		center = arr[int(n/2)]
		if center > arr[int(n/2)-1] and center > arr[int(n/2)+1]:
			return center
		elif center > arr[int(n/2)-1]:
			return question_No2(arr[int(n/2):n])
		else:
			return question_No2(arr[0:int(n/2)])

#run time is logN
def question_No3(arr,i):
	# print(arr,end='/')
	# print(i)
	n = len(arr)
	if n == 1:
		if arr[0] == i:
			return True
		else:
			return False
	center = arr[int(n/2)]
	if center < int(n/2)+i:
		return question_No3(arr[int(n/2):n],int(n/2)+i)
	elif center > int(n/2)+i:
		return question_No3(arr[0:int(n/2)],i)
	else:
		return True

'''
43  66	4	33	7	77
47  3	24	5	8	12
78	29 	37	26	33	9 
96	18	79	1	86	39
40	52	56	78	29	27
81	50	73	62	27	44 
'''
def question_No4(matrix):
	n = len(matrix)
	x,y = findLocalMinimum(matrix,int(n/2))
	return x,y

def findLocalMinimum(matrix,m):
	n = len(matrix)
	minimum,index = getSmallestINdex(matrix[m])
	print(minimum,index)
	if m == 0:
		if minimum < matrix[m+1][index]:
			return m,index
		else:
			return findLocalMinimum(matrix,m+1)
	elif m == n-1:
		if minimum < matrix[m-1][index]:
			return m,index
		else:
			return findLocalMinimum(matrix,m-1)
	else:
		if minimum < matrix[m-1][index] and minimum < matrix[m+1][index]:
			return m,index
		elif matrix[m-1][index] > matrix[m+1][index]:
			return findLocalMinimum(matrix,m+1)
		else:
			return findLocalMinimum(matrix,m-1)
	return -1,-1

def getSmallestINdex(LL):
	minimum = LL[0]
	n = len(LL)
	for i in range(1,n):
		if minimum > LL[i]:
			minimum = LL[i]
			index = i
	return minimum,index;


def question_No4_bak(matrix):
	n = len(matrix)
	# x = int(n/2)
	# y = int(n/2)
	x = 0
	y = 0
	return getSmall(matrix,x,y,0)


def getSmall(matrix,x,y,type):
	print(x,y,type)
	n = len(matrix)
	if type == 0:
		if x == 0:
			if matrix[x][y] < matrix[x+1][y]:
				return getSmall(matrix,x,y,1)
			else:
				return getSmall(matrix,x+1,y,0)
		elif x == n-1:
			if matrix[x][y] < matrix[x-1][y]:
				return getSmall(matrix,x,y,1)
			else:
				return getSmall(matrix,x-1,y,0)
		else:
			if matrix[x][y] < matrix[x-1][y] and matrix[x][y] < matrix[x+1][y]:
				return getSmall(matrix,x,y,1)
			elif matrix[x-1][y] < matrix[x+1][y]:
				return getSmall(matrix,x-1,y,0)
			else:
				return getSmall(matrix,x+1,y,0)
	else:
		if y == 0:
			if matrix[x][y] < matrix[x][y+1]:
				return x,y
			else:
				return getSmall(matrix,x,y+1,0)
		elif y == n-1:
			if matrix[x][y] < matrix[x][y-1]:
				return x,y
			else:
				return getSmall(matrix,x,y-1,0)
		else:
			if matrix[x][y] < matrix[x][y-1] and matrix[x][y] < matrix[x][y+1]:
				return x,y
			elif matrix[x][y-1] < matrix[x][y+1]:
				return getSmall(matrix,x,y-1,0)
			else:
				return getSmall(matrix,x,y+1,0)


if __name__ == '__main__':
	main()