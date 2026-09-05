package FR;

public class DeviceArray<T extends equipment> {
    private int count;
    private final T[] devices;

    DeviceArray(T[] devices){
        this.devices=devices;
        this.count=0;

    }

    boolean store(T device){
        if(count>=devices.length){
            return  false;
        }

        devices[count++]=device;
        return  true;
    }

    T get(int index){
        if(index<0 ||index >=count){
            return  null;
        }
        return  devices[index];
    }
}
