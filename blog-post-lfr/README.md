# Liferay 7.4 Blog CMS Service Builder Module

This is a Liferay 7.4 Service Builder module for a simple content management system with Blog Posts and Comments.

## Module Structure

```
blog-post-lfr/
├── service.xml                          # Service Builder configuration
├── build.gradle                         # Gradle build configuration
├── src/main/java/com/liferay/blog/
│   ├── model/
│   │   ├── BlogPost.java               # BlogPost model interface
│   │   ├── BlogComment.java            # BlogComment model interface
│   │   └── impl/
│   │       ├── BlogPostImpl.java        # BlogPost model implementation
│   │       ├── BlogPostBaseImpl.java    # BlogPost base implementation (generated)
│   │       ├── BlogCommentImpl.java     # BlogComment model implementation
│   │       └── BlogCommentBaseImpl.java # BlogComment base implementation (generated)
│   └── service/
│       ├── BlogPostLocalService.java   # BlogPost service interface
│       ├── BlogCommentLocalService.java# BlogComment service interface
│       └── persistence/
│           ├── BlogPostPersistence.java# BlogPost persistence interface
│           └── BlogCommentPersistence.java # BlogComment persistence interface
```

## Entities

### BlogPost

Represents a blog post in the CMS.

**Fields:**
- `postId` (long, primary key) - Unique identifier for the post
- `groupId` (long) - Site/Group ID for multi-tenancy support
- `userId` (long) - Author's user ID
- `userName` (String) - Author's username
- `title` (String) - Post title (required, max 255 chars)
- `content` (String) - Post content (text blob)
- `status` (String) - Post status (DRAFT, PUBLISHED, ARCHIVED)
- `viewCount` (long) - Number of views (default: 0)
- `createDate` (Date) - Creation timestamp
- `modifiedDate` (Date) - Last modification timestamp
- `uuid` (String) - UUID for export/import

**Finder Methods:**
- `findByGroupId(long groupId)` - Find posts by site/group
- `findByPostId(long postId)` - Find specific post
- `findByStatus(String status)` - Find posts by status
- `findByUserId(long userId)` - Find posts by author
- `findByGroupIdAndStatus(long groupId, String status)` - Find posts by group and status

### BlogComment

Represents a comment on a blog post.

**Fields:**
- `commentId` (long, primary key) - Unique identifier for the comment
- `postId` (long) - Foreign key to BlogPost
- `userId` (long) - Commenter's user ID
- `userName` (String) - Commenter's username
- `commentText` (String) - Comment content (text blob)
- `createDate` (Date) - Creation timestamp
- `modifiedDate` (Date) - Last modification timestamp
- `uuid` (String) - UUID for export/import

**Finder Methods:**
- `findByPostId(long postId)` - Find comments on a post
- `findByUserId(long userId)` - Find comments by user
- `findByPostIdAndUserId(long postId, long userId)` - Find user's comments on specific post

## Building the Module

After running Service Builder via the `buildService` Gradle task:

```bash
./gradlew buildService
```

The following files will be generated:
- Service implementation classes
- Persistence implementation classes
- SQL scripts for database tables
- Service utilities and finders

## Database Tables

Service Builder will create the following tables:
- `BlogCMS_BlogPost` - Stores blog posts
- `BlogCMS_BlogComment` - Stores blog comments

## Service Methods

### BlogPostLocalService

- `createBlogPost()` - Create new post
- `addBlogPost()` - Save post
- `updateBlogPost()` - Update existing post
- `deleteBlogPost()` - Delete post
- `getBlogPost()` - Get post by ID
- `getBlogPostsByGroupId()` - Get posts by group
- `getBlogPostsByStatus()` - Get posts by status
- `getBlogPostsByUserId()` - Get posts by author
- `getBlogPostsByGroupIdAndStatus()` - Get posts by group and status

### BlogCommentLocalService

- `createBlogComment()` - Create new comment
- `addBlogComment()` - Save comment
- `updateBlogComment()` - Update existing comment
- `deleteBlogComment()` - Delete comment
- `getBlogComment()` - Get comment by ID
- `getBlogCommentsByPostId()` - Get comments on post
- `getBlogCommentsByUserId()` - Get comments by user
- `getBlogCommentsByPostIdAndUserId()` - Get user's comments on post

## Configuration

### Multi-tenancy
The BlogPost entity includes `groupId` field for multi-tenancy support, allowing different sites/groups to have their own blog posts.

### Status Enumeration
The status field supports the following values:
- `DRAFT` - Post is still being written
- `PUBLISHED` - Post is live
- `ARCHIVED` - Post is archived

### User Audit Fields
Both entities include audit fields:
- `userId` and `userName` - Track who created/modified the entity
- `createDate` - Track creation time
- `modifiedDate` - Track modification time

## Notes

1. This module uses local services by default. To enable remote services, modify the `local-service` and `remote-service` attributes in `service.xml`.
2. The module is configured with `trash-enabled="true"` for BlogPost, enabling trash/recycle bin functionality.
3. The `uuid` field is enabled for both entities to support export/import functionality.
4. All finder methods automatically include pagination support via `start` and `end` parameters.
5. The persistence layer handles all database operations automatically.
