package com.liferay.blog.model.impl;

import com.liferay.blog.model.BlogPost;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.util.Date;
final java.util.LinkedHashMap<String, Object> attributeGetterFunctions;
final java.util.LinkedHashMap<String, Object> attributeSetterBiConsumers;
static {
    attributeGetterFunctions = new java.util.LinkedHashMap<>();
    attributeSetterBiConsumers = new java.util.LinkedHashMap<>();

    attributeGetterFunctions.put("uuid", BlogPostImpl::getUuid);
    attributeSetterBiConsumers.put("uuid", (o, v) -> ((BlogPostImpl)o).setUuid((String)v));
    attributeGetterFunctions.put("postId", BlogPostImpl::getPostId);
    attributeSetterBiConsumers.put("postId", (o, v) -> ((BlogPostImpl)o).setPostId((Long)v));
    attributeGetterFunctions.put("groupId", BlogPostImpl::getGroupId);
    attributeSetterBiConsumers.put("groupId", (o, v) -> ((BlogPostImpl)o).setGroupId((Long)v));
    attributeGetterFunctions.put("userId", BlogPostImpl::getUserId);
    attributeSetterBiConsumers.put("userId", (o, v) -> ((BlogPostImpl)o).setUserId((Long)v));
    attributeGetterFunctions.put("userName", BlogPostImpl::getUserName);
    attributeSetterBiConsumers.put("userName", (o, v) -> ((BlogPostImpl)o).setUserName((String)v));
    attributeGetterFunctions.put("createDate", BlogPostImpl::getCreateDate);
    attributeSetterBiConsumers.put("createDate", (o, v) -> ((BlogPostImpl)o).setCreateDate((Date)v));
    attributeGetterFunctions.put("modifiedDate", BlogPostImpl::getModifiedDate);
    attributeSetterBiConsumers.put("modifiedDate", (o, v) -> ((BlogPostImpl)o).setModifiedDate((Date)v));
    attributeGetterFunctions.put("title", BlogPostImpl::getTitle);
    attributeSetterBiConsumers.put("title", (o, v) -> ((BlogPostImpl)o).setTitle((String)v));
    attributeGetterFunctions.put("content", BlogPostImpl::getContent);
    attributeSetterBiConsumers.put("content", (o, v) -> ((BlogPostImpl)o).setContent((String)v));
    attributeGetterFunctions.put("status", BlogPostImpl::getStatus);
    attributeSetterBiConsumers.put("status", (o, v) -> ((BlogPostImpl)o).setStatus((String)v));
    attributeGetterFunctions.put("viewCount", BlogPostImpl::getViewCount);
    attributeSetterBiConsumers.put("viewCount", (o, v) -> ((BlogPostImpl)o).setViewCount((Long)v));
}

public class BlogPostBaseImpl extends BaseModelImpl<BlogPost> implements BlogPost {

    protected String uuid;
    protected long postId;
    protected long groupId;
    protected long userId;
    protected String userName;
    protected Date createDate;
    protected Date modifiedDate;
    protected String title;
    protected String content;
    protected String status;
    protected long viewCount;

    public BlogPostBaseImpl() {
    }

    @Override
    public long getPrimaryKey() {
        return postId;
    }

    @Override
    public void setPrimaryKey(long primaryKey) {
        setPostId(primaryKey);
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
    public long getPostId() {
        return postId;
    }

    @Override
    public void setPostId(long postId) {
        this.postId = postId;
    }

    @Override
    public long getGroupId() {
        return groupId;
    }

    @Override
    public void setGroupId(long groupId) {
        this.groupId = groupId;
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
    public String getTitle() {
        if (title == null) {
            return "";
        }
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getContent() {
        if (content == null) {
            return "";
        }
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getStatus() {
        if (status == null) {
            return "";
        }
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public long getViewCount() {
        return viewCount;
    }

    @Override
    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public long getShardingGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(23);

        sb.append(getModelClassName());
        sb.append(" {postId=");
        sb.append(postId);
        sb.append(", groupId=");
        sb.append(groupId);
        sb.append(", userId=");
        sb.append(userId);
        sb.append(", userName=");
        sb.append(userName);
        sb.append(", createDate=");
        sb.append(createDate);
        sb.append(", modifiedDate=");
        sb.append(modifiedDate);
        sb.append(", title=");
        sb.append(title);
        sb.append(", status=");
        sb.append(status);
        sb.append(", viewCount=");
        sb.append(viewCount);
        sb.append("}");

        return sb.toString();
    }

    @Override
    public BlogPost toEscapedModel() {
        return (BlogPost)ProxyUtil.newProxyInstance(
                BlogPost.class.getClassLoader(), new Class<?>[] {BlogPost.class},
                new AutoEscapeBeanHandler(this));
    }

    @Override
    public Object clone() {
        BlogPostImpl clone = new BlogPostImpl();
        clone.setUuid(getUuid());
        clone.setPostId(getPostId());
        clone.setGroupId(getGroupId());
        clone.setUserId(getUserId());
        clone.setUserName(getUserName());
        clone.setCreateDate(getCreateDate());
        clone.setModifiedDate(getModifiedDate());
        clone.setTitle(getTitle());
        clone.setContent(getContent());
        clone.setStatus(getStatus());
        clone.setViewCount(getViewCount());
        clone.resetOriginalValues();
        return clone;
    }

    @Override
    public int compareTo(BlogPost blogPost) {
        long primaryKey = blogPost.getPrimaryKey();
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
        if (!(obj instanceof BlogPost)) {
            return false;
        }
        BlogPost blogPost = (BlogPost)obj;
        if (getPrimaryKey() == blogPost.getPrimaryKey()) {
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
        return "com.liferay.blog.model.BlogPost";
    }
}