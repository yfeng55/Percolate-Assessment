package coffeeapp.percolate.efeng.coffeeapp;


public class Coffee {

    private String desc;
    private String image_url;
    private String id;
    private String name;

    public Coffee(){
    }

    //getters
    public String getName(){
        return this.name;
    }
    public String getDesc(){
        return this.desc;
    }
    public String getID(){
        return this.id;
    }
    public String getImageURL(){
        return this.image_url;
    }

    //setters
    public void setName(String name){
        this.name = name;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public void setID(String id){
        this.id = id;
    }
    public void setImageURL(String image_url){
        this .image_url = image_url;
    }

}