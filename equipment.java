package FR;

public class equipment {
    String e_ID;
    String e_type;
    String e_model;
    String e_wellsite;
    String e_Install_date;
    String e_parameter;
    String e_Status;
      
    equipment(
        String e_type,
        String e_model,
        String e_wellsite,
        String e_Install_date,
        String e_parameter
       ){
        this.e_Install_date=e_Install_date;
        this.e_Status="投运中";
        this.e_model=e_model;
        this.e_parameter=e_parameter;
        this.e_type=e_type;
        this.e_wellsite=e_wellsite;

    }

}
