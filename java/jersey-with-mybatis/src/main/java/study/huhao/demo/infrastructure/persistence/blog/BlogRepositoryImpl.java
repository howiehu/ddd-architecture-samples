package study.huhao.demo.infrastructure.persistence.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.domain.core.Page;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogCriteria;
import study.huhao.demo.domain.models.blog.BlogId;
import study.huhao.demo.domain.models.blog.BlogRepository;

import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Component
public class BlogRepositoryImpl implements BlogRepository {

    private final BlogMapper blogMapper;

    @Autowired
    public BlogRepositoryImpl(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public void save(Blog blog) {
        var blogPO = BlogPO.of(blog);

        blogMapper.findById(blog.getId().toString()).ifPresentOrElse(
                po -> blogMapper.update(blogPO),
                () -> blogMapper.insert(blogPO)
        );
    }

    @Override
    public Optional<Blog> findById(BlogId id) {
        return blogMapper.findById(id.toString()).map(BlogPO::toDomainModel);
    }

    @Override
    public boolean existsById(BlogId id) {
        return blogMapper.existsById(id.toString());
    }

    @Override
    public void deleteById(BlogId id) {
        blogMapper.deleteById(id.toString());
    }

    @Override
    public Page<Blog> findAllWithPagination(BlogCriteria criteria) {
        var total = blogMapper.countTotalByCriteria(criteria);

        var pagedBlog = blogMapper.selectAllByCriteria(criteria).stream().map(BlogPO::toDomainModel).collect(toList());

        return new Page<>(
                pagedBlog,
                criteria.getLimit(),
                criteria.getOffset(),
                total
        );
    }
}

