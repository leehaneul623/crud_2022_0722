package com.mysite.sbb.repository;

import com.mysite.sbb.dao.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
