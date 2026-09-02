class Rectangle{
    int width;
    int height;
    Rectangle(int width,int height){
        this.width=width;
        this.height=height;
    }
    int area(){
        return this.width*this.height*3;
    
    }
}
public class Main{
    public static void main(String[] args){
        Rectangle a=new Rectangle(4,5);
        System.out.println(a.area());
    }
}