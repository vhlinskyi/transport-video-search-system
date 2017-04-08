package com.maxclay.model;

import com.maxclay.utils.DateUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * TODO field constraints
 *
 * @author Vlad Glinskiy
 */
@Document(collection = DataSetVersion.COLLECTION_NAME)
public class DataSetVersion {

    public static final String COLLECTION_NAME = "data_set_versions";

    @Id
    private String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date created;

    @Field("revision_id")
    private Long revisionId;

    public DataSetVersion(Long revisionId, Date created) {
        setRevisionId(revisionId);
        setCreated(created);
    }

    public DataSetVersion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(Long revisionId) {
        this.revisionId = revisionId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("created", DateUtils.formatToSecond(created))
                .append("revision_id", revisionId)
                .toString();
    }
}
