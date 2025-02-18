package coeqa.cucumberio;

import utils.Context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

	private  Map<String, Object> sConText;

    public ScenarioContext(){
        sConText = new HashMap<>();
    }

    public void setContext(Context key, Object value) {
        sConText.put(key.toString(), value);
    }

    public Object getContext(Context key){
        return sConText.get(key.toString());
    }

    public Boolean isContains(Context key){
        return sConText.containsKey(key.toString());
    }

}
