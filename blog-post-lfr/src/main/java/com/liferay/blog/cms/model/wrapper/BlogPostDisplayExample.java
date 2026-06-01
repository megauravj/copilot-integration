package com.liferay.blog.cms.model.wrapper;

import com.liferay.blog.cms.model.BlogPost;
import com.liferay.blog.cms.service.BlogPostLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Example usage of the BlogPostDisplay wrapper class.
 * 
 * This class demonstrates various use cases for the BlogPostDisplay wrapper,
 * including displaying blog posts in portlets, REST API responses, and logging.
 * 
 * @author Blog CMS Development Team
 * @version 1.0
 */
public class BlogPostDisplayExample {

	/**
	 * Example 1: Display a single blog post with computed properties.
	 * 
	 * This example shows how to wrap a BlogPost entity and access computed
	 * properties like author name, formatted dates, and status labels.
	 */
	public static void displaySinglePost(long postId) {
		try {
			// Get the blog post from service
			BlogPost blogPost = BlogPostLocalServiceUtil.getBlogPost(postId);
			
			// Wrap it with the display class
			BlogPostDisplay blogPostDisplay = new BlogPostDisplay(blogPost);
			
			// Access basic properties
			System.out.println("Post ID: " + blogPostDisplay.getPostId());
			System.out.println("Title: " + blogPostDisplay.getTitle());
			System.out.println("Content: " + blogPostDisplay.getContent());
			
			// Access computed properties
			System.out.println("Author: " + blogPostDisplay.getAuthorName());
			System.out.println("Created: " + blogPostDisplay.getFormattedCreatedDate());
			System.out.println("Modified: " + blogPostDisplay.getFormattedModifiedDate());
			System.out.println("Status: " + blogPostDisplay.getStatusLabel());
			System.out.println("Views: " + blogPostDisplay.getViewCount());
			System.out.println("Comments: " + blogPostDisplay.getCommentCount());
			
			// Logging
			System.out.println("Log Output: " + blogPostDisplay.toString());
			
		} catch (Exception e) {
			System.err.println("Error displaying blog post: " + e.getMessage());
		}
	}

