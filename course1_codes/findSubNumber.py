import os

'''
给定一个int数组和一个int数字，判断数组中是否存在两个数相加之和等于给定的数字
isExistSameElement
'''

def main():
	arrInput = input('Input the array number:')
	number = int(input('Input the number:'))

	#(arrInput)
	arrInput = arrInput.split(' ')
	arrInput = list(map(int, arrInput))

	arrInput = mergeSort(arrInput)
	print(arrInput)
	newInput = list()
	for i in range(len(arrInput)-1):
		if arrInput[i] == number - arrInput[i] and arrInput[i] == arrInput[i+1]:
			print("True")
			return 0
		elif newInput.count(arrInput[i]) < 1:
			newInput.append(arrInput[i])
		else:
			continue
	print(newInput)
	newArray = list()
	for i in newInput:
		newArray.append(number - i)
	newArray.reverse()
	print(newArray)
	
	isExist = isExistSameElement(newInput,newArray)
	print(isExist)



def mergeSort(arr):
	n = len(arr)
	arrA = arr[0:int(n/2)]
	arrB = arr[int(n/2):n]
	if n > 2:
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


def isExistSameElement(arrA, arrB):
	n = len(arrA) + len(arrB)
	i = 0
	j = 0
	for l in range(n):
		if i >= len(arrA):
			j+=1
		elif j >= len(arrB):
			i+=1
		elif arrA[i] > arrB[j]:
			j+=1
		elif arrA[i] < arrB[j]:
			i+=1
		elif arrA[i] == arrB[j]:
			return True
		else:
			print('Error!',end=':')
			print(arrA[i],end='/')
			print(arrB[j])
	return False


if __name__ == '__main__':
	main()