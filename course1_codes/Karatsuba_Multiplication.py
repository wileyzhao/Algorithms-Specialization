import os
import math

'''
This is just for test, to accomplish a demo using Karatsuba Multiplication.
n = len(numA)
numA = a*10**(n/2) + b
numB = c*10**(n/2) + d
numA*numB = ac*10**n + (ad+bc)10**(n/2) + bd
ad+bc = (a+b)(c+d) - ac - bd

the diffence bewteen / and //, that's useful
'''
def main():

	numA = input('input integer A: ')
	numB = input('input integer B: ')
	
	result=mult_int(numA,numB)
	print(result)
	
	print('------')
	print(int(numA) * int(numB))

def mult_int(A,B):
	A = int(A)
	B = int(B)
	if A > 10 or B > 10:
		if A >= B:
			n = len(str(A))
		else:
			n = len(str(B))
		a = int(A // (10 ** (n//2)))
		b = int(A % (10 ** (n//2)))
		c = int(B // (10 ** (n//2)))
		d = int(B % (10 ** (n//2)))
		print(a,b,c,d)

		result1 = mult_int(a,c)#a*c
		result2 = mult_int(b,d)#b*d
		result3 = mult_int(a+b,c+d)#(a + b) * (c + d)
		result4 = result3 - result2 - result1
		result5 = result1*10**(math.floor(n/2)*2) + result4*10**math.floor(n/2) + result2
		return result5	
	else:
		return A*B

def mult_str(A,B):
	if len(A) > 1 or len(B) > 1:
		if len(A) > len(B):
			n = len(A)
			while len(B) != n:
				B='0'+ B
		elif len(A) < len(B):
			n = len(B)
			while len(A) != n:
				A='0'+A
		else:
			n = len(A)
		a = A[0:math.ceil(n/2)]
		b = A[math.ceil(n/2):n]
		c = B[0:math.ceil(n/2)]
		d = B[math.ceil(n/2):n]

		result1 = mult_str(a,c)#a*c
		result2 = mult_str(b,d)#b*d
		result3 = mult_str(str(int(a)+int(b)),str(int(c)+int(d)))#(a + b) * (c + d)
		result4 = result3 - result2 - result1
		result5 = int(result1*10**(math.floor(n/2)*2) + result4*10**math.floor(n/2) + result2)	
		return result5
	else:	
		A = int(A)
		B = int(B)
		return A*B


if __name__ == '__main__':
		main()
	