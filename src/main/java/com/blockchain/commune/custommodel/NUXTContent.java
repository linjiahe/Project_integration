package com.blockchain.commune.custommodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class NUXTContent {
        private String title;
        private List<NUXTDesc> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<NUXTDesc> getList() {
            return list;
        }

        public void setList(List<NUXTDesc> list) {
            this.list = list;
        }
    }