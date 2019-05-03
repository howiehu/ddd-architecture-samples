using System;
using HuHao.BlogService.Domain.Core;

namespace HuHao.BlogService.Domain.Models.Blog
{
    public class Blog : IAggregateRoot
    {
        internal Blog(string title, string body, IEntityId authorId)
        {
            BlogId = new BlogId();
            Title = title;
            Body = body;
            AuthorId = authorId;
            Status = Status.Draft;
            SavedAt = CreatedAt = DateTime.UtcNow;
        }

        public Blog(BlogId blogId, string title, string body, IEntityId authorId, Status status, DateTime createdAt,
            DateTime savedAt, PublishedBlog published)
        {
            BlogId = blogId;
            Title = title;
            Body = body;
            AuthorId = authorId;
            Status = status;
            CreatedAt = createdAt;
            SavedAt = savedAt;
            Published = published;
        }

        public BlogId BlogId { get; }
        public string Title { get; }
        public string Body { get; }
        public IEntityId AuthorId { get; }
        public Status Status { get; }
        public DateTime CreatedAt { get; }
        public DateTime SavedAt { get; }
        public PublishedBlog Published { get; }
    }

    public enum Status
    {
        Draft,
        Published
    }
}