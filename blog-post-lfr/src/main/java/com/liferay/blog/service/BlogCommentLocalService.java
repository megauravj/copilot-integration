package com.liferay.blog.service;

import com.liferay.blog.model.BlogComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;

import java.util.Date;
import java.util.List;

/**
 * Provides the local service interface for BlogComment.
 * Service Builder generates the implementation.
 */
public interface BlogCommentLocalService extends BaseLocalService, PersistedModelLocalService {

    /**
     * Create a new BlogComment entity
     */
    BlogComment createBlogComment(long postId, long userId, String commentText, Date createDate, Date modifiedDate) throws PortalException;

    /**
     * Add BlogComment
     */
    BlogComment addBlogComment(BlogComment blogComment);

    /**
     * Update BlogComment
     */
    BlogComment updateBlogComment(BlogComment blogComment);

    /**
     * Delete BlogComment by ID
     */
    BlogComment deleteBlogComment(long commentId) throws PortalException;

    /**
     * Delete BlogComment entity
     */
    BlogComment deleteBlogComment(BlogComment blogComment);

    /**
     * Get BlogComment by ID
     */
    BlogComment getBlogComment(long commentId) throws PortalException;

    /**
     * Find BlogComment by commentId
     */
    BlogComment fetchBlogComment(long commentId);

    /**
     * Find all BlogComments by postId
     */
    List<BlogComment> getBlogCommentsByPostId(long postId);

    /**
     * Find all BlogComments by userId
     */
    List<BlogComment> getBlogCommentsByUserId(long userId);

    /**
     * Find all BlogComments by postId and userId
     */
    List<BlogComment> getBlogCommentsByPostIdAndUserId(long postId, long userId);

    /**
     * Get count of BlogComments
     */
    int getBlogCommentsCount();

    /**
     * Get count of BlogComments by postId
     */
    int getBlogCommentsCountByPostId(long postId);
}