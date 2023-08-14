package cseb.tech.smarthublms;

public class OtpGenerator {
    public String OTPGenerator(){
        int p2 = (int) (100000 + (9999999 - 100000) * Math.random());
        String pass2 = Integer.toString(p2);
        return pass2;
    }
}
