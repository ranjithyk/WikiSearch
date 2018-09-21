package com.wikisearch;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchResultModel implements Serializable {

    @SerializedName("batchcomplete")
    public String batchcomplete;

    @SerializedName("continue")
    public Continue continueTo;

    @SerializedName("query")
    public Query query;

    public class Continue implements Serializable {

        @SerializedName("gpsoffset")
        public int gpsoffset;

        @SerializedName("continue")
        public String continueTo;
    }

    public class Query implements Serializable {

        @SerializedName("redirects")
        public Redirects[] redirects;

        @SerializedName("pages")
        public Pages[] pages;
    }

    public class Redirects implements Serializable {

        @SerializedName("index")
        public int index;

        @SerializedName("to")
        public String to;

        @SerializedName("from")
        public String from;
    }

    public class Pages implements Serializable {

        @SerializedName("pageid")
        public String pageid;

        @SerializedName("ns")
        public int ns;

        @SerializedName("title")
        public String title;

        @SerializedName("index")
        public int index;

        @SerializedName("thumbnail")
        public Thumbnail thumbnail;

        @SerializedName("terms")
        public Terms terms;

    }

    public class Thumbnail implements Serializable {

        @SerializedName("source")
        public String source;

        @SerializedName("width")
        public int width;

        @SerializedName("height")
        public int height;

    }

    public class Terms implements Serializable {

        @SerializedName("description")
        public String[] description;
    }
}
