using System;

namespace HuHao.BlogService.Domain.Core.Exceptions
{
    public class EntityNotFoundException : DomainException
    {
        public EntityNotFoundException(string message) : base(message)
        {
        }

        public EntityNotFoundException(Type type, IEntityId id) :
            this($"cannot find the {type.Name.ToLower()} with id {id}")
        {
        }
    }
}