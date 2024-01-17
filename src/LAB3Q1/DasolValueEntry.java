package LAB3Q1;

public class DasolValueEntry {    private Integer key;
    //no argument constructor
    public DasolValueEntry(){
        //assignment -1 for the field is considered as 'null' for the has table
        key = -1;
    }
    public DasolValueEntry(Integer key){
        this.key = key;
    }
    //setter method for key
    public void setKey(Integer key){
        this.key = key;
    }
    //getter method for key
    public Integer getKey(){
        return key;
    }

}
