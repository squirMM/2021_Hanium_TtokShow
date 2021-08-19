from math import factorial
a = int(input()) * 0.01
b = int(input()) * 0.01
s = [0,1,4,6,8,9,10,12,14,15,16,18]
pa = []
pb = []
t = factorial(18)
answer = 0
for i in s:
    x = t // (factorial(18-i) * factorial(i))
    pa.append((a**i)*(1-a)**(18-i)*x)
    pb.append((b**i)*(1-b)**(18-i)*x)

print(1-sum(pa)*sum(pb))

