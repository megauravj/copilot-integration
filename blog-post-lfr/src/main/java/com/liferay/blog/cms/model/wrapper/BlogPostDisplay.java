package com.liferay.blog.cms.model.wrapper;

import com.liferay.blog.cms.model.BlogPost;
import com.liferay.blog.cms.service.BlogCommentLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper/Display class for BlogPost entity that adds computed properties
 * and provides convenient methods for displaying blog post data.
 * 
 * This class wraps the generated BlogPost entity and adds computed properties
 * such as author name, formatted dates, status labels, and comment counts.
 * It's useful for displaying blog posts in portlets and REST API responses.
 * 
 * @author Blog CMS Development Team
 * @version 1.0
 */
public class BlogPostDisplay implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Log _log = LogFactoryUtil.getLog(BlogPostDisplay.class);

	// Date formatter for display
	private static final DateTimeFormatter DATE_FORMATTER = 
		DateTimeFormatter.ofPattern("dd MMM yyyy");

	// Status label mappings
	private static final Map<String, String> STATUS_LABELS = new HashMap<>();

	static {
		STATUS_LABELS.put("DRAFT", "Draft");
		STATUS_LABELS.put("PUBLISHED", "Published");
		STATUS_LABELS.put("ARCHIVED", "Archived");
	}

	private final BlogPost _blogPost;

	/**
	 * Constructor.
	 *
	 * @param blogPost the wrapped BlogPost entity (must not be null)
	 */
	public BlogPostDisplay(BlogPost blogPost) {
		if (blogPost == null) {
			throw new IllegalArgumentException("BlogPost cannot be null");
		}
		_blogPost = blogPost;
	}

	/**
	 * Gets the wrapped BlogPost entity.
	 *
	 * @return the BlogPost entity
	 */
	public BlogPost getBlogPost() {
		return _blogPost;
	}

	/**
	 * Gets the post ID.
	 *
	 * @return the post ID
	 */
	public long getPostId() {
		return _blogPost.getPostId();
	}

	/**
	 * Gets the group ID (site/community).
	 *
	 * @return the group ID
	 */
	public long getGroupId() {
		return _blogPost.getGroupId();
	}

	/**
	 * Gets the company ID (instance).
	 *
	 * @return the company ID
	 */
	public long getCompanyId() {
		return _blogPost.getCompanyId();
	}

	/**
	 * Gets the user ID (author).
	 *
	 * @return the user ID
	 */
	public long getUserId() {
		return _blogPost.getUserId();
	}

	/**
	 * Gets the title of the blog post.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return _blogPost.getTitle();
	}

	/**
	 * Gets the content of the blog post.
	 *
	 * @return the content, or null if not set
	 */
	public String getContent() {
		return _blogPost.getContent();
	}

	/**
	 * Gets the status of the blog post (DRAFT, PUBLISHED, ARCHIVED).
	 *
	 * @return the status
	 */
	public String getStatus() {
		return _blogPost.getStatus();
	}

	/**
	 * Gets the view count of the blog post.
	 *
	 * @return the view count
	 */
	public long getViewCount() {
		return _blogPost.getViewCount();
	}

	/**
	 * Gets the creation date of the blog post.
	 *
	 * @return the creation date
	 */
	public Date getCreateDate() {
		return _blogPost.getCreateDate();
	}

	/**
	 * Gets the last modification date of the blog post.
	 *
	 * @return the modification date
	 */
	public Date getModifiedDate() {
		return _blogPost.getModifiedDate();
	}

	/**
	 * Gets the UUID of the blog post.
	 *
	 * @return the UUID
	 */
	public String getUuid() {
		return _blogPost.getUuid();
	}

	/**
	 * Gets the author name by fetching from the User service.
	 * 
	 * Attempts to retrieve the full name of the user who created this post.
	 * If the user cannot be found or an error occurs, returns a default message.
	 *
	 * @return the author's full name, or a default value if not found
	 */
	public String getAuthorName() {
		try {
			long userId = _blogPost.getUserId();
			if (userId > 0) {
				return UserLocalServiceUtil.getUser(userId).getFullName();
			}
		} catch (Exception e) {
			_log.warn("Unable to fetch author name for userId: " + _blogPost.getUserId(), e);
		}
		return "Unknown Author";
	}

	/**
	 * Gets the formatted creation date in "dd MMM yyyy" format.
	 * 
	 * For example: "01 Jun 2024"
	 * 
	 * If the creation date is null, returns an empty string.
	 *
	 * @return the formatted creation date
	 */
	public String getFormattedCreatedDate() {
		Date createDate = _blogPost.getCreateDate();
		if (createDate == null) {
			return StringPool.BLANK;
		}

		try {
			LocalDateTime localDateTime = LocalDateTime.ofInstant(
				createDate.toInstant(), 
				ZoneId.systemDefault()
			);
			return DATE_FORMATTER.format(localDateTime);
		} catch (Exception e) {
			_log.warn("Unable to format creation date: " + createDate, e);
			return StringPool.BLANK;
		}
	}

	/**
	 * Gets the formatted modification date in "dd MMM yyyy" format.
	 * 
	 * If the modification date is null, returns an empty string.
	 *
	 * @return the formatted modification date
	 */
	public String getFormattedModifiedDate() {
		Date modifiedDate = _blogPost.getModifiedDate();
		if (modifiedDate == null) {
			return StringPool.BLANK;
		}

		try {
			LocalDateTime localDateTime = LocalDateTime.ofInstant(
				modifiedDate.toInstant(), 
				ZoneId.systemDefault()
			);
			return DATE_FORMATTER.format(localDateTime);
		} catch (Exception e) {
			_log.warn("Unable to format modified date: " + modifiedDate, e);
			return StringPool.BLANK;
		}
	}

	/**
	 * Gets the user-friendly status label.
	 * 
	 * Maps the status code to a display label:
	 * - "DRAFT" → "Draft"
	 * - "PUBLISHED" → "Published"
	 * - "ARCHIVED" → "Archived"
	 * 
	 * If status is not recognized, returns the original status value.
	 *
	 * @return the status label
	 */
	public String getStatusLabel() {
		String status = _blogPost.getStatus();
		if (status == null || status.isEmpty()) {
			return "Unknown";
		}
		return STATUS_LABELS.getOrDefault(status.toUpperCase(), status);
	}

	/**
	 * Gets the total number of comments for this blog post.
	 * 
	 * Queries the BlogCommentLocalService to count comments associated
	 * with this post. If an error occurs, returns 0.
	 *
	 * @return the comment count, or 0 if unable to fetch
	 */
	public int getCommentCount() {
		try {
			return (int) BlogCommentLocalServiceUtil.getBlogCommentsByPostIdCount(
				_blogPost.getPostId()
			);
		} catch (Exception e) {
			_log.warn("Unable to fetch comment count for postId: " + _blogPost.getPostId(), e);
			return 0;
		}
	}

	/**
	 * Converts the blog post display object to a Map suitable for REST API responses.
	 * 
	 * The map includes:
	 * - Basic properties: postId, groupId, companyId, userId, title, content
	 * - Status information: status, statusLabel
	 * - Computed properties: authorName, formattedCreatedDate, formattedModifiedDate, commentCount
	 * - Engagement: viewCount
	 * - UUID: uuid
	 *
	 * @return a Map<String, Object> representation of the blog post
	 */
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();

		// Basic properties
		map.put("postId", getPostId());
		map.put("groupId", getGroupId());
		map.put("companyId", getCompanyId());
		map.put("userId", getUserId());
		map.put("title", getTitle());
		map.put("content", getContent());

		// Status
		map.put("status", getStatus());
		map.put("statusLabel", getStatusLabel());

		// Computed properties
		map.put("authorName", getAuthorName());
		map.put("formattedCreatedDate", getFormattedCreatedDate());
		map.put("formattedModifiedDate", getFormattedModifiedDate());
		map.put("commentCount", getCommentCount());

		// Engagement metrics
		map.put("viewCount", getViewCount());

		// UUID
		map.put("uuid", getUuid());

		// Timestamps
		map.put("createDate", getCreateDate());
		map.put("modifiedDate", getModifiedDate());

		return map;
	}

	/**
	 * Returns a string representation of the blog post for logging purposes.
	 * 
	 * Example output:
	 * BlogPostDisplay{postId=100001, title='Hello World', authorName='John Doe',
	 *   status='PUBLISHED', statusLabel='Published', viewCount=150, commentCount=5}
	 *
	 * @return a string representation of this blog post
	 */
	@Override
	public String toString() {
		return "BlogPostDisplay{" +
			"postId=" + getPostId() +
			", title='" + StringUtil.shorten(getTitle(), 50) + '\'' +
			", authorName='" + getAuthorName() + '\'' +
			", status='" + getStatus() + '\'' +
			", statusLabel='" + getStatusLabel() + '\'' +
			", viewCount=" + getViewCount() +
			", commentCount=" + getCommentCount() +
			", formattedCreatedDate='" + getFormattedCreatedDate() + '\'' +
			'}';
	}

	/**
	 * Compares this BlogPostDisplay with another object.
	 * 
	 * Two BlogPostDisplay objects are considered equal if they wrap
	 * BlogPost entities with the same postId.
	 *
	 * @param o the object to compare with
	 * @return true if both objects have the same postId, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BlogPostDisplay)) {
			return false;
		}
		BlogPostDisplay that = (BlogPostDisplay) o;
		return getPostId() == that.getPostId();
	}

	/**
	 * Returns the hash code for this BlogPostDisplay.
	 * 
	 * Uses the postId to generate the hash code.
	 *
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		return Long.hashCode(getPostId());
	}

	/**
	 * Checks if this blog post is published.
	 *
	 * @return true if status is "PUBLISHED", false otherwise
	 */
	public boolean isPublished() {
		return "PUBLISHED".equalsIgnoreCase(getStatus());
	}

	/**
	 * Checks if this blog post is in draft state.
	 *
	 * @return true if status is "DRAFT", false otherwise
	 */
	public boolean isDraft() {
		return "DRAFT".equalsIgnoreCase(getStatus());
	}

	/**
	 * Checks if this blog post is archived.
	 *
	 * @return true if status is "ARCHIVED", false otherwise
	 */
	public boolean isArchived() {
		return "ARCHIVED".equalsIgnoreCase(getStatus());
	}
}
