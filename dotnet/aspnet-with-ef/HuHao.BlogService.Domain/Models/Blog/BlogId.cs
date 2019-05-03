using System;

namespace HuHao.BlogService.Domain.Models.Blog
{
    public class BlogId : IEquatable<BlogId>
    {
        private readonly Guid _id;

        internal BlogId()
        {
            _id = Guid.NewGuid();
        }

        private BlogId(Guid id)
        {
            _id = id;
        }

        public static BlogId Parse(string id)
        {
            return id == null ? null : new BlogId(Guid.Parse(id));
        }

        public override string ToString()
        {
            return _id.ToString();
        }

        bool IEquatable<BlogId>.Equals(BlogId other)
        {
            return Equals(other);
        }

        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            if (obj.GetType() != GetType()) return false;
            return Equals((BlogId) obj);
        }

        public override int GetHashCode()
        {
            return _id.GetHashCode();
        }

        private bool Equals(BlogId other)
        {
            if (ReferenceEquals(null, other)) return false;
            if (ReferenceEquals(this, other)) return true;
            return ToString().Equals(other.ToString());
        }
    }
}