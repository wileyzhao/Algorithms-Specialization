import random
'''
bubble sort:'
(n-1)(n-1)=n*n-2n-1
O(n*n)

12 41 4 7 99 45 34 6 31 8 90 54
4  41 12 7
'''

def main():
	for i in range(20):
		x = random.randint(1,100)
		print(x, end=' ')
	print()
	arrStr = input('Please input a list of numbers:')
	arr = arrStr.split(' ')
	arr = list(map(int,arr))
	arrR = bubble_sort(arr)
	print(arrR)

def bubble_sort(arr):
	for i in range(1,len(arr)):
		for j in range(1,len(arr)):
			if arr[j-1] > arr[j]:
				temp = arr[j]
				arr[j] = arr[j-1]
				arr[j-1] = temp
	return arr	



if __name__ == '__main__':
	main()