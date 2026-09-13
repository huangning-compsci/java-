package devices;

public class Compressor extends Equipment{
    private double exhaustPressure;
    private double exhaustFlow;
    private double power;
    private static final String NAME="CM";
    private static int index;
    private static final DeviceArray<Compressor> DEVICES =
        new DeviceArray<>(new Compressor[10]);

    public Compressor(){
        super(
            NAME+'_'+(++index),
            "Compressor",
            "",
            "",
            "",
            "exhaustPressure,exhaustFlow,power"
        );
        this.power=37.0;
    }

    public Compressor(
            String e_wellsite,
            String e_Install_date,
            String e_model
            ){
        this();
        setModel(e_model);
        setWellsite(e_wellsite);
        setInstallDate(e_Install_date);

    }
    public static DeviceArray<Compressor> getDevices(){
        return DEVICES;
    }

    public double getExhaustPressure(){
        return exhaustPressure;
    }

    public void setExhaustPressure(double exhaustPressure){
        this.exhaustPressure=exhaustPressure;
    }

    public double getExhaustFlow(){
        return exhaustFlow;
    }

    public void setExhaustFlow(double exhaustFlow){
        this.exhaustFlow=exhaustFlow;
    }

    public double getPower(){
        return power;
    }

    public void setPower(double power){
        this.power=power;
    }
}
