package LAB3Q1;

import com.sun.security.jgss.GSSUtil;

import java.lang.reflect.WildcardType;

import java.util.*;

public class DasolDemoHashingWithLinearProbing {
    //hold value of total number of data items in the hash table
    public static int items;
    //Scanner type object reference
    public static Scanner input = new Scanner(System.in);
    //record the load-factor
    public static double lf;
    //store the value of the table capacity
    public static int tableCapacity;
    //DasolValueEntry type array reference
    public static DasolValueEntry[] hashTable = new DasolValueEntry[tableCapacity];
    //second DasolValueEntry type array reference for storing copy of hashTable for rehashing operation
    public static DasolValueEntry[] workingHashTable = new DasolValueEntry[tableCapacity];

    //this is linear add probing method for adding into the hashmap
    public static void addValueLinearProbe(Integer n) {
        int hashIndex = n;                        //setting n as the hashIndex
        if (hashIndex < 0) {                      //checking and setting hashIndex > 0
            while (hashIndex < 0) {               //making it positive number
                hashIndex += tableCapacity;       //if key value, n, is negative add tableCapacity until (+)
            }
        } else {
            hashIndex = n % tableCapacity;        //if not negative then mod to find hashIndex
        }

        //check if there is null space, so we can add or set the value, n
        if (hashTable[hashIndex].getKey() == -1 || hashTable[hashIndex].getKey() == -111) {
            //if the space is null or available then set it as n at hashIndex
            hashTable[hashIndex].setKey(n);
        }
        //if the space is taken (occupied), not null
        else{
            int count = 0;  //for my while loop
            //while hashIndex location is not null or no available
                while (hashTable[hashIndex].getKey() != -1 || hashTable[hashIndex].getKey() != -111) {
                    //x+1, linear probing and mode by table capacity to put it at next index
                    hashIndex = (hashIndex+1) % tableCapacity;
                    count++;    //increment the count to go to next index
                    //if next available hashIndex is found
                    if (hashTable[hashIndex].getKey() == -1 || hashTable[hashIndex].getKey() == -111){
                        hashTable[hashIndex].setKey(n);                 //set key at hashIndex
                        break;                                          //break out of the while loop
                    }
                    else if (count == tableCapacity){                   //reached the end, unsuccessful search
                        break;                                          //break;
                    }
                }
            }
        }

    public static int checkPrime(int n){
        //just need to check half of the n factors
        int m = n/2;
        //can skip 1 since it is not prime and needs to be greater than 2 so start at 3
        for(int i=3; i<= m; i++){
            //if n is not a prime number
            if(n%i ==0){
                //reset i to 2 so that it is incremented to 3 in the for header
                i=2;
//              System.out.printf("i = %d/n", i);
                //next n value
                n++;
                //we need to check half of the n factors
                m=n/2;
            }
        }
        return n;
    }
    public static void removeValueLinearProbe(Integer n){
        int hashIndex = n % tableCapacity;    //setting hashCode to hashIndex
        int count = 0;                        //used for my while loop

        if (hashIndex < 0) {                  //checking and setting hashIndex > 0
            while (hashIndex < 0) {           //making hashIndex positive number
                hashIndex = hashIndex + tableCapacity;  //real world modding, adding table capacity until (+)
            }
        }
            if (hashTable[hashIndex].getKey() == n) {   //if key, n, is found in hashTable
                System.out.println(n + " is Found and removed!");
                hashTable[hashIndex].setKey(-111);      //remove and set the value to -111, available
            }
            //if the space is taken, no null
            else{
                while (hashTable[hashIndex].getKey() != n) {         //while hashIndex location is not null, occupied
                    hashIndex = (hashIndex + 1) % tableCapacity;     //finding the next null space and putting it there
                    count++;
                    if (hashTable[hashIndex].getKey() == n){         //if the key is found then output statement & break
                        System.out.println(n + " is Found and removed!");
                        hashTable[hashIndex].setKey(-111);           //set the key, n, at hashIndex as -111
                        break;
                    }
                    else if (count == tableCapacity){                //unsuccessful search
                        System.out.println(n + " key is not found!");
                        break;                                       //break out of the loop
                    }
                }
            }
    }
    //printing the content of hashtable
    public static void printHashTable(){
        System.out.print("[");
        for(int i =0; i<hashTable.length;i++) {             //increase the size, table capacity is different
            if(hashTable[i].getKey() == -1){                //if key is -1: null
                System.out.print("null" + ", ");
            }
            else if (hashTable[i].getKey() == -111){        //if key is -111: available
                System.out.print("available" + ", ");
            }
            else{
                System.out.print(hashTable[i].getKey() + ", ");
            }
        }
        //double back-space to remove the comma and white space
        System.out.print("\b\b]\n");
    }

