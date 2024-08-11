public class Employee{
    int e_id;
    String e_name;
    String e_pass;
    int vacc_status;
    String date1;
    String date2;
    String vacc_type;
    boolean b_status;

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public void setE_pass(String e_pass) {
        this.e_pass = e_pass;
    }

    public void setVacc_status(int vacc_status) {
        this.vacc_status = vacc_status;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public void setVacc_type(String vacc_type) {
        this.vacc_type = vacc_type;
    }

    public void setB_status(boolean b_status) {
        this.b_status = b_status;
    }

    Employee()
    {

    }
    Employee(int e_id,String e_name,String e_pass,int vacc_status,String date1,String date2,String vacc_type,boolean b_status)
    {
        this.e_id = e_id;
        this.e_name = e_name;
        this.e_pass = e_pass;
        this.vacc_status = vacc_status;
        this.date1 = date1;
        this.date2 = date2;
        this.vacc_type = vacc_type;
        this.b_status = b_status;
    }

    public int getE_id() {
        return e_id;
    }

    public String getE_name() {
        return e_name;
    }

    public String getE_pass() {
        return e_pass;
    }

    public int getVacc_status() {
        return vacc_status;
    }

    public String getDate1() {
        return date1;
    }

    public String getDate2() {
        return date2;
    }

    public String getVacc_type() {
        return vacc_type;
    }

    public boolean isB_status() {
        return b_status;
    }
}
