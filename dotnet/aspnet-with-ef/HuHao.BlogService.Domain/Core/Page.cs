using System;
using System.Collections.Generic;

namespace HuHao.BlogService.Domain.Core
{
    public class Page<T> : IReadModel
    {
        public List<T> Results { get; }
        public int Limit { get; }
        public long Offset { get; }
        public long Total { get; }

        public Page(List<T> results, int limit, long offset, long total)
        {
            Results = results;
            Limit = limit;
            Offset = offset;
            Total = total;
        }
    }
}