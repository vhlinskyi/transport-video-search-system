package com.maxclay.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Vlad Glinskiy
 */
public class SearchResult {

    private WantedTransport wantedTransport;
    private String image;

    public SearchResult() {
    }

    public SearchResult(WantedTransport wantedTransport) {
        setWantedTransport(wantedTransport);
    }

    public SearchResult(WantedTransport wantedTransport, String image) {
        setWantedTransport(wantedTransport);
        setImage(image);
    }

    public WantedTransport getWantedTransport() {
        return wantedTransport;
    }

    public void setWantedTransport(WantedTransport wantedTransport) {
        this.wantedTransport = wantedTransport;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("wantedTransport", wantedTransport)
                .append("image", image)
                .toString();
    }
}
