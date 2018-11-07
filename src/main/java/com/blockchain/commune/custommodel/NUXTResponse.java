package com.blockchain.commune.custommodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by wrb on 2018/10/23
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NUXTResponse {
    private List<NUXTData> data;
    private Boolean serverRendered;

    public List<NUXTData> getData() {
        return data;
    }

    public void setData(List<NUXTData> data) {
        this.data = data;
    }

    public Boolean getServerRendered() {
        return serverRendered;
    }

    public void setServerRendered(Boolean serverRendered) {
        this.serverRendered = serverRendered;
    }
}
