using System;

namespace HuHao.BlogService.Domain.Core.Exceptions
{
    public class EntityExistedException : DomainException
    {
        public EntityExistedException(string message) : base(message)
        {
        }

        public EntityExistedException(Type type, IEntityId id) :
            this($"the {type.Name.ToLower()} with id {id} was existed")
        {
        }
    }
}