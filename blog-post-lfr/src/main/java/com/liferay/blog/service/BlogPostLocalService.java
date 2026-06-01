package com.liferay.blog.service;

import com.liferay.blog.model.BlogPost;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;

import java.util.Date;
import java.util.List;

/**
 * Provides the local service interface for BlogPost.
 * Service Builder generates the implementation.
 */
public interface BlogPostLocalService extends BaseLocalService, PersistedModelLocalService {

    /**
     * Create a new BlogPost entity
     */
    BlogPost createBlogPost(long groupId, long userId, String title, String content, String status, Date createDate, Date modifiedDate) throws PortalException;

    /**
     * Add BlogPost
     */
    BlogPost addBlogPost(BlogPost blogPost);

    /**
     * Update BlogPost
     */
    BlogPost updateBlogPost(BlogPost blogPost);

    /**
     * Delete BlogPost by ID
     */
    BlogPost deleteBlogPost(long postId) throws PortalException;

    /**
     * Delete BlogPost entity
     */
    BlogPost deleteBlogPost(BlogPost blogPost);

    /**
     * Get BlogPost by ID
     */
    BlogPost getBlogPost(long postId) throws PortalException;

    /**
     * Find BlogPost by postId
     */
    BlogPost fetchBlogPost(long postId);

    /**
     * Find all BlogPosts by groupId
     */
    List<BlogPost> getBlogPostsByGroupId(long groupId);

    /**
     * Find all BlogPosts by status
     */
    List<BlogPost> getBlogPostsByStatus(String status);

    /**
     * Find all BlogPosts by userId
     */
    List<BlogPost> getBlogPostsByUserId(long userId);

    /**
     * Find all BlogPosts by groupId and status
     */
    List<BlogPost> getBlogPostsByGroupIdAndStatus(long groupId, String status);

    /**
     * Get count of BlogPosts
     */
    int getBlogPostsCount();

    /**
     * Get count of BlogPosts by groupId
     */
    int getBlogPostsCountByGroupId(long groupId);

    /**
     * Get count of BlogPosts by status
     */
    int getBlogPostsCountByStatus(String status);
}