    //rehashing with rehashed tableCapacity
    public static void rehashingWithLinearProbe(){
        //increasing the capacity of rehashing  array size
        int rehashedTableCapacity = tableCapacity*2;
        //ensure that it is prime number
        rehashedTableCapacity = checkPrime(rehashedTableCapacity);

        //instantiating workingHashTable with size rehashedTableCapacity
        workingHashTable = new DasolValueEntry[rehashedTableCapacity];
        //populating the hashtable with -1, "null"
        for(int i=0; i<rehashedTableCapacity;i++) {
            workingHashTable[i] = new DasolValueEntry();
        }

        int secondHashIndex;
        //rehashing the old hashTable values to workingHashTable
        for(int k = 0; k<tableCapacity;k++){
            //doing add linear probing with the old hashTable key values (populating)
            if (hashTable[k].getKey() != -1 || hashTable[k].getKey() != -111){
                //if not occupied then add the value to the corresponding index
                secondHashIndex = hashTable[k].getKey() % rehashedTableCapacity;
                //check if it is negative and set it as positive
                if(secondHashIndex<0){
                    secondHashIndex = secondHashIndex + rehashedTableCapacity;
                }
                //add elements to the new hash index, working hashtable
                workingHashTable[secondHashIndex].setKey(hashTable[k].getKey());
            }
        }

        //setting hashTable with rehashed capacity
        hashTable = new DasolValueEntry[rehashedTableCapacity];

        //populating hashTable with workingHashTable for future print method
        for(int i=0; i<rehashedTableCapacity;i++) {
            //first instantiating with -1, "null"
            hashTable[i] = new DasolValueEntry();
            //setting the key with workingHashTable value
            hashTable[i].setKey(workingHashTable[i].getKey());
        }

        //output statement
        System.out.println("rehashed table capacity is: " + rehashedTableCapacity);

    }
    //This is myHeader method containing my name and student number
    public static void myHeader(int assignnmentNum, int questionNum){
        System.out.println("=====================================================");
        System.out.printf("Lab Assignment %d-Q%d \n", assignnmentNum, questionNum);
        System.out.println("Prepared By: Dasol Lim");
        System.out.println("Student Number: 251224110");
        System.out.println("Goal of this Exercise Q1: Using linear probing we learned in class");
        System.out.println("Goal of this Exercise Q2: Using double and quadratic probing");
        System.out.println("");
        System.out.println("=====================================================");
    }
    //This is myFooter containing a good-bye message
    public static void myFooter(int assignnmentNum, int questionNum) {
        System.out.println("=====================================================");
        System.out.printf("Completion of Lab Assignment %d-Q%d is successful! \n", assignnmentNum, questionNum);
        System.out.println("Signing off - Dasol Lim");
        System.out.println("=====================================================");
    }

    public static void main (String args[]){
        //this is a header
        myHeader(3, 1);
        //given sample output statement
        System.out.println("Lets decide the initial table capacity based on the load factor and dataset size");
        System.out.print("How many data items: ");
        //item is the data item size
        items = input.nextInt();
        //0.5 is recommended since the performance decrease as lf increases
        //0.5 maintain at a reasonable efficiency
        System.out.print("What is the load factor (Recommended: <= 0.5): ");
        lf = input.nextDouble();
        int factoredTableCapacity = (int)(items/lf);
        tableCapacity = checkPrime(factoredTableCapacity);
        System.out.println("The minimum required table capacity would be: " + tableCapacity);

        //instantiating hashTable
        hashTable = new DasolValueEntry[tableCapacity];

        //populating the hashtable with -1, "null"
        for(int i=0; i<tableCapacity;i++) {
            hashTable[i] = new DasolValueEntry();
        }
        //populating hashTable with user inputs
        for(int i =0; i<items; i++){
            int dataItems;
            System.out.print("Enter item "+ i + ": ");
            dataItems = input.nextInt();
            //adding into hashTable using linear probing method
            addValueLinearProbe(dataItems);
        }
        //printing the hashTable
        printHashTable();

        int remove, add;
        System.out.println("\nLets remove two values fromm the table and then add one ...\n");

        //removing 2 values from the table using remove linear probe
        for(int i =0; i<2; i++) {
            System.out.print("Enter a value you want to remove: ");
            remove = input.nextInt();
            removeValueLinearProbe(remove);
            System.out.print("The Current Hash-Table: ");
            printHashTable();
        }

        //adding 1 value to the table using add linear probe
        System.out.print("Enter a value you want to add: ");
        add = input.nextInt();
        addValueLinearProbe(add);
        System.out.print("The Current Hash-Table: ");
        printHashTable();

        //rehashing the table
        System.out.println("\nRehashing the table ...");
        rehashingWithLinearProbe();
        printHashTable();

        //this is a footer
        myFooter(3, 1);
    }
}
