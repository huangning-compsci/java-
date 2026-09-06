package FR;

public class PressureSensor extends equipment{
    double pressureRange;
    String accuracy;
    double currentPressure;
    double upperLimit;
    double lowerLimit;
    static String Name="PS";
    static int index;
    static final DeviceArray<PressureSensor> DEVICES =
        new DeviceArray<>(new PressureSensor[10]);

    public PressureSensor(
            String e_wellsite,
            String e_Install_date,
            String e_model
            ){
        super(
            "PressureSensor",
            e_model,
            e_wellsite,
            e_Install_date,
            "pressureRange,accuracy,currentPressure,upperLimit,lowerLimit"
            );

        this.e_ID=Name+'_'+(++index);
    }
}
