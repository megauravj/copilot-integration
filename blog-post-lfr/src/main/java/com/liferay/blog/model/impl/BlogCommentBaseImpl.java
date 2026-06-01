package com.liferay.blog.model.impl;

import com.liferay.blog.model.BlogComment;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.util.Date;
final java.util.LinkedHashMap<String, Object> attributeGetterFunctions;
final java.util.LinkedHashMap<String, Object> attributeSetterBiConsumers;
static {
    attributeGetterFunctions = new java.util.LinkedHashMap<>();
    attributeSetterBiConsumers = new java.util.LinkedHashMap<>();

    attributeGetterFunctions.put("uuid", BlogCommentImpl::getUuid);
    attributeSetterBiConsumers.put("uuid", (o, v) -> ((BlogCommentImpl)o).setUuid((String)v));
    attributeGetterFunctions.put("commentId", BlogCommentImpl::getCommentId);
    attributeSetterBiConsumers.put("commentId", (o, v) -> ((BlogCommentImpl)o).setCommentId((Long)v));
    attributeGetterFunctions.put("postId", BlogCommentImpl::getPostId);
    attributeSetterBiConsumers.put("postId", (o, v) -> ((BlogCommentImpl)o).setPostId((Long)v));
    attributeGetterFunctions.put("userId", BlogCommentImpl::getUserId);
    attributeSetterBiConsumers.put("userId", (o, v) -> ((BlogCommentImpl)o).setUserId((Long)v));
    attributeGetterFunctions.put("userName", BlogCommentImpl::getUserName);
    attributeSetterBiConsumers.put("userName", (o, v) -> ((BlogCommentImpl)o).setUserName((String)v));
    attributeGetterFunctions.put("commentText", BlogCommentImpl::getCommentText);
    attributeSetterBiConsumers.put("commentText", (o, v) -> ((BlogCommentImpl)o).setCommentText((String)v));
    attributeGetterFunctions.put("createDate", BlogCommentImpl::getCreateDate);
    attributeSetterBiConsumers.put("createDate", (o, v) -> ((BlogCommentImpl)o).setCreateDate((Date)v));
    attributeGetterFunctions.put("modifiedDate", BlogCommentImpl::getModifiedDate);
    attributeSetterBiConsumers.put("modifiedDate", (o, v) -> ((BlogCommentImpl)o).setModifiedDate((Date)v));
}

public class BlogCommentBaseImpl extends BaseModelImpl<BlogComment> implements BlogComment {

    protected String uuid;
    protected long commentId;
    protected long postId;
    protected long userId;
    protected String userName;
    protected String commentText;
    protected Date createDate;
    protected Date modifiedDate;

    public BlogCommentBaseImpl() {
    }

    @Override
    public long getPrimaryKey() {
        return commentId;
    }

    @Override
    public void setPrimaryKey(long primaryKey) {
        setCommentId(primaryKey);
    }

    @Override
    public String getUuid() {
        if (uuid == null) {
            return "";
        }
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public long getCommentId() {
        return commentId;
    }

    @Override
    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    @Override
    public long getPostId() {
        return postId;
    }

    @Override
    public void setPostId(long postId) {
        this.postId = postId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String getUserName() {
        if (userName == null) {
            return "";
        }
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getCommentText() {
        if (commentText == null) {
            return "";
        }
        return commentText;
    }

    @Override
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public Date getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(17);

        sb.append(getModelClassName());
        sb.append(" {commentId=");
        sb.append(commentId);
        sb.append(", postId=");
        sb.append(postId);
        sb.append(", userId=");
        sb.append(userId);
        sb.append(", userName=");
        sb.append(userName);
        sb.append(", commentText=");
        sb.append(commentText);
        sb.append(", createDate=");
        sb.append(createDate);
        sb.append(", modifiedDate=");
        sb.append(modifiedDate);
        sb.append("}");

        return sb.toString();
    }

    @Override
    public BlogComment toEscapedModel() {
        return (BlogComment)ProxyUtil.newProxyInstance(
                BlogComment.class.getClassLoader(), new Class<?>[] {BlogComment.class},
                new AutoEscapeBeanHandler(this));
    }

    @Override
    public Object clone() {
        BlogCommentImpl clone = new BlogCommentImpl();
        clone.setUuid(getUuid());
        clone.setCommentId(getCommentId());
        clone.setPostId(getPostId());
        clone.setUserId(getUserId());
        clone.setUserName(getUserName());
        clone.setCommentText(getCommentText());
        clone.setCreateDate(getCreateDate());
        clone.setModifiedDate(getModifiedDate());
        clone.resetOriginalValues();
        return clone;
    }

    @Override
    public int compareTo(BlogComment blogComment) {
        long primaryKey = blogComment.getPrimaryKey();
        if (getPrimaryKey() < primaryKey) {
            return -1;
        } else if (getPrimaryKey() > primaryKey) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BlogComment)) {
            return false;
        }
        BlogComment blogComment = (BlogComment)obj;
        if (getPrimaryKey() == blogComment.getPrimaryKey()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return HashUtil.hash(0, getPrimaryKey());
    }

    @Override
    public String getModelClassName() {
        return "com.liferay.blog.model.BlogComment";
    }
}