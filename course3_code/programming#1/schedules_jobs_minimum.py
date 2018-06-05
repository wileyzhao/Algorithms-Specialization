import os
'''
assignment #1: 69119377652
assignment #2: 67311454237
1.In this programming problem and the next you’ll code up the greedy algorithms from lecture for minimizing the weighted sum of completion times..
Download the text file below.
jobs.txt
This file describes a set of jobs with positive and integral weights and lengths. It has the format
[number_of_jobs]
[job_1_weight] [job_1_length]
[job_2_weight] [job_2_length]
…
For example, the third line of the file is “74 59”, indicating that the second job has weight 74 and length 59.
You should NOT assume that edge weights or lengths are distinct.
Your task in this problem is to run the greedy algorithm that schedules jobs in decreasing order of the difference (weight - length). Recall from lecture that this algorithm is not always optimal. IMPORTANT: if two jobs have equal difference (weight - length), you should schedule the job with higher weight first. Beware: if you break ties in a different way, you are likely to get the wrong answer. You should report the sum of weighted completion times of the resulting schedule — a positive integer — in the box below.
ADVICE: If you get the wrong answer, try out some small test cases to debug your algorithm (and post your test cases to the discussion forum).

2.For this problem, use the same data set as in the previous problem.
Your task now is to run the greedy algorithm that schedules jobs (optimally) in decreasing order of the ratio (weight/length). In this algorithm, it does not matter how you break ties. You should report the sum of weighted completion times of the resulting schedule — a positive integer — in the box below.
'''
def main():
	#assignment1()
	assignment2()

def assignment2():
	f = open('jobs.txt','r')
	jobs = dict()
	orders = list()
	i=1
	for line in f:
		line = line.strip()
		if not len(line) or line.startswith('#'):
			continue
		arr = line.split(' ')
		if len(arr) == 2:
			jobs[i] = list(map(int,arr))
			orders.append([i,round(int(arr[0])/int(arr[1]),4)])
			i += 1
	f.close()

	arrR = quickSort(orders,jobs)
	#print(arrR)

	minSum = 0
	length = 0
	for i in arrR:
		length += jobs[i[0]][1]
		minSum += jobs[i[0]][0] * length
	print(minSum)

def assignment1():
	f = open('jobs.txt','r')
	jobs = dict()
	orders = list()
	i=1
	for line in f:
		line = line.strip()
		if not len(line) or line.startswith('#'):
			continue
		arr = line.split(' ')
		if len(arr) == 2:
			jobs[i] = list(map(int,arr))
			orders.append([i,int(arr[0]) - int(arr[1])])
			i += 1
	f.close()
	#print(jobs)
	#print(orders)
	arrR = quickSort(orders,jobs)
	#print(arrR)
	minSum = 0
	length = 0
	for i in arrR:
		length += jobs[i[0]][1]
		minSum += jobs[i[0]][0] * length
	print(minSum)

def quickSort(arr,jobs):
	n = len(arr)
	if n < 2:
		return arr
	pivot = arr[0]
	i = 1
	j = 1
	while j < n:
		if pivot[1] > arr[j][1]:
			j+=1
		elif pivot[1] == arr[j][1] and jobs[pivot[0]][0] > jobs[arr[j][0]][0]:
			#加入上面的判断，是因为数相等时，需要对比weight，weight大的优先级更高（根据题目的要求）
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
	arrA = quickSort(arr[0:i-1],jobs)
	arrB = quickSort(arr[i:n],jobs)	
	arrA.append(arr[i-1])
	return arrA + arrB

if __name__ == '__main__':
	main()