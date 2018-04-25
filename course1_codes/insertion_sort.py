import random
'''
insertion sort:'
(n-1)!
O(n*n)
12 41 14 7 99 45 34 6 31 8 90 54

12 41 99	140
		41	99
'''

def main():
	for i in range(20):
		x = random.randint(1,100)
		print(x, end=' ')
	print()
	arrStr = input('Please input a list of numbers:')
	arr = arrStr.split(' ')
	arr = list(map(int,arr))
	print(arr)
	arrR = insertion_sort(arr)
	print(arrR)


def insertion_sort(arr):
	for i in range(1,len(arr)):
		k = arr[i]
		for j in range(i-1, -1, -1):
			if k < arr[j]:
				arr[j+1] = arr[j]
			else:
				j+=1
				break
		arr[j] = k
	
		#print(arr)
	return arr



if __name__ == '__main__':
	main()