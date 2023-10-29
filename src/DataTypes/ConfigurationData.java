package DataTypes;

public class ConfigurationData {
    public String banner;
    public int inPort;
    public int outPort;
    public String networkInterface;

    public  ConfigurationData(int inPort, int outPort, String banner, String networkInterface){
        this.inPort = inPort;
        this.outPort = outPort;
        this.banner = banner;
        this.networkInterface = networkInterface;
    }
    public int getInPort(){
        return this.inPort;
    }
    public int getOutPort(){
        return this.outPort;
    }
    public String getBanner(){
        return this.banner;
    }
    public String getNetworkInterface(){
        return this.networkInterface;
    }
}
