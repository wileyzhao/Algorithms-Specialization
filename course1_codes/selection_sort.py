import random
'''
selection sort:'
n*n - n
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
	arrR = selection_sort(arr)
	print(arrR)


def selection_sort(arr):
	for i in range(len(arr)):
		k = arr[i]
		for j in range(i+1,len(arr)):
			if k > arr[j]:
				temp = k
				k = arr[j]
				arr[j] = temp
			j+=1
		arr[i] = k
	return arr



if __name__ == '__main__':
	main()