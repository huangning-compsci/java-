package devices;

public class FlowMeter extends equipment{
    double flowRange;
    String accuracy;
    double currentFlow;
    double totalFlow;
    static String Name="FM";
    static int index;
    public static final DeviceArray<FlowMeter> DEVICES =
        new DeviceArray<>(new FlowMeter[10]);

    public FlowMeter(
            String e_wellsite,
            String e_Install_date,
            String e_model
            ){
        super(
            "FlowMeter",
            e_model,
            e_wellsite,
            e_Install_date,
            "flowRange,accuracy,currentFlow,totalFlow"
            );

        this.e_ID=Name+'_'+(++index);
    }
}
