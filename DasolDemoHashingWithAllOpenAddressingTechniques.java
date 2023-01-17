package LAB3Q2;
//importing codes from Question 1 Lab Assignment 3
import LAB3Q1.*;
import static LAB3Q1.DasolDemoHashingWithLinearProbing.*;

public class DasolDemoHashingWithAllOpenAddressingTechniques {
    //add double probing method
    public static void addValueDoubleHashing(Integer n){
        //ideal index position
        int hashIndex1 = n % tableCapacity;
        if (hashIndex1 < 0) {                      //checking and setting hashIndex > 0
                hashIndex1 += tableCapacity;       //if negative, add table capacity
        }
        //step size (finding next space)
        int q = thePrimeNumberForSecondHashFunction(tableCapacity);
        //q is the next smallest prime number
        q = checkPrime(q);
        //making sure that hashIndex2 cannot be zero
        int hashIndex2 = q - n%q;

        //check if there is null space to add the value
        if (hashTable[hashIndex1].getKey() == -1 || hashTable[hashIndex1].getKey() == -111) {
            //then set the key as n
            hashTable[hashIndex1].setKey(n);        //set key at hashIndex
        }
        //if the space is taken
        else{
            int counter = 0;
            //if it is not null or available
            while (hashTable[hashIndex1].getKey() != -1 || hashTable[hashIndex1].getKey() != -111) {
                //double probing, collision handling
                hashIndex1 = (counter*hashIndex2)%tableCapacity;
                counter++;
                //if the condition is met, set the index and break out of the loop
                if (hashTable[hashIndex1].getKey() == -1 || hashTable[hashIndex1].getKey() == -111){
                    hashTable[hashIndex1].setKey(n);                 //set key at hashIndex
                    break;                                           //break
                }
                else if(counter == tableCapacity){                   //unsuccessful search
                    break;
                }
            }
        }

    }

    //add quadratic probe method
    public static void addValueQuadraticProbe(Integer n){
        //setting hashIndex
        int hashIndex = n % tableCapacity;
        double count = 1;

        if (hashIndex < 0) {                      //checking and setting hashIndex > 0
            while (hashIndex < 0) {               //making it positive number
                hashIndex += tableCapacity;       //if negative, add table capacity
            }
        }
            //check if there is null space to add the value
        if (hashTable[hashIndex].getKey() == -1 || hashTable[hashIndex].getKey() == -111) {
            //then set the key as n
            hashTable[hashIndex].setKey(n);       //set key at hashIndex
        }
        //if the space is taken
        else{                                     //no null, the space is filled, cannot add more
            while (hashTable[hashIndex].getKey() != -1 || hashTable[hashIndex].getKey() != -111) {
                //quadratic collision handling
                hashIndex = (int)(Math.pow(count,2.0)) % tableCapacity;
                count++;
                //if the condition is met, set and break
                if (hashTable[hashIndex].getKey() == -1 || hashTable[hashIndex].getKey() == -111){
                    hashTable[hashIndex].setKey(n);                 //set key at hashIndex
                    break;                                          //break
                } else if (count-1 == tableCapacity){               //unsuccessful search
                    break;
                }
            }
        }
    }
    //print array method to put it in [....] form
    public static void printArray(Integer[] n){
        System.out.print("[");
        for(int i =0; i<n.length;i++) {         //increase the size, table capacity is different
            System.out.print(n[i] + ", ");
        }
        System.out.print("\b\b]\n");
    }

    //used to reset or empty the HashTable with -1, "null"
    public static void emptyHashTable(){
        //resetting the hashTable with value -1 using DasolValueEntry
        for(int i =0; i<hashTable.length; i++){
            hashTable[i] = new DasolValueEntry();
        }
    }

    //get smaller prime number
    public static int thePrimeNumberForSecondHashFunction(int n){
        //using m to store the size of n
        int m = n;
        //only need to check half of tableCapacity
        for(int i=0;i<tableCapacity/2;i++) {
            //if the tableCapacity is greater
            if (checkPrime(tableCapacity) >= checkPrime(n)) {
                //decrement by i
                n = m - i;
                //break when the next smallest prime is found
            } else if (checkPrime(tableCapacity) < checkPrime(n)){
                break;
            }
        }
        //return the prime of n, double-checking
        return checkPrime(n);
    }
    public static void main(String args[]){
        //this is a header
        myHeader(3, 2);

        //sample output format
        System.out.println("Lets demonstrate our understanding on all the open addressing technqiues...");
        System.out.print("How many data items: ");
        items = input.nextInt();
        System.out.print("What is the load factor (Recommended: <= 0.5):");
        lf = input.nextDouble();
        int factoredTableCapacity = (int)(items/lf);
        tableCapacity = checkPrime(factoredTableCapacity);
        System.out.println("The minimum required table capacity would be: " + tableCapacity);
        hashTable = new DasolValueEntry[tableCapacity];
        //populating the hashtable with -1, "null"

        //populating hashTable with -1, "null"
        for(int i=0; i<tableCapacity;i++) {
            hashTable[i] = new DasolValueEntry();
        }

        //using the given array values from the lab
        Integer[] array = {7,14,-21,-28,35};
        //-11,22,-33,-44,55, used for testing for first lab output

        //7,14,-21,-28,35

        System.out.print("The given dataset is: ");
        //printing the array in printArray format
        printArray(array);

        System.out.println("Let's enter each data item from above to the hashtable:");

        //linear probing
        System.out.println("\nAdding data - linear probing resolves collision");
        for(int i=0;i<items;i++){
            addValueLinearProbe(array[i]);
        }
        System.out.print("The Current Hash-Table: ");
        printHashTable();
        emptyHashTable();

        //quadratic probing
        System.out.println("\nAdding data - quadratic probing resolves collision");
        for(int i=0;i<items;i++){
            addValueQuadraticProbe(array[i]);
        }
        //if the load factor is greater than 0.5, output the sample output
        if(lf > 0.5) {
            System.out.println("Probing failed! Use a load factor of 0.5 or less. GoodBye!");
        }
        System.out.print("The Current Hash-Table: ");
        printHashTable();
        emptyHashTable();

        //double hashing
        System.out.println("\nAdding data - double-hashing probing resolves collision");
        for(int i=0;i<items;i++){
            addValueDoubleHashing(array[i]);
        }
        //q is the next smaller prime number
        int q = thePrimeNumberForSecondHashFunction(tableCapacity);
        q = checkPrime(q);
        System.out.println("The q value for double hashing is: " + q);
        System.out.print("The Current Hash-Table: ");
        printHashTable();
        emptyHashTable();

        //this is a footer
        myFooter(3, 2);
    }
}
