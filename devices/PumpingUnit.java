package FR.devices;

public  class  PumpingUnit extends  equipment{
    
    double ratedPower;
    double stroke;
    double strokeRate;
    double currentLoad; 
    double ratedLoad;
    static String Name="PU"; 
    static int index; 
    public static final DeviceArray<PumpingUnit> DEVICES =
        new DeviceArray<>(new PumpingUnit[10]);
    
    
    public PumpingUnit(
            String e_wellsite,
            String e_Install_date,
            String e_model
            ){
            super(
                "PumpingUnit",
                e_model,
                e_wellsite,
                e_Install_date,
                "stroke,strokeRate,currentLoad,ratedLoad,ratedPower"
                );

            this.e_ID=Name+'_'+(++index);

            
    }
    
}
