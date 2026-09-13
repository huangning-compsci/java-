package FR.devices;

public class CentrifugalPump extends equipment {
    double ratedFlow;
    double ratedHead;
    double inletPressure;
    double outletPressure;
    static int index;
    static String Name="CP";
    public static final DeviceArray<CentrifugalPump> DEVICES=
            new DeviceArray<>(new CentrifugalPump[10]);
    
    

    public CentrifugalPump(
            String e_wellsite,
            String e_Install_date,
            String e_model
            ){
            super(
                "CentrifugalPump",
                e_model,
                e_wellsite,
                e_Install_date,
                "ratedFlow,ratedHead,inletPressure,outletpressure"
                );
            this.e_ID=Name+'_'+(++index);
            }
            
}
