package utilities;

public class IpUtilities {
    // FORMAT CONVERSION
    public static String fromMacAddressToString(byte[] macAddress){
        String macAddressString = "";

        for(int x = 0; x < macAddress.length; x++){
            macAddressString += ((x == 0) ? "" : ":") + String.format("%02X", macAddress[x]);
        }

        return macAddressString;
    }
}
