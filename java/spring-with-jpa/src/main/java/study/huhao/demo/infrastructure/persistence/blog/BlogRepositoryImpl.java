package study.huhao.demo.infrastructure.persistence.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.domain.core.Page;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogCriteria;
import study.huhao.demo.domain.models.blog.BlogId;
import study.huhao.demo.domain.models.blog.BlogRepository;

import java.util.Optional;

import static study.huhao.demo.infrastructure.persistence.utils.PaginationUtil.buildPageRequest;

@Component
public class BlogRepositoryImpl implements BlogRepository {

    private final BlogJpaRepository blogJpaRepository;

    @Autowired
    public BlogRepositoryImpl(BlogJpaRepository blogJpaRepository) {
        this.blogJpaRepository = blogJpaRepository;
    }


    @Override
    public void save(Blog blog) {
        blogJpaRepository.save(BlogPO.of(blog));
    }

    @Override
    public Optional<Blog> findById(BlogId id) {
        return blogJpaRepository.findById(id.toString()).map(BlogPO::toDomainModel);
    }

    @Override
    public boolean existsById(BlogId id) {
        return blogJpaRepository.existsById(id.toString());
    }

    @Override
    public void deleteById(BlogId id) {
        blogJpaRepository.deleteById(id.toString());
    }

    @Override
    public Page<Blog> findAllWithPagination(BlogCriteria criteria) {
        var pagedBlog = blogJpaRepository.findAll(buildPageRequest(criteria))
                .map(BlogPO::toDomainModel);

        return new Page<>(
                pagedBlog.getContent(),
                criteria.getPage(),
                criteria.getPageSize(),
                pagedBlog.getTotalElements()
        );
    }

}

