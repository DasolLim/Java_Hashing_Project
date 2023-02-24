# Java_Hashing_Project
Demonstrated understanding in hashing
Hashing:
- determine index or location of storage of an item (hash code or hash index)

Hash Function:
- receive search key
- return index of array from the hash table

Hash Table:
- must be prime number 
- prime number allows uniform distribution
- if negative value, add the size of the hashtable until postive

Hash Table Location:
Occupied:
- location references an entry in the dictionary

Empty:
- lcoation contains null
- search will stop the moment it encounters null

Available:
- location entry removed from the distionary
- search go past the location if the expected entry is not found in any available location

Implemented open addressing collision resolving techniques 
Programmed linear probing, double probing, quadratic probing, and rehashing

![image](https://user-images.githubusercontent.com/92288227/221117189-0f9199e2-55e3-4bd2-9ff8-f838c6d3d474.png)
![image](https://user-images.githubusercontent.com/92288227/221117581-64139eff-90a6-4184-a28d-af30a8bbbcfb.png)
