package FR;

public class TemperatureSensor extends equipment{
    double tempRange;
    String accuracy;
    double currentTemp;
    double upperLimit;
    static String Name="TS";
    static int index;
    static final DeviceArray<TemperatureSensor> DEVICES =
        new DeviceArray<>(new TemperatureSensor[10]);

    public TemperatureSensor(
            String e_wellsite,
            String e_Install_date,
            String e_model
            ){
        super(
            "TemperatureSensor",
            e_model,
            e_wellsite,
            e_Install_date,
            "tempRange,accuracy,currentTemp,upperLimit"
            );

        this.e_ID=Name+'_'+(++index);
    }
}
