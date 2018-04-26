package model;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;


public enum Estimated_time {
    @SerializedName("UNKNOWN")
	UNKNOWN,
	@SerializedName("FIVE_MINS")
	FIVE_MINS,
	@SerializedName("TEN_MINS")
	TEN_MINS,
	@SerializedName("TWENTY_MINS")
	TWENTY_MINS,
	@SerializedName("MORE_THAN_TWENTY_MINS")
	MORE_THAN_TWENTY_MINS;
    
    private Estimated_time time ;
    
    public String getTime() {
        return this.name();
    }
    
    public Estimated_time getESTIMATED_TIME() {
    	if(time == null) time = Estimated_time.UNKNOWN;
    	return time;
    }
};