	/**
	 * Example 2: Convert blog post to Map for REST API response.
	 * 
	 * This example shows how to convert a BlogPost to a Map that can be
	 * serialized to JSON for REST API responses.
	 */
	public static Map<String, Object> getBlogPostForRestApi(long postId) {
		try {
			BlogPost blogPost = BlogPostLocalServiceUtil.getBlogPost(postId);
			BlogPostDisplay blogPostDisplay = new BlogPostDisplay(blogPost);
			
			// Convert to Map for JSON serialization
			return blogPostDisplay.toMap();
			
		} catch (Exception e) {
			System.err.println("Error converting blog post to map: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Example 3: REST API response using JSONFactoryUtil.
	 * 
	 * This example demonstrates creating a JSON response for a REST API endpoint.
	 */
	public static JSONObject getBlogPostAsJSON(long postId) {
		try {
			BlogPost blogPost = BlogPostLocalServiceUtil.getBlogPost(postId);
			BlogPostDisplay blogPostDisplay = new BlogPostDisplay(blogPost);
			
			// Create JSON object
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			
			// Add properties
			jsonObject.put("postId", blogPostDisplay.getPostId());
			jsonObject.put("title", blogPostDisplay.getTitle());
			jsonObject.put("content", blogPostDisplay.getContent());
			jsonObject.put("author", blogPostDisplay.getAuthorName());
			jsonObject.put("status", blogPostDisplay.getStatusLabel());
			jsonObject.put("createdDate", blogPostDisplay.getFormattedCreatedDate());
			jsonObject.put("modifiedDate", blogPostDisplay.getFormattedModifiedDate());
			jsonObject.put("viewCount", blogPostDisplay.getViewCount());
			jsonObject.put("commentCount", blogPostDisplay.getCommentCount());
			jsonObject.put("isPublished", blogPostDisplay.isPublished());
			
			return jsonObject;
			
		} catch (Exception e) {
			System.err.println("Error creating JSON response: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Example 4: Display list of published blog posts.
	 * 
	 * This example shows how to fetch published posts from a site and display
	 * them with computed properties.
	 */
	public static List<BlogPostDisplay> getPublishedPosts(long groupId) {
		List<BlogPostDisplay> displayPosts = new ArrayList<>();
		
		try {
			// Get published posts
			List<BlogPost> blogPosts = BlogPostLocalServiceUtil.getBlogPostsByGroupIdAndStatus(
				groupId, 
				"PUBLISHED"
			);
			
			// Wrap each post with display class
			for (BlogPost blogPost : blogPosts) {
				displayPosts.add(new BlogPostDisplay(blogPost));
			}
			
			// Log results
			System.out.println("Found " + displayPosts.size() + " published posts");
			for (BlogPostDisplay display : displayPosts) {
				System.out.println("  - " + display.getTitle() + 
					" (by " + display.getAuthorName() + ", " + 
					display.getCommentCount() + " comments)");
			}
			
		} catch (Exception e) {
			System.err.println("Error fetching published posts: " + e.getMessage());
		}
		
		return displayPosts;
	}

	/**
	 * Example 5: Filter posts by status and display with icons.
	 * 
	 * This example shows how to use status checks for conditional rendering.
	 */
	public static void displayPostsByStatusWithIcons(long groupId) {
		try {
			// Get all posts
			List<BlogPost> allPosts = BlogPostLocalServiceUtil.getBlogPostsByGroupId(groupId);
			
			for (BlogPost blogPost : allPosts) {
				BlogPostDisplay display = new BlogPostDisplay(blogPost);
				
				// Use status checks for conditional logic
				String icon = "❓";
				if (display.isPublished()) {
					icon = "✅";
				} else if (display.isDraft()) {
					icon = "📝";
				} else if (display.isArchived()) {
					icon = "📦";
				}
				
				System.out.println(icon + " " + display.getTitle() + 
					" (" + display.getStatusLabel() + ")");
			}
			
		} catch (Exception e) {
			System.err.println("Error displaying posts: " + e.getMessage());
		}
	}

	/**
	 * Example 6: Create REST API response with pagination and metadata.
	 * 
	 * This example demonstrates creating a paginated REST API response
	 * with metadata.
	 */
	public static JSONObject getPaginatedBlogPostsResponse(long groupId, int pageNumber, int pageSize) {
		try {
			// Get posts
			List<BlogPost> allPosts = BlogPostLocalServiceUtil.getBlogPostsByGroupId(groupId);
			
			// Calculate pagination
			int totalCount = allPosts.size();
			int startIndex = (pageNumber - 1) * pageSize;
			int endIndex = Math.min(startIndex + pageSize, totalCount);
			int totalPages = (int) Math.ceil((double) totalCount / pageSize);
			
			// Create response
			JSONObject response = JSONFactoryUtil.createJSONObject();
			response.put("pageNumber", pageNumber);
			response.put("pageSize", pageSize);
			response.put("totalCount", totalCount);
			response.put("totalPages", totalPages);
			
			// Add posts for current page
			response.put("posts", JSONFactoryUtil.createJSONArray());
			for (int i = startIndex; i < endIndex; i++) {
				BlogPost blogPost = allPosts.get(i);
				BlogPostDisplay display = new BlogPostDisplay(blogPost);
				
				JSONObject postJson = JSONFactoryUtil.createJSONObject(
					JSONFactoryUtil.looseSerializeDeep(display.toMap())
				);
				response.getJSONArray("posts").put(postJson);
			}
			
			return response;
			
		} catch (Exception e) {
			System.err.println("Error creating paginated response: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Example 7: Search and display results with highlighting.
	 * 
	 * This example shows how to search for posts and display results with
	 * computed properties.
	 */
	public static List<BlogPostDisplay> searchPostsByAuthor(long groupId, String authorName) {
		List<BlogPostDisplay> results = new ArrayList<>();
		
		try {
			List<BlogPost> allPosts = BlogPostLocalServiceUtil.getBlogPostsByGroupId(groupId);
			
			for (BlogPost blogPost : allPosts) {
				BlogPostDisplay display = new BlogPostDisplay(blogPost);
				
				// Search by author
				if (display.getAuthorName().contains(authorName)) {
					results.add(display);
					System.out.println("Found: " + display.toString());
				}
			}
			
			System.out.println("Search results: " + results.size() + " posts found");
			
		} catch (Exception e) {
			System.err.println("Error searching posts: " + e.getMessage());
		}
		
		return results;
	}

	/**
	 * Example 8: Generate HTML table from blog posts.
	 * 
	 * This example shows how to use BlogPostDisplay to generate HTML output.
	 */
	public static String generateBlogPostTable(long groupId) {
		StringBuilder html = new StringBuilder();
		html.append("<table class=\"blog-posts-table\">\n");
		html.append("<thead>\n");
		html.append("<tr><th>Title</th><th>Author</th><th>Created</th><th>Status</th><th>Views</th><th>Comments</th></tr>\n");
		html.append("</thead>\n");
		html.append("<tbody>\n");
		
		try {
			List<BlogPost> posts = BlogPostLocalServiceUtil.getBlogPostsByGroupId(groupId);
			
			for (BlogPost blogPost : posts) {
				BlogPostDisplay display = new BlogPostDisplay(blogPost);
				
				html.append("<tr>\n");
				html.append("<td>").append(display.getTitle()).append("</td>\n");
				html.append("<td>").append(display.getAuthorName()).append("</td>\n");
				html.append("<td>").append(display.getFormattedCreatedDate()).append("</td>\n");
				html.append("<td><span class=\"status-").append(display.getStatus().toLowerCase())
					.append("\">").append(display.getStatusLabel()).append("</span></td>\n");
				html.append("<td>").append(display.getViewCount()).append("</td>\n");
				html.append("<td>").append(display.getCommentCount()).append("</td>\n");
				html.append("</tr>\n");
			}
			
		} catch (Exception e) {
			System.err.println("Error generating table: " + e.getMessage());
		}
		
		html.append("</tbody>\n");
		html.append("</table>\n");
		
		return html.toString();
	}

	/**
	 * Example 9: Error handling - graceful handling of missing data.
	 * 
	 * This example demonstrates that BlogPostDisplay handles null/missing data gracefully.
	 */
	public static void demonstrateErrorHandling(long postId) {
		try {
			BlogPost blogPost = BlogPostLocalServiceUtil.getBlogPost(postId);
			BlogPostDisplay display = new BlogPostDisplay(blogPost);
			
			// All these calls handle null/missing data gracefully
			System.out.println("Title: " + display.getTitle()); // Won't be null
			System.out.println("Content: " + display.getContent()); // Might be null, but handled
			System.out.println("Author: " + display.getAuthorName()); // Returns "Unknown Author" if not found
			System.out.println("Created Date: " + display.getFormattedCreatedDate()); // Returns empty string if null
			System.out.println("Status Label: " + display.getStatusLabel()); // Returns original if unknown
			System.out.println("Comment Count: " + display.getCommentCount()); // Returns 0 if error
			
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Example 10: Using in a Portlet render method.
	 * 
	 * This example shows typical usage in a Liferay portlet render phase.
	 */
	public static void portletRenderExample(long postId, Map<String, Object> renderContext) {
		try {
			BlogPost blogPost = BlogPostLocalServiceUtil.getBlogPost(postId);
			BlogPostDisplay display = new BlogPostDisplay(blogPost);
			
			// Add to render context for JSP
			renderContext.put("post", display);
			renderContext.put("title", display.getTitle());
			renderContext.put("authorName", display.getAuthorName());
			renderContext.put("formattedDate", display.getFormattedCreatedDate());
			renderContext.put("statusLabel", display.getStatusLabel());
			renderContext.put("viewCount", display.getViewCount());
			renderContext.put("commentCount", display.getCommentCount());
			renderContext.put("isPublished", display.isPublished());
			
			// Also make the toMap() result available for complex rendering
			renderContext.put("postData", display.toMap());
			
		} catch (Exception e) {
			System.err.println("Error in portlet render: " + e.getMessage());
		}
	}
}
