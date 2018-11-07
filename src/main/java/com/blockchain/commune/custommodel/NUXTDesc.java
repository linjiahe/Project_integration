package com.blockchain.commune.custommodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NUXTDesc {
        private String caption;
        private String content;
        private String key;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
